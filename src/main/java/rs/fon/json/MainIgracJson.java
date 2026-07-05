package rs.fon.json;

import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;

public class MainIgracJson {

    public static void main(String[] args) {
        try {
            IgracJson jsonAlat = new IgracJson();
            String fajlPutanja = "igrac.json";

            System.out.println("=== 1. Upis i citanje iz lokalnog JSON fajla ===");
            Mesto mesto = new Mesto(1, "Beograd", "11000");
            Igrac igrac = new Igrac(1, "Dusan", "Vlahovic", "064111222", "Napadac", mesto);

            jsonAlat.sacuvaj(igrac, fajlPutanja);
            System.out.println("Igrac je uspesno sacuvan u fajl: " + fajlPutanja);

            Igrac ucitaniIgrac = jsonAlat.ucitaj(fajlPutanja);
            System.out.println("Ucitani igrac iz fajla: " + ucitaniIgrac + " (" + ucitaniIgrac.getPozicija() + ")");

            System.out.println("\n=== 2. Ucitavanje podataka sa RandomUser API-ja ===");
            Igrac nasumicanIgrac = jsonAlat.ucitajNasumicnogIgraca();
            System.out.println("API Ime: " + nasumicanIgrac.getIme());
            System.out.println("API Prezime: " + nasumicanIgrac.getPrezime());
            System.out.println("API Telefon: " + nasumicanIgrac.getTelefon());

            // Postavljamo preostale obavezne atribute pre cuvanja
            nasumicanIgrac.setIdIgrac(2);
            nasumicanIgrac.setPozicija("Vezni");
            nasumicanIgrac.setMesto(new Mesto(2, "Novi Sad", "21000"));

            jsonAlat.sacuvaj(nasumicanIgrac, "nasumican_igrac.json");
            System.out.println("Nasumican igrac uspesno konfigurisan i sacuvan.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}