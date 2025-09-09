package com.example.ispw2.altro.Adapter;

import com.example.ispw2.model.Cliente;
import com.example.ispw2.model.Prenotazione;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClienteAdapter extends TypeAdapter<Cliente> {

    @Override
    public void write(JsonWriter jsonWriter, Cliente cliente) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("email").value(cliente.getEmail());
        jsonWriter.name("password").value(cliente.getPassword());
        jsonWriter.name("nome").value(cliente.getName());
        jsonWriter.name("cognome").value(cliente.getSurname());
        jsonWriter.name("data_registrazione").value(String.valueOf(cliente.getdata_registrazione()));
        jsonWriter.name("prenotazione");
        if (cliente.getPrenotazioni_pendenti() != null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Prenotazione.class, new PrenotazioneAdapter()).setPrettyPrinting().create();
            gson.toJson(cliente.getPrenotazioni_pendenti(), Prenotazione.class, jsonWriter);

        } else {
            jsonWriter.nullValue();
        }
        jsonWriter.endObject();
    }

    @Override
    public Cliente read(JsonReader jsonReader) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String email="";
        String password="";
        String nome = "";
        String cognome="";
        String data_registrazione="";
        Prenotazione prenotazione=null;
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String riga = jsonReader.nextName();
            switch (riga) {
                case "email":
                    email = jsonReader.nextString();
                    break;
                case "password" :
                    password = jsonReader.nextString();
                    break;
                case "nome":
                    nome = jsonReader.nextString();
                    break;
                case "cognome":
                    cognome = jsonReader.nextString();
                    break;
                case "data_registrazione":
                    data_registrazione = jsonReader.nextString();
                    break;
                case "prenotazione":
                    Gson gsonPrenotazione = new GsonBuilder()
                            .registerTypeAdapter(Prenotazione.class, new PrenotazioneAdapter())
                            .create();
                    prenotazione = gsonPrenotazione.fromJson(jsonReader, Prenotazione.class);
                    break;
                default:
                    jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return new Cliente(email, password, nome, cognome, LocalDate.parse(data_registrazione,formatter), prenotazione);
    }

   /* @Override
    public JsonElement serialize(LoginBean src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("email", src.getEmail());
        obj.addProperty("password", src.getPassword());
        obj.addProperty("type", src.getType());
        return obj;
    }

    @Override
    public LoginBean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        String email = obj.has("email") && !obj.get("email").isJsonNull() ? obj.get("email").getAsString() : null;
        String password = obj.has("password") && !obj.get("password").isJsonNull() ? obj.get("password").getAsString() : null;
        String type = obj.has("type") && !obj.get("type").isJsonNull() ? obj.get("type").getAsString() : null;

        return new LoginBean(email, password, type);
    }*/
}

