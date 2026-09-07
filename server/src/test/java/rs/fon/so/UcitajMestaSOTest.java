package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Mesto;

class UcitajMestaSOTest extends BazaTestBase {

    private final UcitajMestaSO so = new UcitajMestaSO();

    @Test
    void izvrsiOperaciju_vracaSvaMesta() throws Exception {
        Mesto prvo = new Mesto(1, "Beograd", "11000");
        Mesto drugo = new Mesto(2, "Novi Sad", "21000");
        MestoRepository repository = new MestoRepository();
        repository.add(prvo);
        repository.add(drugo);

        so.preduslovi(null);
        List<Mesto> rezultat = so.izvrsiOperaciju(null);

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(prvo));
        assertTrue(rezultat.contains(drugo));
    }
}
