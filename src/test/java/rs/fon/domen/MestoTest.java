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

    @ParameterizedTest
    @CsvSource({
        "1, Beograd, 11000, true",
        "2, Novi Sad, 21000, false"
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