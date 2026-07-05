package rs.fon.domen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrenerTest {

    private Trener trener;

    @BeforeEach
    void setUp() {
        trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
    }

    @AfterEach
    void tearDown() {
        trener = null;
    }

    @Test
    void konstruktorPostavljaAtribute() {
        assertEquals(1, trener.getIdTrener());
        assertEquals("Marko", trener.getIme());
        assertEquals("Markovic", trener.getPrezime());
        assertEquals("marko123", trener.getKorisnickoIme());
        assertEquals("sifra123", trener.getSifra());
    }

    @Test
    void setIdTrener_bacaException_kadaJeNula() {
        assertThrows(IllegalArgumentException.class, () -> trener.setIdTrener(0));
    }

    @Test
    void setIme_bacaException_kadaJeNullIliPrazno() {
        assertThrows(NullPointerException.class, () -> trener.setIme(null));
        assertThrows(IllegalArgumentException.class, () -> trener.setIme(""));
    }

    @Test
    void equals_vracaTrueZaIstogTrenera() {
        Trener drugiTrener = new Trener(1, "Petar", "Petrovic", "pera", "pera123");
        assertEquals(trener, drugiTrener); // Poredi po ID-u
    }
}