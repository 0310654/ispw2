package testing;

import com.example.ispw2.engineering.factory.DAOFactory;
import com.example.ispw2.engineering.bean.LoginBean;
import com.example.ispw2.controller.HomeOrganizzatoreController;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.engineering.exceptions.CredenzialiErrateException;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class IMieiEventiTest {

    private void iMieiEventitest() {

        LoginBean loginBean = new LoginBean(
                "organizzatore@email.com",
                "password",
                "organizzatore");
        LoginController loginController = LoginController.getInstance();
        try {
            Organizzatore organizzatore = (Organizzatore) loginController.start(loginBean);
            System.out.println(organizzatore.toString());
        } catch (CredenzialiErrateException | DAOException e) {
            throw new RuntimeException(e);
        }

        try{
            ArrayList<Evento> imieieventi = HomeOrganizzatoreController.getInstance().getMieiEventi();
            if (!imieieventi.isEmpty()) {
                for (Evento evento : imieieventi) {
                    System.out.println(evento.toString());
                }
            }else{
                System.out.println("Non hai ancora organizzato nessun evento");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void iMieiEventitestDEMO() {
        changeType("demo");
        DAOFactory.refreshDAOFactory(true);
        iMieiEventitest();
    }

    @Test
    public void iMieiEventitestMYSQL() {
        changeType("MYSQL");
        DAOFactory.refreshDAOFactory(true);
        iMieiEventitest();
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
