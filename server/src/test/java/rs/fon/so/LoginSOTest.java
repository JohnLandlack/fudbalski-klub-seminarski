package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Trener;

class LoginSOTest extends BazaTestBase {

    private final LoginSO so = new LoginSO();
    private Trener trener;

    @BeforeEach
    void dodajTrenera() throws Exception {
        trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        new TrenerRepository().add(trener);
    }

    @Test
    void izvrsiOperaciju_vracaTrenera_akoSuPodaciIspravni() throws Exception {
        Trener pokusaj = new Trener(2, "Marko", "Markovic", "marko123", "sifra123");

        so.preduslovi(pokusaj);
        Trener rezultat = so.izvrsiOperaciju(pokusaj);

        assertEquals(trener, rezultat);
    }

    @Test
    void izvrsiOperaciju_bacaException_akoJeSifraPogresna() {
        Trener pokusaj = new Trener(2, "Marko", "Markovic", "marko123", "pogresnaSifra");

        assertThrows(Exception.class, () -> so.izvrsiOperaciju(pokusaj));
    }

    @Test
    void izvrsiOperaciju_bacaException_akoKorisnickoImeNePostoji() {
        Trener pokusaj = new Trener(2, "Nepostojeci", "Trener", "nepostojeci", "sifra123");

        assertThrows(Exception.class, () -> so.izvrsiOperaciju(pokusaj));
    }

    @Test
    void preduslovi_bacaException_akoJeTrenerNull() {
        assertThrows(NullPointerException.class, () -> so.preduslovi(null));
    }
}
