package testing;
import com.example.ispw2.engineering.DAO.EventiDAO;
import com.example.ispw2.engineering.factory.DAOFactory;
import com.example.ispw2.engineering.bean.EventBean;
import com.example.ispw2.engineering.bean.LoginBean;
import com.example.ispw2.controller.AddEventoController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.engineering.exceptions.CredenzialiErrateException;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.altro.Connector;
import com.example.ispw2.model.Evento;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddEventoTest {


    private void newCodiceEventoTest(){
        String nuovocodiceevento = AddEventoController.getInstance().newCodiceEvento();
        System.out.println(nuovocodiceevento);
    }


    private void addEventoTest(){

        LoginBean loginBean = new LoginBean(
                "organizzatore@email.com",
                "password",
                "organizzatore");
        LoginController loginController = LoginController.getInstance();
        try {
            loginController.start(loginBean);
        } catch (CredenzialiErrateException | DAOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(LoginController.getInstance().getUser().toString());

        LocalDate data = LocalDate.of(2025, 9, 12);
        LocalTime ora = LocalTime.of(10, 30);
        LocalDateTime dt = LocalDateTime.of(data, ora);

        ArrayList<String> settori1 = new ArrayList<>(List.of("VIP", "Standard", "Economy"));
        ArrayList<Double> prezzi1 = new ArrayList<>(List.of(80.0, 50.0, 30.0));
        ArrayList<Integer> disponibilita1 = new ArrayList<>(List.of(50, 150, 300));
        ArrayList<Integer> numposti = new ArrayList<>(List.of(100, 300,200));

        EventBean eventBean = new EventBean("006",
                "concerto Linkin Park",
                "organizzatore@email.com",
                "a",
                "Concerto",
                "Roma",
                settori1,
                disponibilita1,
                prezzi1,
                dt,
                numposti,
                " ");
        AddEventoController.getInstance().addEvento(eventBean);



    }

    @Test
    public void testAddEventoMYSQL(){
        changeType("MYSQL");
        DAOFactory.refreshDAOFactory(true);

        EventiDAO eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
        ArrayList<Evento> eventi = eventiDAO.getEventi();
        Integer numEventiOld = eventi.size();

        addEventoTest();
        newCodiceEventoTest();

        eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
        eventi = eventiDAO.getEventi();
        Integer numEventiNew = eventi.size();

        cancellaEventoMYSQL();
        cancellaSettoriMYSQL();

        assertEquals(numEventiOld, numEventiNew-1);
    }
    @Test
    public void testAddEventoDEMO(){
        changeType("demo");
        DAOFactory.refreshDAOFactory(true);

        EventiDAO eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
        ArrayList<Evento> eventi = eventiDAO.getEventi();
        Integer numEventiOld = eventi.size();

        addEventoTest();
        newCodiceEventoTest();

        eventiDAO = DAOFactory.getDAOFactory().getEventiDAO();
        eventi = eventiDAO.getEventi();
        Integer numEventiNew = eventi.size();
        assertEquals(numEventiOld, numEventiNew-1);
    }

    private void changeType(String persistence){
        File file = new File("src/main/resources/configurations.properties");
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(file)) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        props.setProperty("PERSISTENCE_TYPE", persistence);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            props.store(fos, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancellaSettoriMYSQL(){
        //funzione che cancella settori per l'evento
        String sql_s = "DELETE FROM ispw.Settori WHERE codice_evento = '006'";
        Connection conn = Connector.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql_s);
            int rowsDeleted = ps.executeUpdate();
            conn.commit();
            System.out.println("righe cancellate settore: "+ rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancellaEventoMYSQL(){
        //funzione che cancella eventi
        String sql = "DELETE FROM ispw.Evento WHERE cod_evento = '006'";
        Connection conn = Connector.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            int rowsDeleted = ps.executeUpdate();
            conn.commit();
            System.out.println("righe cancellate evento: "+ rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
