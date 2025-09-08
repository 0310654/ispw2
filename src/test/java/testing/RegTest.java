package testing;

import com.example.ispw2.DAO.factory.DAOFactory;
import com.example.ispw2.bean.RegisterBean;
import com.example.ispw2.controller.RegisterController;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.view.gui.other.Adapter.ClienteAdapter;
import com.example.ispw2.view.gui.other.Connector;
import com.example.ispw2.view.gui.other.Wrapper.ClientiWrapper;
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

    private void testReg() {
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
        eliminaUtenteMYSQL();
        testReg();
    }
    @Test
    public void testRegistrazioneDEMO(){
        changeType("demo");
        DAOFactory.refreshDAOFactory(true);
        testReg();
    }
    @Test
    public void testRegistrazioneJSON(){
        changeType("JSON");
        DAOFactory.refreshDAOFactory(true);
        testReg();
        eliminaUtenteJSON();
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
            System.out.println("righe cancellate: "+ rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
