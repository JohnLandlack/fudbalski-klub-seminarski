package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Trener;

class UcitajTrenereSOTest extends BazaTestBase {

    private final UcitajTrenereSO so = new UcitajTrenereSO();

    @Test
    void izvrsiOperaciju_vracaSveTrenere() throws Exception {
        Trener prvi = new Trener(1, "Jovan", "Radojicic", "jovan", "sifra1");
        Trener drugi = new Trener(2, "Milija", "Radicevic", "milija", "sifra2");
        TrenerRepository repository = new TrenerRepository();
        repository.add(prvi);
        repository.add(drugi);

        so.preduslovi(null);
        List<Trener> rezultat = so.izvrsiOperaciju(null);

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(prvi));
        assertTrue(rezultat.contains(drugi));
    }
}
