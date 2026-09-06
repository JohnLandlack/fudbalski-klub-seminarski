package rs.fon.baza;

/**
 * Drži parametre konekcije ka bazi podataka. Podrazumevano pokazuje na
 * produkcionu bazu; URL se može promeniti (npr. u testovima, na test bazu).
 * * @author Jovan Radojičić
 */
public class Konfiguracija {

    private static String url = "jdbc:mysql://localhost:3306/ugovoriprojekat?useUnicode=true&characterEncoding=UTF-8";
    private static String username = "root";
    private static String password = "";

    private Konfiguracija() {
    }

    /**
     * Vraća URL konekcije ka bazi.
     * @return url kao String
     */
    public static String getUrl() {
        return url;
    }

    /**
     * Postavlja URL konekcije ka bazi (npr. za prebacivanje na test bazu).
     * @param noviUrl novi URL konekcije
     */
    public static void setUrl(String noviUrl) {
        url = noviUrl;
    }

    /**
     * Vraća korisničko ime za konekciju ka bazi.
     * @return username kao String
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Vraća lozinku za konekciju ka bazi.
     * @return password kao String
     */
    public static String getPassword() {
        return password;
    }
}
