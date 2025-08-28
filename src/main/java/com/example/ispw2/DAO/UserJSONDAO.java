package com.example.ispw2.DAO;

import com.example.ispw2.bean.LoginBean;
import com.example.ispw2.exceptions.DAOException;
import com.example.ispw2.exceptions.EmailGiaInUsoException;
import com.example.ispw2.exceptions.UserNonTrovatoException;
import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Evento;
import com.example.ispw2.model.Organizzatore;
import com.example.ispw2.model.Prenotazione;
import com.example.ispw2.view.gui.other.ConfigurationJSN;
import com.example.ispw2.view.gui.other.Configurations;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import com.example.ispw2.view.gui.other.LocalDateAdapter;
import com.google.gson.*;
import org.w3c.dom.events.Event;


public class UserJSONDAO implements UserDAO {

    private static final String BASE_DIRECTORY = ConfigurationJSN.USER_BASE_DIRECTORY;
    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    @Override
    public void nuovoCliente(Cliente cliente) throws EmailGiaInUsoException, DAOException {

        Path userDirectory = null;

        try {
            // Verifica se la cartella persistence esiste, altrimenti la crea
            Path persistenceDirectory = Paths.get(ConfigurationJSN.PERSISTENCE_BASE_DIRECTORY);
            if (!Files.exists(persistenceDirectory)) {
                Files.createDirectories(persistenceDirectory);
            }

            // Verifica se l'utente esiste già
            if (checkIfUserExists(cliente.getEmail())) {
                throw new EmailGiaInUsoException();
            }

            // Crea la directory dell'utente e il file di informazioni
            userDirectory = Files.createDirectories(Paths.get(BASE_DIRECTORY, cliente.getEmail()));
            Path userInfoFile = userDirectory.resolve(ConfigurationJSN.USER_INFO_FILE_NAME);
            Path clienteInfoFile = userDirectory.resolve(ConfigurationJSN.COSTUMER_INFO_FILE_NAME);

            //divido le info per i due file
            var userInfo = new LoginBean(cliente.getEmail(), cliente.getPassword(), "utente registrato");
            var clienteInfo = new Cliente(cliente.getEmail(), cliente.getPassword(), cliente.getName(), cliente.getSurname(), LocalDate.now(), null);

            // Serializza l'oggetto Login in formato JSON e scrivi nel file
            String json = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(userInfo);
            Files.writeString(userInfoFile, json);
            json = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(clienteInfo);
            Files.writeString(clienteInfoFile, json);

        } catch (JsonIOException | IOException e) {

            if(userDirectory!=null) {
                try {
                    /*viene creata una classe anonima che estende SimpleFileVisitor<Path>.
                    La classe anonima permette di sovrascrivere i metodi visitFile e postVisitDirectory senza
                    dover dichiarare esplicitamente una nuova classe che estenda SimpleFileVisitor.
                    */
                    Files.walkFileTree(userDirectory, new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file); // Rimuove il file
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir); // Rimuove la directory vuota
                            return FileVisitResult.CONTINUE;
                        }
                    });
                } catch (IOException ex) {
                    log.severe(e.getMessage());
                    throw new DAOException("Errore in UserJSONDAO (rimuovo directory): " + e.getMessage());
                }
            }

            log.severe("Error in UserJSONDAO (inserimento cliente): " + e.getMessage());
            throw new DAOException();
        }

    }

    @Override
    public Prenotazione loadPrenotazione(String email) throws UserNonTrovatoException, DAOException {
        return null;
    }


    private boolean checkIfUserExists(String email) {
        // Costruito il percorso della directory dell'utente basandosi sulla mail come nome utente
        Path userDirectory = Paths.get(BASE_DIRECTORY, email);

        // Verifica se la directory dell'utente esiste
        return Files.exists(userDirectory);
    }



    @Override
    public Cliente loadCliente(String email) throws UserNonTrovatoException, DAOException {
        try {
            Path userInfoFile = Paths.get(BASE_DIRECTORY, ConfigurationJSN.COSTUMER_INFO_FILE_NAME);

            if (!Files.exists(userInfoFile)) {
                System.out.println("file non trovato, path:" + userInfoFile);
                throw new UserNonTrovatoException() ; // Lanciare l'eccezione se il file non esiste
            }

            String content = Files.readString(userInfoFile);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject root = gson.fromJson(content, JsonObject.class);

            JsonArray clienti = root.getAsJsonArray("clienti");
            for (JsonElement elem : clienti) {
                JsonObject clienteObj = elem.getAsJsonObject();

                if (clienteObj.getAsJsonPrimitive("email").getAsString().equals(email)) {

                    // Prendi la prenotazione
                    JsonObject prenotazioneObj = clienteObj.getAsJsonObject("prenotazione");

                    Prenotazione p = new Prenotazione(
                            prenotazioneObj.get("nomeEvento").getAsString(),
                            prenotazioneObj.get("codicePrenotazione").getAsString(),
                            prenotazioneObj.get("nomeUtente").getAsString(),
                            prenotazioneObj.get("cognomeUtente").getAsString(),
                            LocalDateTime.parse(prenotazioneObj.get("dataEvento").getAsString()),
                            LocalDateTime.parse(prenotazioneObj.get("dataPrenotazione").getAsString()),
                            prenotazioneObj.get("stato").getAsString()
                    );

                    // Ora puoi creare il Cliente con la prenotazione
                    return new Cliente(
                            email,
                            clienteObj.get("password").getAsString(),
                            clienteObj.get("nome").getAsString(),
                            clienteObj.get("cognome").getAsString(),
                            LocalDate.parse(clienteObj.get("data_registrazione").getAsString()),
                            p
                    );
                }
            }
            throw new UserNonTrovatoException();
        } catch (IOException e) {
            log.severe("Error in UserJSONDAO (getUserInfoByEmail): " + e.getMessage());
            throw new DAOException();
        }
    }


    @Override
    public Organizzatore loadOrganizzatore(String email) throws UserNonTrovatoException, DAOException {
        try {
            Path userInfoFile = Paths.get(BASE_DIRECTORY, ConfigurationJSN.ORGANIZZATORE_INFO_FILE_NAME);

            if (!Files.exists(userInfoFile)) {
                System.out.println("file non trovato, path:" + userInfoFile);
                throw new UserNonTrovatoException() ; // Lanciare l'eccezione se il file non esiste
            }

            String content = Files.readString(userInfoFile);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject root = gson.fromJson(content, JsonObject.class);

            JsonArray organizzatori = root.getAsJsonArray("organizzatori");
            for (JsonElement elem : organizzatori) {
                JsonObject organizzatoreObj = elem.getAsJsonObject();

                if (organizzatoreObj.getAsJsonPrimitive("email").getAsString().equals(email)) {
                    ArrayList<Evento> eventos = new ArrayList<>();

                    // Prendi l'evento
                    JsonArray eventi = organizzatoreObj.getAsJsonArray("mieiEventi");
                    for (JsonElement eventiElem : eventi) {

                        JsonObject eventoObj = eventiElem.getAsJsonObject();

                        ArrayList<String> nomeSettore = new ArrayList<>();
                        ArrayList<Integer> disponibilita_settore = new ArrayList<>();
                        ArrayList<Double> prezzo_settore = new ArrayList<>();
                        ArrayList<Integer>  num_posti_settore = new ArrayList<>();

                        JsonArray settori = eventoObj.getAsJsonArray("settori");

                        for (JsonElement settore : settori) {
                            nomeSettore.add(settore.getAsJsonObject().get("nome").getAsString());
                            disponibilita_settore.add(settore.getAsJsonObject().get("disponibilita").getAsInt());
                            prezzo_settore.add(settore.getAsJsonObject().get("prezzo").getAsDouble());
                            num_posti_settore.add(settore.getAsJsonObject().get("posti totali").getAsInt());
                        }

                        Evento e = new Evento(
                                eventoObj.get("id").getAsString(),
                                eventoObj.get("nome").getAsString(),
                                email,
                                eventoObj.get("ente").getAsString(),
                                eventoObj.get("tipo").getAsString(),
                                eventoObj.get("localita").getAsString(),
                                nomeSettore,
                                disponibilita_settore,
                                prezzo_settore,
                                LocalDateTime.parse(eventoObj.get("data").getAsString()),
                                num_posti_settore,
                                eventoObj.get("descrizione").getAsString()
                        );

                        eventos.add(e);
                    }

                    return new Organizzatore(
                            email,
                            organizzatoreObj.get("password").getAsString(),
                            organizzatoreObj.get("nome").getAsString(),
                            organizzatoreObj.get("cognome").getAsString(),
                            LocalDate.parse(organizzatoreObj.get("data_registrazione").getAsString()),
                            eventos
                    );
                }
            }
            throw new UserNonTrovatoException();
        } catch (IOException e) {
            log.severe("Error in UserJSONDAO (loadOrganizzatore): " + e.getMessage());
            throw new DAOException();
        }
    }

    @Override
    public LoginBean getUserInfoByEmail(String email) throws UserNonTrovatoException, DAOException {
        try {
            Path userInfoFile = Paths.get(BASE_DIRECTORY, ConfigurationJSN.USER_INFO_FILE_NAME);

            if (!Files.exists(userInfoFile)) {
                throw new UserNonTrovatoException() ; // Lanciare l'eccezione se il file non esiste
            }

            String content = Files.readString(userInfoFile);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject root = gson.fromJson(content, JsonObject.class);

            JsonArray utenti = root.getAsJsonArray("utenti");
            for (JsonElement elem : utenti) {

                JsonObject utente = elem.getAsJsonObject();
                if (utente.getAsJsonPrimitive("email").getAsString().equals(email)) {

                    return new LoginBean(
                            email,
                            utente.getAsJsonPrimitive("password").getAsString(),
                            utente.getAsJsonPrimitive("type").getAsString()
                    );
                }
            }
            throw new UserNonTrovatoException();

        } catch (IOException e) {
            log.severe("Error in UserJSONDAO (getUserInfoByEmail): " + e.getMessage());
            throw new DAOException();
        }
    }

}

