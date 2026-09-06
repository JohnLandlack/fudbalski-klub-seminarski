package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import rs.fon.baza.OpremaRepository;
import rs.fon.domen.Oprema;

class DodajOpremuSOTest extends BazaTestBase {

    private final DodajOpremuSO so = new DodajOpremuSO();

    @Test
    void izvrsiOperaciju_dodajeOpremu() throws Exception {
        Oprema oprema = new Oprema(1, "Domaci", "Letnji");

        so.preduslovi(oprema);
        Oprema rezultat = so.izvrsiOperaciju(oprema);

        assertEquals(oprema, rezultat);
        assertEquals(oprema, new OpremaRepository().getById(1));
    }

    @Test
    void preduslovi_bacaException_akoIdVecPostoji() throws Exception {
        Oprema oprema = new Oprema(1, "Domaci", "Letnji");
        new OpremaRepository().add(oprema);

        Oprema duplikat = new Oprema(1, "Gostujuci", "Zimski");
        assertThrows(Exception.class, () -> so.preduslovi(duplikat));
    }
}
