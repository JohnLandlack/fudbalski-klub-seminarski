package rs.fon.domen;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Predstavlja stavku ugovora — plata i oprema za jedan period važenja ugovora.
 * Jedinstveno je određena ugovorom kome pripada i rednim brojem u okviru njega.
 * * @author Jovan Radojičić
 */
public class StavkaUgovora implements Serializable {

    /** Ugovor kome stavka pripada. */
    private Ugovor ugovor;
    /** Redni broj stavke u okviru ugovora. */
    private int rbStavkaUgovora;
    /** Datum do kog stavka ugovora važi. */
    private Date vazenjeUgovora;
    /** Plata za period važenja stavke. */
    private int plata;
    /** Oprema dodeljena za period važenja stavke. */
    private Oprema oprema;

    public StavkaUgovora() {
    }

    public StavkaUgovora(Ugovor ugovor, int rbStavkaUgovora, Date vazenjeUgovora, int plata, Oprema oprema) {
        setUgovor(ugovor);
        setRbStavkaUgovora(rbStavkaUgovora);
        setVazenjeUgovora(vazenjeUgovora);
        setPlata(plata);
        setOprema(oprema);
    }

    /**
     * Vraća ugovor kome stavka pripada.
     * @return ugovor kao objekat klase Ugovor
     */
    public Ugovor getUgovor() {
        return ugovor;
    }

    /**
     * Postavlja ugovor kome stavka pripada.
     * @param ugovor ugovor
     * @throws NullPointerException ako je ugovor null
     */
    public void setUgovor(Ugovor ugovor) {
        Objects.requireNonNull(ugovor, "Ugovor ne sme biti null");
        this.ugovor = ugovor;
    }

    /**
     * Vraća redni broj stavke.
     * @return rbStavkaUgovora kao int
     */
    public int getRbStavkaUgovora() {
        return rbStavkaUgovora;
    }

    /**
     * Postavlja redni broj stavke.
     * @param rbStavkaUgovora redni broj stavke
     * @throws IllegalArgumentException ako je redni broj nula ili manji
     */
    public void setRbStavkaUgovora(int rbStavkaUgovora) {
        if (rbStavkaUgovora <= 0) throw new IllegalArgumentException("Redni broj mora biti veci od 0");
        this.rbStavkaUgovora = rbStavkaUgovora;
    }

    /**
     * Vraća datum važenja stavke.
     * @return vazenjeUgovora kao Date
     */
    public Date getVazenjeUgovora() {
        return vazenjeUgovora;
    }

    /**
     * Postavlja datum važenja stavke.
     * @param vazenjeUgovora datum važenja stavke
     * @throws NullPointerException ako je datum null
     */
    public void setVazenjeUgovora(Date vazenjeUgovora) {
        Objects.requireNonNull(vazenjeUgovora, "Datum vazenja ne sme biti null");
        this.vazenjeUgovora = vazenjeUgovora;
    }

    /**
     * Vraća platu za period važenja stavke.
     * @return plata kao int
     */
    public int getPlata() {
        return plata;
    }

    /**
     * Postavlja platu za period važenja stavke.
     * @param plata plata
     * @throws IllegalArgumentException ako je plata nula ili manja
     */
    public void setPlata(int plata) {
        if (plata <= 0) throw new IllegalArgumentException("Plata mora biti veca od 0");
        this.plata = plata;
    }

    /**
     * Vraća opremu dodeljenu za period važenja stavke.
     * @return oprema kao objekat klase Oprema
     */
    public Oprema getOprema() {
        return oprema;
    }

    /**
     * Postavlja opremu dodeljenu za period važenja stavke.
     * @param oprema oprema
     * @throws NullPointerException ako je oprema null
     */
    public void setOprema(Oprema oprema) {
        Objects.requireNonNull(oprema, "Oprema ne sme biti null");
        this.oprema = oprema;
    }

    /**
     * Poredi dva objekta po ugovoru i rednom broju stavke.
     * * @param obj objekat sa kojim se poredi
     * @return true ako su oba objekta iste klase i imaju isti ugovor i redni broj, inace false
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StavkaUgovora that = (StavkaUgovora) obj;
        return rbStavkaUgovora == that.rbStavkaUgovora && Objects.equals(ugovor, that.ugovor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ugovor, rbStavkaUgovora);
    }

    @Override
    public String toString() {
        return "Stavka " + rbStavkaUgovora + " (" + ugovor + ")";
    }
}
