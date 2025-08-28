package testing;

import com.example.ispw2.bean.SelectedBean;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.UserNonSupportatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RicercaTest {
    @Test
    public void ricercatest() {
        LocalDate data = LocalDate.of(2025, 9, 12);
        LocalTime ora = LocalTime.of(10, 30);
        LocalDateTime dt = LocalDateTime.of(data, ora);

        SelectedBean selectedBean = new SelectedBean(
                "Teatro",
                "Milano",
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
