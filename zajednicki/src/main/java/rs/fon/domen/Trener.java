package rs.fon.domen;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja trenera u fudbalskom klubu.
 * * @author Jovan Radojičić
 */
public class Trener implements Serializable {
    
    /** Jedinstveni identifikator trenera. */
    private int idTrener;
    /** Ime trenera. */
    private String ime;
    /** Prezime trenera. */
    private String prezime;
    /** Korisničko ime trenera za pristup sistemu. */
    private String korisnickoIme;
    /** Šifra trenera. */
    private String sifra;

    public Trener() {
    }

    public Trener(int idTrener, String ime, String prezime, String korisnickoIme, String sifra) {
        setIdTrener(idTrener);
        setIme(ime);
        setPrezime(prezime);
        setKorisnickoIme(korisnickoIme);
        setSifra(sifra);
    }

    /**
     * Vraća ID trenera.
     * @return idTrener kao int
     */
    public int getIdTrener() {
        return idTrener;
    }

    /**
     * Postavlja ID trenera.
     * @param idTrener identifikator trenera
     * @throws IllegalArgumentException ako je id nula ili manji
     */
    public void setIdTrener(int idTrener) {
        if (idTrener <= 0) throw new IllegalArgumentException("ID mora biti veci od 0");
        this.idTrener = idTrener;
    }

    /**
     * Vraća ime trenera.
     * @return ime kao String
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime trenera.
     * @param ime ime trenera
     * @throws NullPointerException ako je ime null
     * @throws IllegalArgumentException ako je ime prazno
     */
    public void setIme(String ime) {
        Objects.requireNonNull(ime, "Ime ne sme biti null");
        if (ime.isBlank()) throw new IllegalArgumentException("Ime ne sme biti prazno");
        this.ime = ime;
    }

    /**
     * Vraća prezime trenera.
     * @return prezime kao String
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime trenera.
     * @param prezime prezime trenera
     * @throws NullPointerException ako je prezime null
     * @throws IllegalArgumentException ako je prezime prazno
     */
    public void setPrezime(String prezime) {
        Objects.requireNonNull(prezime, "Prezime ne sme biti null");
        if (prezime.isBlank()) throw new IllegalArgumentException("Prezime ne sme biti prazno");
        this.prezime = prezime;
    }

    /**
     * Vraća korisničko ime.
     * @return korisnickoIme kao String
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Postavlja korisničko ime.
     * @param korisnickoIme korisničko ime
     * @throws NullPointerException ako je korisničko ime null
     * @throws IllegalArgumentException ako je korisničko ime prazno
     */
    public void setKorisnickoIme(String korisnickoIme) {
        Objects.requireNonNull(korisnickoIme, "Korisnicko ime ne sme biti null");
        if (korisnickoIme.isBlank()) throw new IllegalArgumentException("Korisnicko ime ne sme biti prazno");
        this.korisnickoIme = korisnickoIme;
    }

    /**
     * Vraća šifru trenera.
     * @return sifra kao String
     */
    public String getSifra() {
        return sifra;
    }

    /**
     * Postavlja šifru.
     * @param sifra šifra trenera
     * @throws NullPointerException ako je šifra null
     * @throws IllegalArgumentException ako je šifra prazna
     */
    public void setSifra(String sifra) {
        Objects.requireNonNull(sifra, "Sifra ne sme biti null");
        if (sifra.isBlank()) throw new IllegalArgumentException("Sifra ne sme biti prazna");
        this.sifra = sifra;
    }
    
    /**
     * Poredi dva objekta po ID-ju trenera.
     * * @param obj objekat sa kojim se poredi
     * @return true ako su oba objekta iste klase i imaju isti ID trenera, inace false
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Trener trener = (Trener) obj;
        return idTrener == trener.idTrener;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTrener);
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}