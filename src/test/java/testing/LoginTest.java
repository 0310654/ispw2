package testing;

import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.controller.LoginController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.UserNonSupportatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Organizzatore;
import org.junit.jupiter.api.Test;

public class LoginTest {
    @Test
    public void testClienteLogin() {
        LoginBean loginBean = new LoginBean(
                "cliente@email.com",
                "password",
                "utente registrato");
        LoginController loginController = LoginController.getInstance();
        try {
            Cliente cliente = (Cliente) loginController.start(loginBean);
            System.out.println(cliente.toString());
        } catch (CredenzialiErrateException | UserNonSupportatoException | DAOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testOrganizzatoreLogin() {
        LoginBean loginBean = new LoginBean(
                "organizzatore@email.com",
                "password",
                "organizzatore");
        LoginController loginController = LoginController.getInstance();
        try {
            Organizzatore organizzatore = (Organizzatore) loginController.start(loginBean);
            System.out.println(organizzatore.toString());
        } catch (CredenzialiErrateException | DAOException | UserNonSupportatoException e) {
            throw new RuntimeException(e);
        }
    }

}
