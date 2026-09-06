package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import rs.fon.baza.LicencaRepository;
import rs.fon.domen.Licenca;

class DodajLicencuSOTest extends BazaTestBase {

    private final DodajLicencuSO so = new DodajLicencuSO();

    @Test
    void izvrsiOperaciju_dodajeLicencu() throws Exception {
        Licenca licenca = new Licenca(1, "Trenerska", "PRO");

        so.preduslovi(licenca);
        Licenca rezultat = so.izvrsiOperaciju(licenca);

        assertEquals(licenca, rezultat);
        assertEquals(licenca, new LicencaRepository().getById(1));
    }

    @Test
    void preduslovi_bacaException_akoIdVecPostoji() throws Exception {
        Licenca licenca = new Licenca(1, "Trenerska", "PRO");
        new LicencaRepository().add(licenca);

        Licenca duplikat = new Licenca(1, "Medicinska", "A");
        assertThrows(Exception.class, () -> so.preduslovi(duplikat));
    }
}
