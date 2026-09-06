package rs.fon.domen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja ugovor koji fudbalski klub sklapa sa trenerom i igračem.
 * * @author Jovan Radojičić
 */
public class Ugovor implements Serializable {

    /** Jedinstveni identifikator ugovora. */
    private int idUgovor;
    /** Datum potpisivanja ugovora. */
    private Date datumPotpisivanja;
    /** Trener sa kojim je ugovor sklopljen. */
    private Trener trener;
    /** Igrač sa kojim je ugovor sklopljen. */
    private Igrac igrac;
    /** Stavke ugovora (plata i oprema po periodima važenja). */
    private List<StavkaUgovora> stavke = new ArrayList<>();

    public Ugovor() {
    }

    public Ugovor(int idUgovor, Date datumPotpisivanja, Trener trener, Igrac igrac) {
        setIdUgovor(idUgovor);
        setDatumPotpisivanja(datumPotpisivanja);
        setTrener(trener);
        setIgrac(igrac);
    }

    /**
     * Vraća ID ugovora.
     * @return idUgovor kao int
     */
    public int getIdUgovor() {
        return idUgovor;
    }

    /**
     * Postavlja ID ugovora.
     * @param idUgovor identifikator ugovora
     * @throws IllegalArgumentException ako je id nula ili manji
     */
    public void setIdUgovor(int idUgovor) {
        if (idUgovor <= 0) throw new IllegalArgumentException("ID mora biti veci od 0");
        this.idUgovor = idUgovor;
    }

    /**
     * Vraća datum potpisivanja ugovora.
     * @return datumPotpisivanja kao Date
     */
    public Date getDatumPotpisivanja() {
        return datumPotpisivanja;
    }

    /**
     * Postavlja datum potpisivanja ugovora.
     * @param datumPotpisivanja datum potpisivanja
     * @throws NullPointerException ako je datum null
     */
    public void setDatumPotpisivanja(Date datumPotpisivanja) {
        Objects.requireNonNull(datumPotpisivanja, "Datum potpisivanja ne sme biti null");
        this.datumPotpisivanja = datumPotpisivanja;
    }

    /**
     * Vraća trenera sa kojim je ugovor sklopljen.
     * @return trener kao objekat klase Trener
     */
    public Trener getTrener() {
        return trener;
    }

    /**
     * Postavlja trenera.
     * @param trener trener
     * @throws NullPointerException ako je trener null
     */
    public void setTrener(Trener trener) {
        Objects.requireNonNull(trener, "Trener ne sme biti null");
        this.trener = trener;
    }

    /**
     * Vraća igrača sa kojim je ugovor sklopljen.
     * @return igrac kao objekat klase Igrac
     */
    public Igrac getIgrac() {
        return igrac;
    }

    /**
     * Postavlja igrača.
     * @param igrac igrac
     * @throws NullPointerException ako je igrac null
     */
    public void setIgrac(Igrac igrac) {
        Objects.requireNonNull(igrac, "Igrac ne sme biti null");
        this.igrac = igrac;
    }

    /**
     * Vraća listu stavki ugovora.
     * @return stavke kao lista objekata klase StavkaUgovora
     */
    public List<StavkaUgovora> getStavke() {
        return stavke;
    }

    /**
     * Postavlja listu stavki ugovora.
     * @param stavke lista stavki ugovora
     * @throws NullPointerException ako je lista null
     */
    public void setStavke(List<StavkaUgovora> stavke) {
        Objects.requireNonNull(stavke, "Lista stavki ne sme biti null");
        this.stavke = stavke;
    }

    /**
     * Poredi dva objekta po ID-ju ugovora.
     * * @param obj objekat sa kojim se poredi
     * @return true ako su oba objekta iste klase i imaju isti ID ugovora, inace false
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ugovor ugovor = (Ugovor) obj;
        return idUgovor == ugovor.idUgovor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUgovor);
    }

    @Override
    public String toString() {
        return "Ugovor " + idUgovor + " (" + trener + ", " + igrac + ")";
    }
}
