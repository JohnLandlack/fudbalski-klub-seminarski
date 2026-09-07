package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import rs.fon.baza.OpremaRepository;
import rs.fon.domen.Oprema;

class UcitajOpremuSOTest extends BazaTestBase {

    private final UcitajOpremuSO so = new UcitajOpremuSO();

    @Test
    void izvrsiOperaciju_vracaSvuOpremu() throws Exception {
        Oprema prva = new Oprema(1, "Prvi", "Domaći");
        Oprema druga = new Oprema(2, "Drugi", "Gostujući");
        OpremaRepository repository = new OpremaRepository();
        repository.add(prva);
        repository.add(druga);

        so.preduslovi(null);
        List<Oprema> rezultat = so.izvrsiOperaciju(null);

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(prva));
        assertTrue(rezultat.contains(druga));
    }
}
