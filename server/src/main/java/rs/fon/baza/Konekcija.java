package rs.fon.baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Drži jedinstvenu konekciju ka bazi podataka (transakciona — autocommit
 * isključen), otvorenu prema parametrima iz {@link Konfiguracija}.
 *
 * @author Jovan Radojičić
 */
public class Konekcija {

    private static Connection konekcija;

    private Konekcija() {
    }

    /**
     * Vraća otvorenu konekciju ka bazi, otvarajući novu ako trenutna ne
     * postoji ili je zatvorena.
     * @return konekcija ka bazi podataka
     * @throws SQLException ako konekcija ne može da se uspostavi
     */
    public static Connection getConnection() throws SQLException {
        if (konekcija == null || konekcija.isClosed()) {
            konekcija = DriverManager.getConnection(
                    Konfiguracija.getUrl(), Konfiguracija.getUsername(), Konfiguracija.getPassword());
            konekcija.setAutoCommit(false);
        }
        return konekcija;
    }

    /**
     * Zatvara trenutnu konekciju ka bazi, ako postoji.
     * @throws SQLException ako zatvaranje konekcije ne uspe
     */
    public static void zatvoriKonekciju() throws SQLException {
        if (konekcija != null && !konekcija.isClosed()) {
            konekcija.close();
        }
        konekcija = null;
    }
}
