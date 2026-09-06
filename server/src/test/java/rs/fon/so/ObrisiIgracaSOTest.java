package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;

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
}
