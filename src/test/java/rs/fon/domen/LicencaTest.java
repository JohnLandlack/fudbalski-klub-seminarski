package rs.fon.domen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LicencaTest {

    private Licenca licenca;

    @BeforeEach
    void setUp() {
        licenca = new Licenca(1, "Trenerska", "PRO");
    }

    @AfterEach
    void tearDown() {
        licenca = null;
    }
    
    @Test
    void setIdLicence_postavljaVrednost(){
        licenca.setIdLicence(2);
        assertEquals(2, licenca.getIdLicence());
    }
    
    @Test
    void setTipLicence_postavljaVrednost(){
        licenca.setTipLicence("Medicinska");
        assertEquals("Medicinska", licenca.getTipLicence());
    }
    
    @Test
    void setNivoLicence_postavljaVrednost(){
        licenca.setNivoLicence("A");
        assertEquals("A", licenca.getNivoLicence());
    }

    @Test
    void setIdLicence_bacaException_kadaJeNula() {
        assertThrows(IllegalArgumentException.class, () -> licenca.setIdLicence(0));
    }

    @Test
    void setTipLicence_bacaException_kadaJeNullIliPrazan() {
        assertThrows(NullPointerException.class, () -> licenca.setTipLicence(null));
        assertThrows(IllegalArgumentException.class, () -> licenca.setTipLicence(""));
    }

    @Test
    void setNivoLicence_bacaException_kadaJeNullIliPrazan() {
        assertThrows(NullPointerException.class, () -> licenca.setNivoLicence(null));
        assertThrows(IllegalArgumentException.class, () -> licenca.setNivoLicence(""));
    }
    
    @Test
    void equals_True_istiObjekat() {
        assertTrue(licenca.equals(licenca));
    }
    
    @Test
    void equals_False_akoJeNull() {
        assertFalse(licenca.equals(null));
    }
    
    @Test
    void equals_False_drugaKlasa() {
        assertFalse(licenca.equals(new Object()));
    }
    
    @ParameterizedTest
    @CsvSource({
        "1, Trenerska, PRO, true",
        "2, Medicinska, A, false"
    })
    
    void equals_porediPoIdu(int id, String tip, String nivo, boolean ocekivano) {
        Licenca drugaLicenca = new Licenca(id, tip, nivo);
        assertEquals(ocekivano, licenca.equals(drugaLicenca));
    }
    
    

    @Test
    void toString_ispisujePravilanFormat() {
        assertEquals("Trenerska - PRO", licenca.toString());
    }
}