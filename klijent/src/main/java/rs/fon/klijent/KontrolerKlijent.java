package rs.fon.klijent;

import java.util.List;
import rs.fon.domen.Igrac;
import rs.fon.domen.Licenca;
import rs.fon.domen.Mesto;
import rs.fon.domen.Oprema;
import rs.fon.domen.Trener;
import rs.fon.domen.TrenerLicenca;
import rs.fon.domen.Ugovor;
import rs.fon.komunikacija.KlijentskiZahtev;
import rs.fon.komunikacija.Operacije;
import rs.fon.komunikacija.ServerskiOdgovor;

/**
 * Singleton koji za svaku sistemsku operaciju šalje odgovarajući zahtev
 * serveru preko {@link Komunikacija} i vraća rezultat, ili prosleđuje
 * izuzetak ako je server prijavio grešku.
 * @author Jovan Radojičić
 */
public class KontrolerKlijent {

    private static KontrolerKlijent instance;

    private KontrolerKlijent() {
    }

    /**
     * Vraća jedinu instancu kontrolera.
     * @return instanca kontrolera
     */
    public static KontrolerKlijent getInstance() {
        if (instance == null) {
            instance = new KontrolerKlijent();
        }
        return instance;
    }

    private Object posalji(Operacije operacija, Object parametar) throws Exception {
        ServerskiOdgovor odgovor = Komunikacija.getInstance().posaljiZahtev(new KlijentskiZahtev(operacija, parametar));
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
        return odgovor.getOdgovor();
    }

    public Trener login(String korisnickoIme, String sifra) throws Exception {
        Trener zaLogin = new Trener();
        zaLogin.setKorisnickoIme(korisnickoIme);
        zaLogin.setSifra(sifra);
        return (Trener) posalji(Operacije.LOGIN, zaLogin);
    }

    public List<Mesto> vratiMesta() throws Exception {
        return (List<Mesto>) posalji(Operacije.UCITAJ_MESTA, null);
    }

    public void dodajMesto(Mesto mesto) throws Exception {
        posalji(Operacije.DODAJ_MESTO, mesto);
    }

    public void obrisiMesto(Mesto mesto) throws Exception {
        posalji(Operacije.OBRISI_MESTO, mesto);
    }

    public List<Oprema> vratiOpremu() throws Exception {
        return (List<Oprema>) posalji(Operacije.UCITAJ_OPREMU, null);
    }

    public void dodajOpremu(Oprema oprema) throws Exception {
        posalji(Operacije.DODAJ_OPREMU, oprema);
    }

    public void obrisiOpremu(Oprema oprema) throws Exception {
        posalji(Operacije.OBRISI_OPREMU, oprema);
    }

    public List<Licenca> vratiLicence() throws Exception {
        return (List<Licenca>) posalji(Operacije.UCITAJ_LICENCE, null);
    }

    public void dodajLicencu(Licenca licenca) throws Exception {
        posalji(Operacije.DODAJ_LICENCU, licenca);
    }

    public void obrisiLicencu(Licenca licenca) throws Exception {
        posalji(Operacije.OBRISI_LICENCU, licenca);
    }

    public List<Trener> vratiTrenere() throws Exception {
        return (List<Trener>) posalji(Operacije.UCITAJ_TRENERE, null);
    }

    public void dodajTrenera(Trener trener) throws Exception {
        posalji(Operacije.DODAJ_TRENERA, trener);
    }

    public void dodeliLicencuTreneru(TrenerLicenca trenerLicenca) throws Exception {
        posalji(Operacije.DODELI_LICENCU_TRENERU, trenerLicenca);
    }

    public List<Igrac> vratiIgrace() throws Exception {
        return (List<Igrac>) posalji(Operacije.PREUZMI_SVE_IGRACE, null);
    }

    public void dodajIgraca(Igrac igrac) throws Exception {
        posalji(Operacije.DODAJ_IGRACA, igrac);
    }

    public void izmeniIgraca(Igrac igrac) throws Exception {
        posalji(Operacije.IZMENI_IGRACA, igrac);
    }

    public void obrisiIgraca(Igrac igrac) throws Exception {
        posalji(Operacije.OBRISI_IGRACA, igrac);
    }

    public List<Ugovor> vratiUgovore() throws Exception {
        return (List<Ugovor>) posalji(Operacije.UCITAJ_UGOVORE, null);
    }

    public void dodajUgovor(Ugovor ugovor) throws Exception {
        posalji(Operacije.DODAJ_UGOVOR, ugovor);
    }

    public void izmeniUgovor(Ugovor ugovor) throws Exception {
        posalji(Operacije.IZMENI_UGOVOR, ugovor);
    }

    public void obrisiUgovor(Ugovor ugovor) throws Exception {
        posalji(Operacije.OBRISI_UGOVOR, ugovor);
    }
}
