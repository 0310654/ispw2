package testing;

import com.example.ispw2.DAO.DemoPrenotazioneDAO;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class PrenotaTicketTest {

    @Test
    public void newCodiceTest(){
        String nuovocodice = PrenotazioniController.getInstance().newCodice();
        System.out.println(nuovocodice);
    }


    @Test
    public void prenotatest() {

        //funzione che cancella prenotazione
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

        PrenotazioniBean prenotazioniBean = new PrenotazioniBean(
                "Festival del Cinema",
                "006",
                "Anna",
                "Bianchi",
                LocalDateTime.of(2025, 9, 5, 18, 00,00),
                LocalDateTime.now(),
                "PENDENTE",
                false);


        List<Prenotazione> prenotazioniTot = PrenotazioniController.getInstance().getPrenotazioni();
        System.out.println("prenotazioni: OLD");
        for(Prenotazione prenotazione : prenotazioniTot) {
            System.out.println("\t" + prenotazione.toString());
        }

        try {
            PrenotazioniController.getInstance().prenotaEvento(prenotazioniBean);
            prenotazioniTot = PrenotazioniController.getInstance().getPrenotazioni();
            System.out.println("prenotazioni: NEW");
            for(Prenotazione prenotazione : prenotazioniTot) {
                System.out.println("\t" + prenotazione.toString());
            }

        } catch (MaxPendingBorrowsException e) {
            throw new RuntimeException(e);
        }

    }
}
