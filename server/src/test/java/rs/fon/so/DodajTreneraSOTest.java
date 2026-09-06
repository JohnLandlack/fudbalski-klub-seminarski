package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Trener;

class DodajTreneraSOTest extends BazaTestBase {

    private final DodajTreneraSO so = new DodajTreneraSO();

    @Test
    void izvrsiOperaciju_dodajeTrenera() throws Exception {
        Trener trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");

        so.preduslovi(trener);
        Trener rezultat = so.izvrsiOperaciju(trener);

        assertEquals(trener, rezultat);
        assertEquals(trener, new TrenerRepository().getById(1));
    }

    @Test
    void preduslovi_bacaException_akoIdVecPostoji() throws Exception {
        Trener trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        new TrenerRepository().add(trener);

        Trener duplikat = new Trener(1, "Petar", "Petrovic", "petar123", "sifra456");
        assertThrows(Exception.class, () -> so.preduslovi(duplikat));
    }
}
