package rs.fon.domen;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja opremu (dres) u fudbalskom klubu.
 * * @author Jovan Radojičić
 */
public class Oprema implements Serializable {

    /** Jedinstveni identifikator opreme. */
    private int idOpreme;
    /** Tip dresa (npr. Domaći, Gostujući). */
    private String tipDresa;
    /** Vrsta dresa (npr. Letnji, Zimski). */
    private String vrsteDresa;

    public Oprema() {
    }

    public Oprema(int idOpreme, String tipDresa, String vrsteDresa) {
        setIdOpreme(idOpreme);
        setTipDresa(tipDresa);
        setVrsteDresa(vrsteDresa);
    }

    /**
     * Vraća ID opreme.
     * @return idOpreme kao int
     */
    public int getIdOpreme() {
        return idOpreme;
    }

    /**
     * Postavlja ID opreme.
     * @param idOpreme identifikator opreme
     * @throws IllegalArgumentException ako je id nula ili manji
     */
    public void setIdOpreme(int idOpreme) {
        if (idOpreme <= 0) throw new IllegalArgumentException("ID mora biti veci od 0");
        this.idOpreme = idOpreme;
    }

    /**
     * Vraća tip dresa.
     * @return tipDresa kao String
     */
    public String getTipDresa() {
        return tipDresa;
    }

    /**
     * Postavlja tip dresa.
     * @param tipDresa tip dresa
     * @throws NullPointerException ako je tip null
     * @throws IllegalArgumentException ako je tip prazan
     */
    public void setTipDresa(String tipDresa) {
        Objects.requireNonNull(tipDresa, "Tip dresa ne sme biti null");
        if (tipDresa.isBlank()) throw new IllegalArgumentException("Tip dresa ne sme biti prazan");
        this.tipDresa = tipDresa;
    }

    /**
     * Vraća vrstu dresa.
     * @return vrsteDresa kao String
     */
    public String getVrsteDresa() {
        return vrsteDresa;
    }

    /**
     * Postavlja vrstu dresa.
     * @param vrsteDresa vrsta dresa
     * @throws NullPointerException ako je vrsta null
     * @throws IllegalArgumentException ako je vrsta prazna
     */
    public void setVrsteDresa(String vrsteDresa) {
        Objects.requireNonNull(vrsteDresa, "Vrsta dresa ne sme biti null");
        if (vrsteDresa.isBlank()) throw new IllegalArgumentException("Vrsta dresa ne sme biti prazna");
        this.vrsteDresa = vrsteDresa;
    }

    /**
     * Poredi dva objekta po ID-ju opreme.
     * * @param obj objekat sa kojim se poredi
     * @return true ako su oba objekta iste klase i imaju isti ID opreme, inace false
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Oprema oprema = (Oprema) obj;
        return idOpreme == oprema.idOpreme;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOpreme);
    }

    @Override
    public String toString() {
        return tipDresa + " " + vrsteDresa;
    }
}
