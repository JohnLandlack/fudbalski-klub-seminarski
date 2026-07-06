package rs.fon.domen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MestoTest {

    private Mesto mesto;

    @BeforeEach
    void setUp() {
        mesto = new Mesto(1, "Beograd", "11000");
    }

    @AfterEach
    void tearDown() {
        mesto = null;
    }

    @Test
    void konstruktorPostavljaAtribute() {
        assertEquals(1, mesto.getIdMesta());
        assertEquals("Beograd", mesto.getNaziv());
        assertEquals("11000", mesto.getPostanskiBroj());
    }
    
    @Test
    void setIdMesta_postavljaVrednost() {
        mesto.setIdMesta(5);
        assertEquals(5, mesto.getIdMesta());
    }
    
    @Test
    void setNaziv_postavljaVrednost() {
        mesto.setNaziv("Kikinda");
        assertEquals("Kikinda", mesto.getNaziv());
    }
    
    @Test
    void setPostanskiBroj_postavljaVrednost() {
        mesto.setPostanskiBroj("23300");
        assertEquals("23300", mesto.getPostanskiBroj());
    }

    @Test
    void setIdMesta_bacaException_kadaJeNulaIliManje() {
        assertThrows(IllegalArgumentException.class, () -> mesto.setIdMesta(0));
        assertThrows(IllegalArgumentException.class, () -> mesto.setIdMesta(-5));
    }

    @Test
    void setNaziv_bacaException_kadaJeNullIliPrazan() {
        assertThrows(NullPointerException.class, () -> mesto.setNaziv(null));
        assertThrows(IllegalArgumentException.class, () -> mesto.setNaziv(""));
    }

    @Test
    void setPostanskiBroj_bacaException_kadaJeNullIliPrazan() {
        assertThrows(NullPointerException.class, () -> mesto.setPostanskiBroj(null));
        assertThrows(IllegalArgumentException.class, () -> mesto.setPostanskiBroj("   "));
    }
    
    @Test
    void equals_vracaTrue_zaIstiObjekat(){
        assertTrue(mesto.equals(mesto));
    }
    
    void equals_vracaTrue_akoJeNull(){
        assertFalse(mesto.equals(null));
    }
    
    void equals_vracaTrue_akoJeDrugaKlasa(){
        assertFalse(mesto.equals(new Object()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Beograd, 11000, true",
        "2, Kikinda, 23300, false"
    })
    void equals_porediPoIdu(int id, String naziv, String postanskiBroj, boolean ocekivano) {
        Mesto drugoMesto = new Mesto(id, naziv, postanskiBroj);
        if (ocekivano) {
            assertEquals(mesto, drugoMesto);
        } else {
            assertNotEquals(mesto, drugoMesto);
        }
    }
}