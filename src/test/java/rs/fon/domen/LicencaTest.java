package rs.fon.domen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    void toString_ispisujePravilanFormat() {
        assertEquals("Trenerska - PRO", licenca.toString());
    }
}