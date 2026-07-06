package rs.fon.domen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    void setIdTrener_postavljaVrednost() {
        trener.setIdTrener(2);
        assertEquals(2, trener.getIdTrener());
    }

    @Test
    void setIme_postavljaVrednost() {
        trener.setIme("Petar");
        assertEquals("Petar", trener.getIme());
    }

    @Test
    void setPrezime_postavljaVrednost() {
        trener.setPrezime("Petrovic");
        assertEquals("Petrovic", trener.getPrezime());
    }

    @Test
    void setKorisnickoIme_postavljaVrednost() {
        trener.setKorisnickoIme("petar123");
        assertEquals("petar123", trener.getKorisnickoIme());
    }

    @Test
    void setSifra_postavljaVrednost() {
        trener.setSifra("novaSifra123");
        assertEquals("novaSifra123", trener.getSifra());
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
    void equals_vracaTrue_zaIstiObjekat() {
        assertTrue(trener.equals(trener));
    }
    
    @Test
    void equals_vracaFalse_akoJeNull() {
        assertFalse(trener.equals(null));
    }

    @Test
    void equals_vracaFalse_akoJeDrugaKlasa() {
        assertFalse(trener.equals(new Object()));
    }

    @Test
    void equals_vracaTrueZaIstogTrenera() {
        Trener drugiTrener = new Trener(1, "Petar", "Petrovic", "pera", "pera123");
        assertEquals(trener, drugiTrener); // Poredi po ID-u
    }
    
    @ParameterizedTest
    @CsvSource({
        "1, Marko, Markovic, user, pass, true",
        "2, Petar, Petrovic, user2, pass2, false"
    })
    void equals_porediPoIdu(int id, String ime, String prezime, String user, String pass, boolean ocekivano) {
        Trener drugi = new Trener(id, ime, prezime, user, pass);
        assertEquals(ocekivano, trener.equals(drugi));
    }
}
