package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import rs.fon.baza.LicencaRepository;
import rs.fon.domen.Licenca;

class UcitajLicenceSOTest extends BazaTestBase {

    private final UcitajLicenceSO so = new UcitajLicenceSO();

    @Test
    void izvrsiOperaciju_vracaSveLicence() throws Exception {
        Licenca prva = new Licenca(1, "Trenerska", "Početni");
        Licenca druga = new Licenca(2, "Medicinska", "Pro");
        LicencaRepository repository = new LicencaRepository();
        repository.add(prva);
        repository.add(druga);

        so.preduslovi(null);
        List<Licenca> rezultat = so.izvrsiOperaciju(null);

        assertEquals(2, rezultat.size());
        assertTrue(rezultat.contains(prva));
        assertTrue(rezultat.contains(druga));
    }
}
