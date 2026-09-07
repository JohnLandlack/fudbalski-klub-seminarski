package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.baza.TrenerRepository;
import rs.fon.baza.UgovorRepository;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;
import rs.fon.domen.Trener;
import rs.fon.domen.Ugovor;

class UcitajUgovoreSOTest extends BazaTestBase {

    private final UcitajUgovoreSO so = new UcitajUgovoreSO();

    @Test
    void izvrsiOperaciju_vracaSveUgovore() throws Exception {
        Mesto mesto = new Mesto(1, "Beograd", "11000");
        new MestoRepository().add(mesto);
        Trener trener = new Trener(1, "Jovan", "Radojicic", "jovan", "sifra1");
        new TrenerRepository().add(trener);
        Igrac igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);
        new IgracRepository().add(igrac);

        Ugovor ugovor = new Ugovor(1, new Date(), trener, igrac);
        UgovorRepository repository = new UgovorRepository();
        repository.add(ugovor);

        so.preduslovi(null);
        List<Ugovor> rezultat = so.izvrsiOperaciju(null);

        assertEquals(1, rezultat.size());
        assertTrue(rezultat.contains(ugovor));
    }
}
