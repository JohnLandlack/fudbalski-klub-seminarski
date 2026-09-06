package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;

class PreuzmiSveIgraceSOTest extends BazaTestBase {

    private final PreuzmiSveIgraceSO so = new PreuzmiSveIgraceSO();

    @Test
    void izvrsiOperaciju_vracaSveIgrace() throws Exception {
        Mesto mesto = new Mesto(1, "Beograd", "11000");
        new MestoRepository().add(mesto);
        Igrac prvi = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);
        Igrac drugi = new Igrac(2, "Stefan", "Stefanovic", "0651234567", "Golman", mesto);
        IgracRepository repository = new IgracRepository();
        repository.add(prvi);
        repository.add(drugi);

        so.preduslovi(null);
        List<Igrac> rezultat = so.izvrsiOperaciju(null);

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(prvi));
        assertTrue(rezultat.contains(drugi));
    }
}
