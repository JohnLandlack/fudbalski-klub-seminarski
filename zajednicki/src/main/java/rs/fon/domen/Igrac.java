package rs.fon.domen;

import java.util.Objects;

/**
 * Predstavlja igrača u fudbalskom klubu.
 * * @author Jovan Radojičić
 */
public class Igrac {
    
    /** Jedinstveni identifikator igrača. */
    private int idIgrac;
    /** Ime igrača. */
    private String ime;
    /** Prezime igrača. */
    private String prezime;
    /** Telefon igrača. */
    private String telefon;
    /** Pozicija na kojoj igrač igra. */
    private String pozicija;
    /** Mesto iz kog je igrač. */
    private Mesto mesto;

    public Igrac() {
    }

    public Igrac(int idIgrac, String ime, String prezime, String telefon, String pozicija, Mesto mesto) {
        setIdIgrac(idIgrac);
        setIme(ime);
        setPrezime(prezime);
        setTelefon(telefon);
        setPozicija(pozicija);
        setMesto(mesto);
    }

    /**
     * Vraća ID igrača.
     * @return idIgrac kao int
     */
    public int getIdIgrac() {
        return idIgrac;
    }

    /**
     * Postavlja ID igrača.
     * @param idIgrac identifikator igrača
     * @throws IllegalArgumentException ako je id nula ili manji
     */
    public void setIdIgrac(int idIgrac) {
        if (idIgrac <= 0) throw new IllegalArgumentException("ID mora biti veci od 0");
        this.idIgrac = idIgrac;
    }

    /**
     * Vraća ime igrača.
     * @return ime kao String
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime igrača.
     * @param ime ime igrača
     * @throws NullPointerException ako je ime null
     * @throws IllegalArgumentException ako je ime prazno
     */
    public void setIme(String ime) {
        Objects.requireNonNull(ime, "Ime ne sme biti null");
        if (ime.isBlank()) throw new IllegalArgumentException("Ime ne sme biti prazno");
        this.ime = ime;
    }

    /**
     * Vraća prezime igrača.
     * @return prezime kao String
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime igrača.
     * @param prezime prezime igrača
     * @throws NullPointerException ako je prezime null
     * @throws IllegalArgumentException ako je prezime prazno
     */
    public void setPrezime(String prezime) {
        Objects.requireNonNull(prezime, "Prezime ne sme biti null");
        if (prezime.isBlank()) throw new IllegalArgumentException("Prezime ne sme biti prazno");
        this.prezime = prezime;
    }

    /**
     * Vraća telefon.
     * @return telefon kao String
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Postavlja telefon.
     * @param telefon telefon igrača
     * @throws NullPointerException ako je telefon null
     * @throws IllegalArgumentException ako je telefon prazan
     */
    public void setTelefon(String telefon) {
        Objects.requireNonNull(telefon, "Telefon ne sme biti null");
        if (telefon.isBlank()) throw new IllegalArgumentException("Telefon ne sme biti prazan");
        this.telefon = telefon;
    }

    /**
     * Vraća poziciju.
     * @return pozicija kao String
     */
    public String getPozicija() {
        return pozicija;
    }

    /**
     * Postavlja poziciju.
     * @param pozicija pozicija igrača
     * @throws NullPointerException ako je pozicija null
     * @throws IllegalArgumentException ako je pozicija prazna
     */
    public void setPozicija(String pozicija) {
        Objects.requireNonNull(pozicija, "Pozicija ne sme biti null");
        if (pozicija.isBlank()) throw new IllegalArgumentException("Pozicija ne sme biti prazna");
        this.pozicija = pozicija;
    }

    /**
     * Vraća mesto igrača.
     * @return mesto kao objekat klase Mesto
     */
    public Mesto getMesto() {
        return mesto;
    }

    /**
     * Postavlja mesto igrača.
     * @param mesto mesto
     * @throws NullPointerException ako je mesto null
     */
    public void setMesto(Mesto mesto) {
        Objects.requireNonNull(mesto, "Mesto ne sme biti null");
        this.mesto = mesto;
    }
    
    /**
     * Poredi dva objekta po ID-ju igraca.
     * * @param obj objekat sa kojim se poredi
     * @return true ako su oba objekta iste klase i imaju isti ID igraca, inace false
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Igrac igrac = (Igrac) obj;
        return idIgrac == igrac.idIgrac;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIgrac);
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}