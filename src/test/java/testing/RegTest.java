package testing;

import com.example.ispw2.engineering.factory.DAOFactory;
import com.example.ispw2.engineering.bean.RegisterBean;
import com.example.ispw2.controller.RegisterController;
import com.example.ispw2.engineering.exceptions.DAOException;
import com.example.ispw2.engineering.exceptions.EmailGiaInUsoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.altro.Adapter.ClienteAdapter;
import com.example.ispw2.altro.Connector;
import com.example.ispw2.altro.Wrapper.ClientiWrapper;
import com.google.gson.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

//FUNZIONA

public class RegTest {

    private Boolean testReg() {
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
        return true;
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

    @Test
    public void testRegistrazioneMYSQL(){
        changeType("MYSQL");
        DAOFactory.refreshDAOFactory(true);
        Boolean esito = testReg();
        eliminaUtenteMYSQL();
        assertTrue(esito);

    }
    @Test
    public void testRegistrazioneDEMO(){
        changeType("demo");
        DAOFactory.refreshDAOFactory(true);
        assertTrue(testReg());
    }
    @Test
    public void testRegistrazioneJSON(){
        changeType("JSON");
        DAOFactory.refreshDAOFactory(true);
        Boolean esito =testReg();
        eliminaUtenteJSON();
        assertTrue(esito);
    }

    private void eliminaUtenteJSON() {
        String FILE_PATH = "src/main/resources/persistence/users/cliente/clienteInfo.json";
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Cliente.class, new ClienteAdapter()) // se hai un adapter
                .create();
        try {
            ClientiWrapper wrapper;
            if (Files.exists(Path.of(FILE_PATH))) {
                try (Reader reader = Files.newBufferedReader(Path.of(FILE_PATH))) {
                    wrapper = gson.fromJson(reader, ClientiWrapper.class);
                    if (wrapper == null) wrapper = new ClientiWrapper();
                }
                wrapper.getClienti().removeIf(c -> c.getEmail().equalsIgnoreCase("test2@email.com"));
                try (Writer writer = Files.newBufferedWriter(Path.of(FILE_PATH))) {
                    gson.toJson(wrapper, writer);
                }
                System.out.println("Cliente rimosso con successo!");
            } else {
                System.out.println("File non trovato!");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void eliminaUtenteMYSQL(){
        //funzione che cancella utente di prova
        Connection conn = Connector.getConnection();
        PreparedStatement ps = null;
        String sql_s = "DELETE FROM ispw.User WHERE email = 'test2@email.com'";
        try {
            ps = conn.prepareStatement(sql_s);
            int rowsDeleted = ps.executeUpdate();
            conn.commit();
            System.out.println("righe cancellate: "+ rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
