package rs.fon.so;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertNull;
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

class ObrisiIgracaSOTest extends BazaTestBase {

    private final ObrisiIgracaSO so = new ObrisiIgracaSO();
    private final IgracRepository repository = new IgracRepository();
    private Igrac igrac;

    @BeforeEach
    void dodajIgraca() throws Exception {
        Mesto mesto = new Mesto(1, "Beograd", "11000");
        new MestoRepository().add(mesto);
        igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);
        repository.add(igrac);
    }

    @Test
    void izvrsiOperaciju_briseIgraca() throws Exception {
        so.preduslovi(igrac);
        so.izvrsiOperaciju(igrac);

        assertNull(repository.getById(igrac.getIdIgrac()));
    }

    @Test
    void preduslovi_bacaException_akoIgracNePostoji() {
        Igrac nepostojeci = new Igrac(999, "Nepostojeci", "Igrac", "000", "Rezerva", igrac.getMesto());
        assertThrows(Exception.class, () -> so.preduslovi(nepostojeci));
    }

    @Test
    void izvrsiOperaciju_bacaException_akoIgracImaAktivanUgovor() throws Exception {
        Trener trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        new TrenerRepository().add(trener);
        new UgovorRepository().add(new Ugovor(1, new Date(), trener, igrac));

        assertThrows(Exception.class, () -> so.izvrsiOperaciju(igrac));
    }
}
