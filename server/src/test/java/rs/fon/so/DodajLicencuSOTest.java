package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import rs.fon.baza.LicencaRepository;
import rs.fon.domen.Licenca;

class DodajLicencuSOTest extends BazaTestBase {

    private final DodajLicencuSO so = new DodajLicencuSO();

    @Test
    void izvrsiOperaciju_dodajeLicencuIDodeljujeId() throws Exception {
        Licenca licenca = new Licenca(1, "Trenerska", "Pro");

        so.preduslovi(licenca);
        Licenca rezultat = so.izvrsiOperaciju(licenca);

        assertEquals(1, rezultat.getIdLicence());
        assertEquals(licenca, new LicencaRepository().getById(1));
    }

    @Test
    void preduslovi_bacaException_akoJeLicencaNull() {
        assertThrows(NullPointerException.class, () -> so.preduslovi(null));
    }
}
