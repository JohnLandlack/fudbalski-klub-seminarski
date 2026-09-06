package rs.fon.so;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import rs.fon.baza.Konekcija;
import rs.fon.baza.Konfiguracija;

/**
 * Zajednička osnova za testove sistemskih operacija koji rade nad pravom
 * (test) bazom podataka. Pre svih testova u klasi prebacuje konekciju na
 * test bazu, a posle vraća na produkcionu. Svaki pojedinačni test radi u
 * sopstvenoj transakciji koja se na kraju uvek poništava (rollback), tako da
 * test baza ostaje čista bez ručnog brisanja podataka.
 * * @author Jovan Radojičić
 */
public abstract class BazaTestBase {

    private static final String TEST_URL =
            "jdbc:mysql://localhost:3306/ugovoriprojekat_test?useUnicode=true&characterEncoding=UTF-8";

    private static String produkcioniUrl;

    @BeforeAll
    static void prebaciNaTestBazu() {
        produkcioniUrl = Konfiguracija.getUrl();
        Konfiguracija.setUrl(TEST_URL);
    }

    @AfterAll
    static void vratiProdukcionuBazu() throws Exception {
        Konekcija.zatvoriKonekciju();
        Konfiguracija.setUrl(produkcioniUrl);
    }

    @BeforeEach
    void otvoriKonekciju() throws Exception {
        Konekcija.getConnection();
    }

    @AfterEach
    void ocistiBazu() throws Exception {
        Konekcija.getConnection().rollback();
    }
}
