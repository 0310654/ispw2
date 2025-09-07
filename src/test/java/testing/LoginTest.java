package testing;

import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.UserNonSupportatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Organizzatore;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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
        File file = new File("src/main/resources/configurations.properties");
        try {
            List<String> righe = Files.readAllLines(file.toPath());
            System.out.println("prima: " + righe.get(1));
            righe.set(1, "PERSISTENCE_TYPE=MYSQL");
            System.out.println("dopo: " + righe.get(1));
            Files.write(file.toPath(), righe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        testClienteLogin();
    }
    @Test
    public void testLoginClienteDEMO(){
        File file = new File("src/main/resources/configurations.properties");
        try {
            List<String> righe = Files.readAllLines(file.toPath());
            System.out.println("prima: " + righe.get(1));
            righe.set(1, "PERSISTENCE_TYPE=demo");
            System.out.println("dopo: " + righe.get(1));
            Files.write(file.toPath(), righe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        testClienteLogin();
    }
    @Test
    public void testLoginClienteJSON(){
        File file = new File("src/main/resources/configurations.properties");
        try {
            List<String> righe = Files.readAllLines(file.toPath());
            System.out.println("prima: " + righe.get(1));
            righe.set(1, "PERSISTENCE_TYPE=JSON");
            System.out.println("dopo: " + righe.get(1));
            Files.write(file.toPath(), righe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        File file = new File("src/main/resources/configurations.properties");
        try {
            List<String> righe = Files.readAllLines(file.toPath());
            System.out.println("prima: " + righe.get(1));
            righe.set(1, "PERSISTENCE_TYPE=JSON");
            System.out.println("dopo: " + righe.get(1));
            Files.write(file.toPath(), righe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        testOrganizzatoreLogin();
    }
    @Test
    public void testLoginOrganizzatoreMYSQL(){
        File file = new File("src/main/resources/configurations.properties");
        try {
            List<String> righe = Files.readAllLines(file.toPath());
            System.out.println("prima: " + righe.get(1));
            righe.set(1, "PERSISTENCE_TYPE=MYSQL");
            System.out.println("dopo: " + righe.get(1));
            Files.write(file.toPath(), righe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        testOrganizzatoreLogin();
    }
    @Test
    public void testLoginOrganizzatoreDEMO(){
        File file = new File("src/main/resources/configurations.properties");
        try {
            List<String> righe = Files.readAllLines(file.toPath());
            System.out.println("prima: " + righe.get(1));
            righe.set(1, "PERSISTENCE_TYPE=demo");
            System.out.println("dopo: " + righe.get(1));
            Files.write(file.toPath(), righe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        testOrganizzatoreLogin();
    }

}
