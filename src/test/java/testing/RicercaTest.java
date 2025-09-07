package testing;

import com.example.ispw2.bean.EventBean;
import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.bean.SelectedBean;
import com.example.ispw2.controller.AddEventoController;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.controller.HomeOrganizzatoreController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.UserNonSupportatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.view.gui.other.Connector;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RicercaTest {

    @Test
    @Order(1)
    public void addEventoTest(){

        //funzione che cancella eventi
        String sql = "DELETE FROM ispw.Evento WHERE cod_evento = '006'";
        Connection conn = Connector.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            int rowsDeleted = ps.executeUpdate();
            System.out.println("righe cancellate: "+ rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //funzione che cancella settori per l'evento
        String sql_s = "DELETE FROM ispw.Settori WHERE codice_evento = '006'";
        try {
            ps = conn.prepareStatement(sql_s);
            int rowsDeleted = ps.executeUpdate();
            System.out.println("righe cancellate: "+ rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
    @Order(2)
    public void ricercatest() {
        LocalDate data = LocalDate.of(2025, 9, 12);
        LocalTime ora = LocalTime.of(10, 30);
        LocalDateTime dt = LocalDateTime.of(data, ora);

        SelectedBean selectedBean = new SelectedBean(
                "Concerto",
                "Roma",
                dt,
                "50€-100€"
        );
        List<Evento> eventi = HomeClienteController.getInstance().filtriSelezionati(selectedBean);

        // Stampa tutti gli eventi trovati
        for (Evento e : eventi) {
            System.out.println(e.toString());
        }
    }
}
