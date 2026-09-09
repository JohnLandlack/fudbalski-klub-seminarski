package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Trener;

class DodajTreneraSOTest extends BazaTestBase {

    private final DodajTreneraSO so = new DodajTreneraSO();

    @Test
    void izvrsiOperaciju_dodajeTreneraIDodeljujeId() throws Exception {
        Trener trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");

        so.preduslovi(trener);
        Trener rezultat = so.izvrsiOperaciju(trener);

        assertEquals(1, rezultat.getIdTrener());
        assertEquals(trener, new TrenerRepository().getById(1));
    }

    @Test
    void preduslovi_bacaException_akoJeTrenerNull() {
        assertThrows(NullPointerException.class, () -> so.preduslovi(null));
    }
}
