package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;

class IzmeniIgracaSOTest extends BazaTestBase {

    private final IzmeniIgracaSO so = new IzmeniIgracaSO();
    private final IgracRepository repository = new IgracRepository();
    private Igrac igrac;
    private Mesto mesto;

    @BeforeEach
    void dodajIgraca() throws Exception {
        mesto = new Mesto(1, "Beograd", "11000");
        new MestoRepository().add(mesto);
        igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);
        repository.add(igrac);
    }

    @Test
    void izvrsiOperaciju_menjaPodatkeIgraca() throws Exception {
        Igrac izmenjen = new Igrac(1, "Petar", "Petrovic", "0699999999", "Odbrana", mesto);

        so.preduslovi(izmenjen);
        so.izvrsiOperaciju(izmenjen);

        assertEquals("Odbrana", repository.getById(1).getPozicija());
    }

    @Test
    void preduslovi_bacaException_akoIgracNePostoji() {
        Igrac nepostojeci = new Igrac(999, "Nepostojeci", "Igrac", "000", "Rezerva", mesto);
        assertThrows(Exception.class, () -> so.preduslovi(nepostojeci));
    }

    @Test
    void preduslovi_bacaException_akoMestoNePostoji() {
        Mesto nepostojeceMesto = new Mesto(999, "Nepostojece", "00000");
        Igrac izmenjen = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", nepostojeceMesto);
        assertThrows(Exception.class, () -> so.preduslovi(izmenjen));
    }
}
