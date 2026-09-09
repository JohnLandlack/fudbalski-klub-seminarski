package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;

class ObrisiMestoSOTest extends BazaTestBase {

    private final ObrisiMestoSO so = new ObrisiMestoSO();
    private final MestoRepository repository = new MestoRepository();
    private Mesto mesto;

    @BeforeEach
    void dodajMesto() throws Exception {
        mesto = new Mesto(1, "Beograd", "11000");
        repository.add(mesto);
    }

    @Test
    void izvrsiOperaciju_briseMesto() throws Exception {
        so.preduslovi(mesto);
        so.izvrsiOperaciju(mesto);

        assertNull(repository.getById(mesto.getIdMesta()));
    }

    @Test
    void preduslovi_bacaException_akoMestoNePostoji() {
        Mesto nepostojece = new Mesto(999, "Nepostojece", "00000");
        assertThrows(Exception.class, () -> so.preduslovi(nepostojece));
    }

    @Test
    void izvrsiOperaciju_bacaException_akoMestoImaPovezanogIgraca() throws Exception {
        Igrac igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);
        new IgracRepository().add(igrac);

        assertThrows(Exception.class, () -> so.izvrsiOperaciju(mesto));
    }
}
