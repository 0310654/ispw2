package testing;


import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Ticket;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ModelTest {
    @Test
    public void eventoTest() {
        ArrayList<String> nome_settore = new ArrayList<>();
        nome_settore.add("distinti");
        nome_settore.add("curva nord");
        nome_settore.add("curva sud");
        ArrayList<Integer> disp_settore = new ArrayList<>();
        disp_settore.add(300);
        disp_settore.add(200);
        disp_settore.add(200);
        ArrayList<Double> prezzo_settore = new ArrayList<>();
        prezzo_settore.add(80.0);
        prezzo_settore.add(90.0);
        prezzo_settore.add(100.0);
        ArrayList<Integer> posti_settore = new ArrayList<>();
        posti_settore.add(400);
        posti_settore.add(500);
        posti_settore.add(500);
        LocalDate data = LocalDate.of(2026, 5, 24);
        LocalTime ora = LocalTime.of(17, 30);
        LocalDateTime dt = LocalDateTime.of(data, ora);

        Evento evento = new Evento(
                "001",
                "evento test",
                "test",
                "test1",
                "concerto",
                "roma",
                nome_settore,
                disp_settore,
                prezzo_settore,
                dt,
                posti_settore,
                "");

        System.out.println(evento.toString());
    }

    @Test
    public void ticketTest() {
        LocalDate data = LocalDate.of(2026, 5, 24);
        LocalTime ora = LocalTime.of(17, 30);
        LocalDateTime dt = LocalDateTime.of(data, ora);

        Ticket ticket = new Ticket(
                "001",
                "evento test",
                "pippo",
                "pippo",
                "num",
                "A1",
                dt);

        System.out.println(ticket.toString());
    }


}
