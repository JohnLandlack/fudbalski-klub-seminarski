package rs.fon.domen;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    void setTrener_postavljaVrednost() {
        Trener noviTrener = new Trener(2, "Petar", "Petrovic", "petar123", "sifra456");
        trenerLicenca.setTrener(noviTrener);
        assertEquals(noviTrener, trenerLicenca.getTrener());
    }

    @Test
    void setLicenca_postavljaVrednost() {
        Licenca novaLicenca = new Licenca(2, "Medicinska", "A");
        trenerLicenca.setLicenca(novaLicenca);
        assertEquals(novaLicenca, trenerLicenca.getLicenca());
    }

    @Test
    void setDatumIzdavanja_postavljaVrednost() {
        Date noviDatumIzdavanja = new Date();
        trenerLicenca.setDatumIzdavanja(noviDatumIzdavanja);
        assertEquals(noviDatumIzdavanja, trenerLicenca.getDatumIzdavanja());
    }

    @Test
    void setDatumIsteka_postavljaVrednost() {
        // Postavljamo datum u budućnosti (trenutno vreme + 10 dana) da sigurno bude validan
        Date noviDatumIsteka = new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000L);
        trenerLicenca.setDatumIsteka(noviDatumIsteka);
        assertEquals(noviDatumIsteka, trenerLicenca.getDatumIsteka());
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
    
    @Test
    void equals_vracaTrue_zaIstiObjekat() {
        assertTrue(trenerLicenca.equals(trenerLicenca));
    }

    @Test
    void equals_vracaFalse_akoJeNull() {
        assertFalse(trenerLicenca.equals(null));
    }

    @Test
    void equals_vracaFalse_akoJeDrugaKlasa() {
        assertFalse(trenerLicenca.equals(new Object()));
    }

    @Test
    void equals_vracaTrue_zaIsteReference() {
        TrenerLicenca druga = new TrenerLicenca(trener, licenca, datumIzdavanja, datumIsteka);
        assertTrue(trenerLicenca.equals(druga));
    }
    
    @ParameterizedTest
    @CsvSource({
        "1, 1, true",  
        "1, 2, false", 
        "2, 1, false", 
        "2, 2, false"  
    })
    void equals_porediPoTreneruILicenci(int idTrenera, int idLicence, boolean ocekivano) {
        Trener drugiTrener = new Trener(idTrenera, "Mika", "Mikic", "mika", "sifra");   
        Licenca drugaLicenca = new Licenca(idLicence, "A", "Nivo");        
        TrenerLicenca drugaTrenerLicenca = new TrenerLicenca(drugiTrener, drugaLicenca, new Date(), new Date());
        assertEquals(ocekivano, trenerLicenca.equals(drugaTrenerLicenca));
    }
    
    
}