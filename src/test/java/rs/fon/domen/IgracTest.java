package rs.fon.domen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IgracTest {

    private Igrac igrac;
    private Mesto mesto;

    @BeforeEach
    void setUp() {
        mesto = new Mesto(1, "Beograd", "11000");
        igrac = new Igrac(1, "Aleksandar", "Mitrovic", "060123456", "Napadac", mesto);
    }

    @AfterEach
    void tearDown() {
        igrac = null;
        mesto = null;
    }

    @Test
    void konstruktorPostavljaAtribute() {
        assertEquals(1, igrac.getIdIgrac());
        assertEquals("Aleksandar", igrac.getIme());
        assertEquals("Mitrovic", igrac.getPrezime());
        assertEquals("060123456", igrac.getTelefon());
        assertEquals("Napadac", igrac.getPozicija());
        assertEquals(mesto, igrac.getMesto());
    }

    @Test
    void setIdIgrac_bacaException_kadaJeNula() {
        assertThrows(IllegalArgumentException.class, () -> igrac.setIdIgrac(0));
    }

    @Test
    void setIme_bacaException_kadaJeNullIliPrazno() {
        assertThrows(NullPointerException.class, () -> igrac.setIme(null));
        assertThrows(IllegalArgumentException.class, () -> igrac.setIme(""));
    }

    @Test
    void setMesto_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> igrac.setMesto(null));
    }

    @Test
    void equals_vracaTrueZaIstogIgraca() {
        Igrac drugiIgrac = new Igrac(1, "Dusan", "Tadic", "061987654", "Vezni", mesto);
        assertEquals(igrac, drugiIgrac); // Poredi po ID-u, tako da mora biti true
    }
}