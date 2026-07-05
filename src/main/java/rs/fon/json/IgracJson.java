package rs.fon.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Klasa zaduzena za serijalizaciju i deserijalizaciju igraca u JSON fajl,
 * kao i za povlacenje nasumicnih podataka sa RandomUser Web API-ja.
 * * @author Jovan Radojičić
 */
public class IgracJson {

    private static final String RANDOM_USER_API_URL = "https://randomuser.me/api/?nat=rs&inc=name,cell";
    private final Gson gson;
    private final HttpClient httpClient;

    public IgracJson() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.httpClient = HttpClient.newHttpClient();
    }

    public void sacuvaj(Igrac igrac, String putanjaFajla) throws IOException {
        Path putanja = Path.of(putanjaFajla);
        try (Writer writer = Files.newBufferedWriter(putanja, StandardCharsets.UTF_8)) {
            gson.toJson(igrac, writer);
        }
    }

    public Igrac ucitaj(String putanjaFajla) throws IOException {
        Path putanja = Path.of(putanjaFajla);
        try (Reader reader = Files.newBufferedReader(putanja, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, Igrac.class);
        }
    }

    public Igrac ucitajNasumicnogIgraca() throws IOException, InterruptedException {
        Igrac igrac = new Igrac();
        HttpRequest zahtev = HttpRequest.newBuilder().uri(URI.create(RANDOM_USER_API_URL)).GET().build();
        HttpResponse<String> odgovor = httpClient.send(zahtev, HttpResponse.BodyHandlers.ofString());

        if (odgovor.statusCode() != 200) {
            throw new IOException("RandomUser API Error: " + odgovor.statusCode());
        }

        JsonObject koren = JsonParser.parseString(odgovor.body()).getAsJsonObject();
        JsonArray rezultati = koren.getAsJsonArray("results");
        JsonObject korisnik = rezultati.get(0).getAsJsonObject();
        JsonObject imeObjekat = korisnik.getAsJsonObject("name");

        igrac.setIme(imeObjekat.get("first").getAsString());
        igrac.setPrezime(imeObjekat.get("last").getAsString());
        igrac.setTelefon(korisnik.get("cell").getAsString());
        
        return igrac;
    }
}