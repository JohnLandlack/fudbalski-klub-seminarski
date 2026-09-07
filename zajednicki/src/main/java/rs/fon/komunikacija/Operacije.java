package rs.fon.komunikacija;

import java.io.Serializable;

/**
 * Enumeracija operacija koje klijent može zatražiti od servera preko soketa.
 * Svaka vrednost odgovara tačno jednoj sistemskoj operaciji na serveru.
 * @author Jovan Radojičić
 */
public enum Operacije implements Serializable {
    DODAJ_MESTO,
    DODAJ_LICENCU,
    DODAJ_TRENERA,
    DODAJ_OPREMU,
    DODAJ_IGRACA,
    DODAJ_UGOVOR,
    IZMENI_UGOVOR,
    OBRISI_UGOVOR,
    DODELI_LICENCU_TRENERU,
    PREUZMI_SVE_IGRACE,
    OBRISI_IGRACA,
    UCITAJ_MESTA,
    UCITAJ_TRENERE,
    UCITAJ_OPREMU,
    UCITAJ_LICENCE,
    UCITAJ_UGOVORE,
    LOGIN,
    IZMENI_IGRACA,
    OBRISI_MESTO,
    OBRISI_LICENCU,
    OBRISI_OPREMU
}
