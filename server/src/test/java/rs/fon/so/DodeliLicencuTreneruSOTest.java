package rs.fon.so;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.fon.baza.LicencaRepository;
import rs.fon.baza.TrenerLicencaRepository;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Licenca;
import rs.fon.domen.Trener;
import rs.fon.domen.TrenerLicenca;

class DodeliLicencuTreneruSOTest extends BazaTestBase {

    private final DodeliLicencuTreneruSO so = new DodeliLicencuTreneruSO();
    private final TrenerLicencaRepository repository = new TrenerLicencaRepository();
    private Trener trener;
    private Licenca licenca;

    @BeforeEach
    void dodajTreneraILicencu() throws Exception {
        trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        new TrenerRepository().add(trener);

        licenca = new Licenca(1, "Trenerska", "Pro");
        new LicencaRepository().add(licenca);
    }

    @Test
    void izvrsiOperaciju_dodeljujeLicencu() throws Exception {
        TrenerLicenca trenerLicenca = new TrenerLicenca(trener, licenca, new Date(), new Date(System.currentTimeMillis() + 100000));

        so.preduslovi(trenerLicenca);
        TrenerLicenca rezultat = so.izvrsiOperaciju(trenerLicenca);

        assertEquals(trenerLicenca, rezultat);
        assertEquals(trenerLicenca, repository.getById(trener.getIdTrener(), licenca.getIdLicence()));
    }

    @Test
    void preduslovi_bacaException_akoVecPostojiDodela() throws Exception {
        TrenerLicenca trenerLicenca = new TrenerLicenca(trener, licenca, new Date(), new Date(System.currentTimeMillis() + 100000));
        repository.add(trenerLicenca);

        assertThrows(Exception.class, () -> so.preduslovi(trenerLicenca));
    }
}
