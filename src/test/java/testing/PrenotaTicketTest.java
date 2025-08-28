package testing;

import com.example.ispw2.DAO.DemoPrenotazioneDAO;
import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.bean.PrenotazioniBean;
import com.example.ispw2.bean.SelectedBean;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.controller.PrenotazioniController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.MaxPendingBorrowsException;
import com.example.ispw2.exceptions.UserNonSupportatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Prenotazione;
import com.example.ispw2.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class PrenotaTicketTest {
    @Test
    public void newCodiceTest(){
        String nuovocodice = PrenotazioniController.getInstance().newCodice();
        System.out.println(nuovocodice);
    }


    @Test
    public void prenotatest() {

        LoginBean loginBean = new LoginBean(
                "cliente@email.com",
                "password",
                "utente registrato");
        LoginController loginController = LoginController.getInstance();
        try {
            loginController.start(loginBean);
        } catch (CredenzialiErrateException | DAOException | UserNonSupportatoException e) {
            throw new RuntimeException(e);
        }

        System.out.println(LoginController.getInstance().getUser().toString());

        PrenotazioniBean prenotazioniBean = new PrenotazioniBean(
                "Mostra Van Gogh",
                "1002",
                "Maria",
                "Rossi",
                LocalDateTime.of(2025, 9, 12, 10, 30),
                LocalDateTime.of(2025, 8, 25, 14, 15),
                "PENDENTE",
                false);


        List<Prenotazione> prenotazioniTot = DemoPrenotazioneDAO.getInstance().getPrenotazioni();
        System.out.println("prenotazioni: OLD");
        for(Prenotazione prenotazione : prenotazioniTot) {
            System.out.println("\t" + prenotazione.toString());
        }

        try {
            PrenotazioniController.getInstance().prenotaEvento(prenotazioniBean);
            prenotazioniTot = DemoPrenotazioneDAO.getInstance().getPrenotazioni();
            System.out.println("prenotazioni: NEW");
            for(Prenotazione prenotazione : prenotazioniTot) {
                System.out.println("\t" + prenotazione.toString());
            }

        } catch (MaxPendingBorrowsException e) {
            throw new RuntimeException(e);
        }

    }
}
