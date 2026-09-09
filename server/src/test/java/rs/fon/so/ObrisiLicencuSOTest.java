package rs.fon.so;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.fon.baza.LicencaRepository;
import rs.fon.baza.TrenerLicencaRepository;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Licenca;
import rs.fon.domen.Trener;
import rs.fon.domen.TrenerLicenca;

class ObrisiLicencuSOTest extends BazaTestBase {

    private final ObrisiLicencuSO so = new ObrisiLicencuSO();
    private final LicencaRepository repository = new LicencaRepository();
    private Licenca licenca;

    @BeforeEach
    void dodajLicencu() throws Exception {
        licenca = new Licenca(1, "Trenerska", "Pro");
        repository.add(licenca);
    }

    @Test
    void izvrsiOperaciju_briseLicencu() throws Exception {
        so.preduslovi(licenca);
        so.izvrsiOperaciju(licenca);

        assertNull(repository.getById(licenca.getIdLicence()));
    }

    @Test
    void preduslovi_bacaException_akoLicencaNePostoji() {
        Licenca nepostojeca = new Licenca(999, "Nepostojeca", "Nivo");
        assertThrows(Exception.class, () -> so.preduslovi(nepostojeca));
    }

    @Test
    void izvrsiOperaciju_bacaException_akoJeLicencaDodeljenaTreneru() throws Exception {
        Trener trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        new TrenerRepository().add(trener);
        TrenerLicenca dodela = new TrenerLicenca(trener, licenca, new Date(), new Date(System.currentTimeMillis() + 100000));
        new TrenerLicencaRepository().add(dodela);

        assertThrows(Exception.class, () -> so.izvrsiOperaciju(licenca));
    }
}
