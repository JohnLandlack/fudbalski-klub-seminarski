package rs.fon.so;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.baza.TrenerRepository;
import rs.fon.baza.UgovorRepository;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;
import rs.fon.domen.Trener;
import rs.fon.domen.Ugovor;

class DodajUgovorSOTest extends BazaTestBase {

    private final DodajUgovorSO so = new DodajUgovorSO();
    private Trener trener;
    private Igrac igrac;

    @BeforeEach
    void dodajTreneraIIgraca() throws Exception {
        trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        new TrenerRepository().add(trener);

        Mesto mesto = new Mesto(1, "Beograd", "11000");
        new MestoRepository().add(mesto);
        igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);
        new IgracRepository().add(igrac);
    }

    @Test
    void izvrsiOperaciju_dodajeUgovor() throws Exception {
        Ugovor ugovor = new Ugovor(1, new Date(), trener, igrac);

        so.preduslovi(ugovor);
        Ugovor rezultat = so.izvrsiOperaciju(ugovor);

        assertEquals(ugovor, rezultat);
        assertEquals(ugovor, new UgovorRepository().getById(1));
    }

    @Test
    void preduslovi_bacaException_akoTrenerNePostoji() {
        Trener nepostojeciTrener = new Trener(999, "Nepostojeci", "Trener", "nema", "nema");
        Ugovor ugovor = new Ugovor(2, new Date(), nepostojeciTrener, igrac);

        assertThrows(Exception.class, () -> so.preduslovi(ugovor));
    }

    @Test
    void preduslovi_bacaException_akoIgracNePostoji() {
        Igrac nepostojeciIgrac = new Igrac(999, "Nepostojeci", "Igrac", "000", "Rezerva", igrac.getMesto());
        Ugovor ugovor = new Ugovor(2, new Date(), trener, nepostojeciIgrac);

        assertThrows(Exception.class, () -> so.preduslovi(ugovor));
    }
}
