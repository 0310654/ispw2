package com.example.ispw2.view.gui.other.Adapter;

import com.example.ispw2.model.Prenotazione;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrenotazioneAdapter extends TypeAdapter<Prenotazione> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void write(JsonWriter out, Prenotazione prenotazione) throws IOException {
        out.beginObject();
        out.name("nomeEvento").value(prenotazione.getNome_evento());
        out.name("codicePrenotazione").value(prenotazione.getCod_prenotazione());
        out.name("nomeUtente").value(prenotazione.getNome());
        out.name("cognomeUtente").value(prenotazione.getCognome());
        out.name("dataEvento").value(prenotazione.getData_evento() != null ? prenotazione.getData_evento().format(formatter) : null);
        out.name("dataPrenotazione").value(prenotazione.getData_prenotazione() != null ? prenotazione.getData_prenotazione().format(formatter) : null);
        out.name("stato").value(prenotazione.getStato_prenotazione());
        out.endObject();
    }


    @Override
    public Prenotazione read(JsonReader reader) throws IOException {
        String nomeEvento = null;
        String codicePrenotazione = null;
        String nomeUtente = null;
        String cognomeUtente = null;
        LocalDateTime dataEvento = null;
        LocalDateTime dataPrenotazione = null;
        String stato = null;

        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "nomeEvento": nomeEvento = reader.nextString(); break;
                case "codicePrenotazione": codicePrenotazione = reader.nextString(); break;
                case "nomeUtente": nomeUtente = reader.nextString(); break;
                case "cognomeUtente": cognomeUtente = reader.nextString(); break;
                case "dataEvento": dataEvento = LocalDateTime.parse(reader.nextString(), formatter); break;
                case "dataPrenotazione": dataPrenotazione = LocalDateTime.parse(reader.nextString(), formatter); break;
                case "stato": stato = reader.nextString(); break;
                default: reader.skipValue(); break;
            }
        }
        reader.endObject();
        return new Prenotazione(nomeEvento, codicePrenotazione, nomeUtente, cognomeUtente, dataEvento, dataPrenotazione, stato);
    }
}
