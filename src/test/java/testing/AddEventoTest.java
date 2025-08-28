package testing;

import com.example.ispw2.DAO.DemoEventiDAO;
import com.example.ispw2.DAO.DemoPrenotazioneDAO;
import com.example.ispw2.bean.EventBean;
import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.bean.PrenotazioniBean;
import com.example.ispw2.controller.AddEventoController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.controller.PrenotazioniController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.MaxPendingBorrowsException;
import com.example.ispw2.exceptions.UserNonSupportatoException;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Prenotazione;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddEventoTest {

    @Test
    public void newCodiceEventoTest(){
        String nuovocodiceevento = AddEventoController.getInstance().newCodiceEvento();
        System.out.println(nuovocodiceevento);
    }


    @Test
    public void addEventotest() {

        LoginBean loginBean = new LoginBean(
                "organizzatore@email.com",
                "password",
                "organizzatore");
        LoginController loginController = LoginController.getInstance();
        try {
            loginController.start(loginBean);
        } catch (CredenzialiErrateException | DAOException | UserNonSupportatoException e) {
            throw new RuntimeException(e);
        }

        System.out.println(LoginController.getInstance().getUser().toString());

        ArrayList<String> settore = new ArrayList<>();
        settore.add("Platea");
        settore.add("Galleria");

        ArrayList<Integer> disponibilita_settore = new ArrayList<>();
        disponibilita_settore.add(150);
        disponibilita_settore.add(100);

        ArrayList<Double> prezzo_settore = new ArrayList<>();
        prezzo_settore.add(50.0);
        prezzo_settore.add(30.0);

        ArrayList<Integer> num_posti_settore = new ArrayList<>();
        num_posti_settore.add(150);
        num_posti_settore.add(100);

        EventBean eventBean = new EventBean(
                "007",
                "Concerto Eminem",
                "Arianna Rossi",
                "LiveNation",
                "Concerto",
                "Verona",
                settore,
                disponibilita_settore,
                prezzo_settore,
                LocalDateTime.of(2025, 9, 14, 21, 0),
                num_posti_settore,
                "Concerto unico con scenografie spettacolari e ospiti speciali."
        );

        System.out.println("voglio inserire: " + eventBean.toString());


        List<Evento> eventiTot = DemoEventiDAO.getInstance().getEventi();
        System.out.println("eventi: OLD");
        for(Evento evento : eventiTot) {
            System.out.println("\t" + evento.toString());
        }

        try {
            AddEventoController.getInstance().addEvento(eventBean);
            eventiTot = DemoEventiDAO.getInstance().getEventi();
            System.out.println("eventi: NEW");
            for(Evento evento : eventiTot) {
                System.out.println("\t" + evento.toString());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
