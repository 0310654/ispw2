package testing;

import com.example.ispw2.bean.RegisterBean;
import com.example.ispw2.controller.RegisterController;
import com.example.ispw2.exceptions.CredenzialiErrateException;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.exceptions.UserNonSupportatoException;
import com.example.ispw2.model.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegTest {
    @Test
    public void testReg() {
        RegisterBean registerBean = new RegisterBean(
                "nome",
                "cognome",
                "test2@email.com",
                "password2");
        RegisterController registerController = RegisterController.getInstance();
        try {
            assertTrue(registerController.registraCliente(registerBean));
        } catch (EmailGiaInUsoException e) {
            throw new RuntimeException(e);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }
}
