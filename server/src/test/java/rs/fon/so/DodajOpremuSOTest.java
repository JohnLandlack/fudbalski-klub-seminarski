package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import rs.fon.baza.OpremaRepository;
import rs.fon.domen.Oprema;

class DodajOpremuSOTest extends BazaTestBase {

    private final DodajOpremuSO so = new DodajOpremuSO();

    @Test
    void izvrsiOperaciju_dodajeOpremuIDodeljujeId() throws Exception {
        Oprema oprema = new Oprema(1, "Prvi", "Domaći");

        so.preduslovi(oprema);
        Oprema rezultat = so.izvrsiOperaciju(oprema);

        assertEquals(1, rezultat.getIdOpreme());
        assertEquals(oprema, new OpremaRepository().getById(1));
    }
}
