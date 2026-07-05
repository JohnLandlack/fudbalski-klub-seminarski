package rs.fon.domen;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrenerLicencaTest {

    private TrenerLicenca trenerLicenca;
    private Trener trener;
    private Licenca licenca;
    private Date datumIzdavanja;
    private Date datumIsteka;

    @BeforeEach
    void setUp() {
        trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        licenca = new Licenca(1, "Trenerska", "PRO");
        
        // Postavljamo datume tako da istek bude nakon izdavanja
        datumIzdavanja = new Date(System.currentTimeMillis() - 100000); 
        datumIsteka = new Date(System.currentTimeMillis() + 100000);
        
        trenerLicenca = new TrenerLicenca(trener, licenca, datumIzdavanja, datumIsteka);
    }

    @AfterEach
    void tearDown() {
        trenerLicenca = null;
        trener = null;
        licenca = null;
    }

    @Test
    void konstruktorPostavljaAtribute() {
        assertEquals(trener, trenerLicenca.getTrener());
        assertEquals(licenca, trenerLicenca.getLicenca());
        assertEquals(datumIzdavanja, trenerLicenca.getDatumIzdavanja());
        assertEquals(datumIsteka, trenerLicenca.getDatumIsteka());
    }

    @Test
    void setTrener_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> trenerLicenca.setTrener(null));
    }

    @Test
    void setLicenca_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> trenerLicenca.setLicenca(null));
    }

    @Test
    void setDatumIsteka_bacaException_kadaJePreDatumaIzdavanja() {
        Date stariDatum = new Date(datumIzdavanja.getTime() - 500000);
        assertThrows(IllegalArgumentException.class, () -> trenerLicenca.setDatumIsteka(stariDatum));
    }
}