package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;

class DodajIgracaSOTest extends BazaTestBase {

    private final DodajIgracaSO so = new DodajIgracaSO();
    private Mesto mesto;

    @BeforeEach
    void dodajMesto() throws Exception {
        mesto = new Mesto(1, "Beograd", "11000");
        new MestoRepository().add(mesto);
    }

    @Test
    void izvrsiOperaciju_dodajeIgracaIDodeljujeId() throws Exception {
        Igrac igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);

        so.preduslovi(igrac);
        Igrac rezultat = so.izvrsiOperaciju(igrac);

        assertEquals(1, rezultat.getIdIgrac());
        assertEquals(igrac, new IgracRepository().getById(1));
    }

    @Test
    void preduslovi_bacaException_akoMestoNePostoji() {
        Mesto nepostojeceMesto = new Mesto(999, "Nepostojece", "00000");
        Igrac igrac = new Igrac(2, "Petar", "Petrovic", "0641234567", "Napadac", nepostojeceMesto);

        assertThrows(Exception.class, () -> so.preduslovi(igrac));
    }
}
