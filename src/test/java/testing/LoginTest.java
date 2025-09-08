package testing;

import com.example.ispw2.DAO.factory.DAOFactory;
import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Organizzatore;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

//FUNZIONA

public class LoginTest {

    private void testClienteLogin() {
        LoginBean loginBean = new LoginBean(
                "cliente@email.com",
                "password",
                "utente registrato");
        LoginController loginController = LoginController.getInstance();
        try {
            Cliente cliente = (Cliente) loginController.start(loginBean);
            System.out.println(cliente.toString());
        } catch (CredenzialiErrateException | DAOException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    public void testLoginClienteMYSQL(){
        changeType("MYSQL");
        DAOFactory.refreshDAOFactory(true);
        testClienteLogin();
    }
    @Test
    public void testLoginClienteDEMO(){
        changeType("demo");
        DAOFactory.refreshDAOFactory(true);
        testClienteLogin();
    }
    @Test
    public void testLoginClienteJSON(){
        changeType("JSON");
        DAOFactory.refreshDAOFactory(true);
        testClienteLogin();
    }



    private void testOrganizzatoreLogin() {
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
    }

    @Test
    public void testLoginOrganizzatoreJSON(){
        changeType("JSON");
        DAOFactory.refreshDAOFactory(true);
        testOrganizzatoreLogin();
    }
    @Test
    public void testLoginOrganizzatoreMYSQL(){
        changeType("MYSQL");
        DAOFactory.refreshDAOFactory(true);
        testOrganizzatoreLogin();
    }
    @Test
    public void testLoginOrganizzatoreDEMO(){
        changeType("demo");
        DAOFactory.refreshDAOFactory(true);
        testOrganizzatoreLogin();
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
