package rs.fon.komunikacija;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja zahtev koji klijent šalje serveru preko soketa: koju operaciju
 * treba izvršiti i sa kojim parametrom.
 * @author Jovan Radojičić
 */
public class KlijentskiZahtev implements Serializable {

    /** Operacija koju server treba da izvrši. */
    private Operacije operacija;
    /** Ulazni podatak za operaciju (npr. domenski objekat). */
    private Object parametar;

    public KlijentskiZahtev() {
    }

    public KlijentskiZahtev(Operacije operacija, Object parametar) {
        setOperacija(operacija);
        this.parametar = parametar;
    }

    /**
     * Vraća traženu operaciju.
     * @return operacija kao {@link Operacije}
     */
    public Operacije getOperacija() {
        return operacija;
    }

    /**
     * Postavlja traženu operaciju.
     * @param operacija operacija koja se traži, ne sme biti null
     * @throws NullPointerException ako je operacija null
     */
    public void setOperacija(Operacije operacija) {
        this.operacija = Objects.requireNonNull(operacija, "Operacija ne sme biti null");
    }

    /**
     * Vraća ulazni parametar zahteva.
     * @return parametar kao Object, može biti null ako operacija ne zahteva parametar
     */
    public Object getParametar() {
        return parametar;
    }

    /**
     * Postavlja ulazni parametar zahteva.
     * @param parametar parametar zahteva, sme biti null
     */
    public void setParametar(Object parametar) {
        this.parametar = parametar;
    }
}
