package rs.fon.komunikacija;

import java.io.Serializable;

/**
 * Predstavlja odgovor koji server šalje klijentu preko soketa: rezultat
 * izvršene operacije, ili grešku ako operacija nije uspela.
 * @author Jovan Radojičić
 */
public class ServerskiOdgovor implements Serializable {

    /** Rezultat uspešno izvršene operacije. */
    private Object odgovor;
    /** Greška nastala prilikom izvršenja operacije, ako je bilo greške. */
    private Exception greska;

    public ServerskiOdgovor() {
    }

    public ServerskiOdgovor(Object odgovor, Exception greska) {
        this.odgovor = odgovor;
        this.greska = greska;
    }

    /**
     * Vraća rezultat operacije.
     * @return odgovor kao Object, može biti null ako je operacija završena greškom
     */
    public Object getOdgovor() {
        return odgovor;
    }

    /**
     * Postavlja rezultat operacije.
     * @param odgovor rezultat operacije, sme biti null
     */
    public void setOdgovor(Object odgovor) {
        this.odgovor = odgovor;
    }

    /**
     * Vraća grešku nastalu prilikom izvršenja operacije.
     * @return greska kao Exception, null ako operacija nije bacila grešku
     */
    public Exception getGreska() {
        return greska;
    }

    /**
     * Postavlja grešku nastalu prilikom izvršenja operacije.
     * @param greska greška operacije, sme biti null
     */
    public void setGreska(Exception greska) {
        this.greska = greska;
    }
}
