package testing;

import com.example.ispw2.DAO.DemoPrenotazioneDAO;
import com.example.ispw2.DAO.factory.DAOFactory;
import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.bean.PrenotazioniBean;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.controller.PrenotazioniController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.MaxPendingBorrowsException;
import com.example.ispw2.exceptions.UserNonSupportatoException;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Prenotazione;
import com.example.ispw2.view.gui.other.Connector;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class PrenotaTicketTest {
    @Test
    public void newCodiceTest(){
        String nuovocodice = PrenotazioniController.getInstance().newCodice();
        System.out.println(nuovocodice);
    }


    @Test
    public void prenotatestMYSQL() {
        changeType("MYSQL");
        DAOFactory.refreshDAOFactory(true);
        prenotatest();
        eliminaPrenotazioneMYSQL();
    }
    @Test
    public void prenotatestDEMO(){
        changeType("demo");
        DAOFactory.refreshDAOFactory(true);
        prenotatest();
    }

    private void prenotatest() {
        LoginController.getInstance().setUser(null);
        PrenotazioniController.getInstance().setCliente(null);
        LoginBean loginBean = new LoginBean(
                "anna.bianchi@example.com",
                "annaPass!",
                "utente registrato");
        LoginController loginController = LoginController.getInstance();
        try {
            loginController.start(loginBean);
        } catch (CredenzialiErrateException | DAOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(LoginController.getInstance().getUser().toString());

        PrenotazioniBean prenotazioneBean = new PrenotazioniBean(
                "Festival del Cinema",
                "006",
                "Anna",
                "Bianchi",
                LocalDateTime.of(2025, 9, 5, 18, 00,00),
                LocalDateTime.now(),
                "PENDENTE");


        List<Prenotazione> prenotazioniTot = PrenotazioniController.getInstance().getPrenotazioni();
        System.out.println("prenotazioni: OLD");
        for(Prenotazione prenotazione : prenotazioniTot) {
            System.out.println("\t" + prenotazione.getCod_prenotazione());
        }

        try {
            PrenotazioniController.getInstance().prenotaEvento(prenotazioneBean);
            prenotazioniTot = PrenotazioniController.getInstance().getPrenotazioni();
            System.out.println("prenotazioni: NEW");
            for(Prenotazione prenotazione : PrenotazioniController.getInstance().getPrenotazioni()) {
                System.out.println("\t" + prenotazione.getCod_prenotazione());
            }

        } catch (MaxPendingBorrowsException e) {
            throw new RuntimeException(e);
        }

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

    private void eliminaPrenotazioneMYSQL() {
        String sql = "DELETE FROM ispw.Prenotazione WHERE cod_prenotazione = '006'";
        Connection conn = Connector.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            int rowsDeleted = ps.executeUpdate();
            System.out.println("righe cancellate: "+ rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
