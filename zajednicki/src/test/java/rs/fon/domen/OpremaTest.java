package rs.fon.domen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OpremaTest {

    private Oprema oprema;

    @BeforeEach
    void setUp() {
        oprema = new Oprema(1, "Domaci", "Letnji");
    }

    @AfterEach
    void tearDown() {
        oprema = null;
    }

    @Test
    void konstruktorPostavljaAtribute() {
        assertEquals(1, oprema.getIdOpreme());
        assertEquals("Domaci", oprema.getTipDresa());
        assertEquals("Letnji", oprema.getVrsteDresa());
    }

    @Test
    void setIdOpreme_postavljaVrednost() {
        oprema.setIdOpreme(2);
        assertEquals(2, oprema.getIdOpreme());
    }

    @Test
    void setTipDresa_postavljaVrednost() {
        oprema.setTipDresa("Gostujuci");
        assertEquals("Gostujuci", oprema.getTipDresa());
    }

    @Test
    void setVrsteDresa_postavljaVrednost() {
        oprema.setVrsteDresa("Zimski");
        assertEquals("Zimski", oprema.getVrsteDresa());
    }

    @Test
    void setIdOpreme_bacaException_kadaJeNula() {
        assertThrows(IllegalArgumentException.class, () -> oprema.setIdOpreme(0));
    }

    @Test
    void setTipDresa_bacaException_kadaJeNullIliPrazno() {
        assertThrows(NullPointerException.class, () -> oprema.setTipDresa(null));
        assertThrows(IllegalArgumentException.class, () -> oprema.setTipDresa(""));
    }

    @Test
    void setVrsteDresa_bacaException_kadaJeNullIliPrazno() {
        assertThrows(NullPointerException.class, () -> oprema.setVrsteDresa(null));
        assertThrows(IllegalArgumentException.class, () -> oprema.setVrsteDresa(""));
    }

    @Test
    void equals_vracaTrue_zaIstiObjekat() {
        assertTrue(oprema.equals(oprema));
    }

    @Test
    void equals_vracaFalse_akoJeNull() {
        assertFalse(oprema.equals(null));
    }

    @Test
    void equals_vracaFalse_akoJeDrugaKlasa() {
        assertFalse(oprema.equals(new Object()));
    }

    @Test
    void equals_vracaTrueZaIstuOpremu() {
        Oprema drugaOprema = new Oprema(1, "Gostujuci", "Zimski");
        assertEquals(oprema, drugaOprema); // Poredi po ID-u
    }

    @ParameterizedTest
    @CsvSource({
        "1, Domaci, Letnji, true",
        "2, Gostujuci, Zimski, false"
    })
    void equals_porediPoIdu(int id, String tip, String vrsta, boolean ocekivano) {
        Oprema druga = new Oprema(id, tip, vrsta);
        assertEquals(ocekivano, oprema.equals(druga));
    }
}
