package rs.fon.domen;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja mesto u fudbalskom klubu.
 *
 * @author Jovan Radojičić
 */
public class Mesto implements Serializable {
    
    /** Jedinstveni identifikator mesta. */
    private int idMesta;
    /** Naziv mesta. */
    private String naziv;
    /** Poštanski broj mesta kao String. */
    private String postanskiBroj;

    public Mesto() {
    }

    public Mesto(int idMesta, String naziv, String postanskiBroj) {
        setIdMesta(idMesta);
        setNaziv(naziv);
        setPostanskiBroj(postanskiBroj);
    }

    /**
     * Vraća ID mesta.
     * @return idMesta kao int
     */
    public int getIdMesta() {
        return idMesta;
    }

    /**
     * Postavlja ID mesta.
     * @param idMesta identifikator mesta
     * @throws IllegalArgumentException ako je id nula ili manji
     */
    public void setIdMesta(int idMesta) {
        if (idMesta <= 0) throw new IllegalArgumentException("ID mora biti veci od 0");
        this.idMesta = idMesta;
    }

    /**
     * Vraća naziv mesta.
     * @return naziv kao String
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv mesta.
     * @param naziv naziv mesta
     * @throws NullPointerException ako je naziv null
     * @throws IllegalArgumentException ako je naziv prazan
     */
    public void setNaziv(String naziv) {
        Objects.requireNonNull(naziv, "Naziv ne sme biti null");
        if (naziv.isBlank()) throw new IllegalArgumentException("Naziv ne sme biti prazan");
        this.naziv = naziv;
    }

    /**
     * Vraća poštanski broj mesta.
     * @return postanski broj kao String
     */
    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    /**
     * Postavlja poštanski broj.
     * @param postanskiBroj poštanski broj mesta
     * @throws NullPointerException ako je poštanski broj null
     * @throws IllegalArgumentException ako je poštanski broj prazan
     */
    public void setPostanskiBroj(String postanskiBroj) {
        Objects.requireNonNull(postanskiBroj, "Postanski broj ne sme biti null");
        if (postanskiBroj.isBlank()) throw new IllegalArgumentException("Postanski broj ne sme biti prazan");
        this.postanskiBroj = postanskiBroj;
    }
    
    /**
     * Poredi dva objekta po ID-ju mesta.
     *
     * @param obj objekat sa kojim se poredi
     * @return true ako su oba objekta iste klase i imaju isti ID mesta, inace false
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mesto mesto = (Mesto) obj;
        return idMesta == mesto.idMesta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMesta);
    }

    @Override
    public String toString() {
        return naziv;
    }
}