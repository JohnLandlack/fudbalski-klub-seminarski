package rs.fon.domen;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StavkaUgovoraTest {

    private StavkaUgovora stavka;
    private Ugovor ugovor;
    private Oprema oprema;
    private Date vazenjeUgovora;

    @BeforeEach
    void setUp() {
        Trener trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        Igrac igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", new Mesto(1, "Beograd", "11000"));
        ugovor = new Ugovor(1, new Date(), trener, igrac);
        oprema = new Oprema(1, "Domaci", "Letnji");
        vazenjeUgovora = new Date();
        stavka = new StavkaUgovora(ugovor, 1, vazenjeUgovora, 1000, oprema);
    }

    @AfterEach
    void tearDown() {
        stavka = null;
        ugovor = null;
        oprema = null;
    }

    @Test
    void konstruktorPostavljaAtribute() {
        assertEquals(ugovor, stavka.getUgovor());
        assertEquals(1, stavka.getRbStavkaUgovora());
        assertEquals(vazenjeUgovora, stavka.getVazenjeUgovora());
        assertEquals(1000, stavka.getPlata());
        assertEquals(oprema, stavka.getOprema());
    }

    @Test
    void setUgovor_postavljaVrednost() {
        Ugovor drugiUgovor = new Ugovor(2, new Date(), ugovor.getTrener(), ugovor.getIgrac());
        stavka.setUgovor(drugiUgovor);
        assertEquals(drugiUgovor, stavka.getUgovor());
    }

    @Test
    void setRbStavkaUgovora_postavljaVrednost() {
        stavka.setRbStavkaUgovora(2);
        assertEquals(2, stavka.getRbStavkaUgovora());
    }

    @Test
    void setVazenjeUgovora_postavljaVrednost() {
        Date noviDatum = new Date();
        stavka.setVazenjeUgovora(noviDatum);
        assertEquals(noviDatum, stavka.getVazenjeUgovora());
    }

    @Test
    void setPlata_postavljaVrednost() {
        stavka.setPlata(2000);
        assertEquals(2000, stavka.getPlata());
    }

    @Test
    void setOprema_postavljaVrednost() {
        Oprema novaOprema = new Oprema(2, "Gostujuci", "Zimski");
        stavka.setOprema(novaOprema);
        assertEquals(novaOprema, stavka.getOprema());
    }

    @Test
    void setUgovor_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> stavka.setUgovor(null));
    }

    @Test
    void setRbStavkaUgovora_bacaException_kadaJeNula() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setRbStavkaUgovora(0));
    }

    @Test
    void setVazenjeUgovora_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> stavka.setVazenjeUgovora(null));
    }

    @Test
    void setPlata_bacaException_kadaJeNula() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setPlata(0));
    }

    @Test
    void setOprema_bacaException_kadaJeNull() {
        assertThrows(NullPointerException.class, () -> stavka.setOprema(null));
    }

    @Test
    void equals_vracaTrue_zaIstiObjekat() {
        assertTrue(stavka.equals(stavka));
    }

    @Test
    void equals_vracaFalse_akoJeNull() {
        assertFalse(stavka.equals(null));
    }

    @Test
    void equals_vracaFalse_akoJeDrugaKlasa() {
        assertFalse(stavka.equals(new Object()));
    }

    @Test
    void equals_vracaTrueZaIstuStavku() {
        StavkaUgovora drugaStavka = new StavkaUgovora(ugovor, 1, new Date(), 5000, new Oprema(2, "Gostujuci", "Zimski"));
        assertEquals(stavka, drugaStavka); // Poredi po ugovoru i rednom broju
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1, true",
        "1, 2, false",
        "2, 1, false",
        "2, 2, false"
    })
    void equals_porediPoUgovoruIRednomBroju(int idUgovora, int rb, boolean ocekivano) {
        Ugovor drugiUgovor = new Ugovor(idUgovora, new Date(), ugovor.getTrener(), ugovor.getIgrac());
        StavkaUgovora druga = new StavkaUgovora(drugiUgovor, rb, new Date(), 1000, oprema);
        assertEquals(ocekivano, stavka.equals(druga));
    }
}
