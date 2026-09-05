package rs.fon.domen;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UgovorTest {

    private Ugovor ugovor;
    private Trener trener;
    private Igrac igrac;
    private Date datumPotpisivanja;

    @BeforeEach
    void setUp() {
        trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", new Mesto(1, "Beograd", "11000"));
        datumPotpisivanja = new Date();
        ugovor = new Ugovor(1, datumPotpisivanja, trener, igrac);
    }

    @AfterEach
    void tearDown() {
        ugovor = null;
        trener = null;
        igrac = null;
    }

    @Test
    void konstruktorPostavljaAtribute() {
        assertEquals(1, ugovor.getIdUgovor());
        assertEquals(datumPotpisivanja, ugovor.getDatumPotpisivanja());
        assertEquals(trener, ugovor.getTrener());
        assertEquals(igrac, ugovor.getIgrac());
        assertTrue(ugovor.getStavke().isEmpty());
    }

    @Test
    void setIdUgovor_postavljaVrednost() {
        ugovor.setIdUgovor(2);
        assertEquals(2, ugovor.getIdUgovor());
    }

    @Test
    void setDatumPotpisivanja_postavljaVrednost() {
        Date noviDatum = new Date();
        ugovor.setDatumPotpisivanja(noviDatum);
        assertEquals(noviDatum, ugovor.getDatumPotpisivanja());
    }

    @Test
    void setTrener_postavljaVrednost() {
        Trener noviTrener = new Trener(2, "Nikola", "Nikolic", "nikola123", "sifra456");
        ugovor.setTrener(noviTrener);
        assertEquals(noviTrener, ugovor.getTrener());
    }

    @Test
    void setIgrac_postavljaVrednost() {
        Igrac noviIgrac = new Igrac(2, "Stefan", "Stefanovic", "0651234567", "Golman", new Mesto(2, "Novi Sad", "21000"));
        ugovor.setIgrac(noviIgrac);
        assertEquals(noviIgrac, ugovor.getIgrac());
    }

    @Test
    void setStavke_postavljaVrednost() {
        StavkaUgovora stavka = new StavkaUgovora(ugovor, 1, new Date(), 1000, new Oprema(1, "Domaci", "Letnji"));
        ugovor.setStavke(java.util.List.of(stavka));
        assertEquals(1, ugovor.getStavke().size());
    }

    @Test
    void setIdUgovor_bacaException_kadaJeNula() {
        assertThrows(IllegalArgumentException.class, () -> ugovor.setIdUgovor(0));
    }

    @Test
    void setDatumPotpisivanja_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> ugovor.setDatumPotpisivanja(null));
    }

    @Test
    void setTrener_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> ugovor.setTrener(null));
    }

    @Test
    void setIgrac_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> ugovor.setIgrac(null));
    }

    @Test
    void setStavke_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> ugovor.setStavke(null));
    }

    @Test
    void equals_vracaTrue_zaIstiObjekat() {
        assertTrue(ugovor.equals(ugovor));
    }

    @Test
    void equals_vracaFalse_akoJeNull() {
        assertFalse(ugovor.equals(null));
    }

    @Test
    void equals_vracaFalse_akoJeDrugaKlasa() {
        assertFalse(ugovor.equals(new Object()));
    }

    @Test
    void equals_vracaTrueZaIstiUgovor() {
        Ugovor drugiUgovor = new Ugovor(1, new Date(), trener, igrac);
        assertEquals(ugovor, drugiUgovor); // Poredi po ID-u
    }

    @ParameterizedTest
    @CsvSource({
        "1, true",
        "2, false"
    })
    void equals_porediPoIdu(int id, boolean ocekivano) {
        Ugovor drugi = new Ugovor(id, new Date(), trener, igrac);
        assertEquals(ocekivano, ugovor.equals(drugi));
    }
}
