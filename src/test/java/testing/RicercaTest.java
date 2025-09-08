package testing;

import com.example.ispw2.DAO.factory.DAOFactory;
import com.example.ispw2.bean.FiltriBean;
import com.example.ispw2.bean.SelectedBean;
import com.example.ispw2.controller.HomeClienteController;
import com.example.ispw2.model.Evento;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;

//FUNZIONA

public class RicercaTest {


    private void getFiltritest() {
        FiltriBean filtriBean = HomeClienteController.getInstance().getFiltri();
        System.out.println(filtriBean.toString());
    }
    @Test
    public void testFiltriMYSQL(){
        changeType("MYSQL");
        DAOFactory.refreshDAOFactory(true);
        getFiltritest();
    }
    @Test
    public void testFiltriDEMO(){
        changeType("demo");
        DAOFactory.refreshDAOFactory(true);
        getFiltritest();
    }


    @Test
    public void testRicercaMYSQL(){
        changeType("MYSQL");
        DAOFactory.refreshDAOFactory(true);
        ricercatest();
    }

    @Test
    public void testRicercaDEMO(){
        changeType("demo");
        DAOFactory.refreshDAOFactory(true);
        ricercatest();
    }


    private void ricercatest() {
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


}
