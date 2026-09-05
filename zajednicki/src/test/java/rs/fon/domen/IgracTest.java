package rs.fon.domen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    void setIdIgrac_postavljaVrednost() {
        igrac.setIdIgrac(5);
        assertEquals(5, igrac.getIdIgrac());
    }

    @Test
    void setIme_postavljaVrednost() {
        igrac.setIme("Dušan");
        assertEquals("Dušan", igrac.getIme());
    }

    @Test
    void setPrezime_postavljaVrednost() {
        igrac.setPrezime("Tadić");
        assertEquals("Tadić", igrac.getPrezime());
    }

    @Test
    void setTelefon_postavljaVrednost() {
        igrac.setTelefon("065999888");
        assertEquals("065999888", igrac.getTelefon());
    }

    @Test
    void setPozicija_postavljaVrednost() {
        igrac.setPozicija("Vezni");
        assertEquals("Vezni", igrac.getPozicija());
    }

    @Test
    void setMesto_postavljaVrednost() {
        Mesto novoMesto = new Mesto(2, "Novi Sad", "21000");
        igrac.setMesto(novoMesto);
        assertEquals(novoMesto, igrac.getMesto());
    }

    @Test
    void setIdIgrac_bacaException_kadaJeNula() {
        assertThrows(IllegalArgumentException.class, () -> igrac.setIdIgrac(0));
    }

    @Test
    void setIme_bacaException_kadaJeNullIliPrazno() {
        assertThrows(IllegalArgumentException.class, () -> igrac.setIme(""));
    }

    @Test
    void setIme_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> igrac.setIme(null));
    }

    @Test
    void setMesto_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> igrac.setMesto(null));
    }

    @Test
    void equals_vracaTrue_zaIstiObjekat() {
        assertTrue(igrac.equals(igrac));
    }

    @Test
    void equals_vracaFalse_akoJeNull() {
        assertFalse(igrac.equals(null));
    }

    @Test
    void equals_vracaFalse_akoJeDrugaKlasa() {
        assertFalse(igrac.equals(new Object()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Aleksandar, Mitrovic, Napadac, true",
        "2, Dusan, Tadic, Vezni, false"
    })
    void equals_porediPoIdu(int id, String ime, String prezime, String poz, boolean ocekivano) {
        Igrac drugi = new Igrac(id, ime, prezime, "060", poz, mesto);
        assertEquals(ocekivano, igrac.equals(drugi));
    }

    @Test
    void equals_vracaTrueZaIstogIgraca() {
        Igrac drugiIgrac = new Igrac(1, "Dusan", "Tadic", "061987654", "Vezni", mesto);
        assertEquals(igrac, drugiIgrac); // Poredi po ID-u, tako da mora biti true
    }
}
