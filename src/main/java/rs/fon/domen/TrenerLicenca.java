package rs.fon.domen;

import java.util.Date;
import java.util.Objects;

/**
 * Predstavlja asocijativnu klasu između trenera i licence.
 * * @author Jovan Radojičić
 */
public class TrenerLicenca {
    
    /** Trener koji poseduje licencu. */
    private Trener trener;
    /** Licenca koju trener poseduje. */
    private Licenca licenca;
    /** Datum kada je licenca izdata. */
    private Date datumIzdavanja;
    /** Datum kada licenca ističe. */
    private Date datumIsteka;

    public TrenerLicenca() {
    }

    public TrenerLicenca(Trener trener, Licenca licenca, Date datumIzdavanja, Date datumIsteka) {
        setTrener(trener);
        setLicenca(licenca);
        setDatumIzdavanja(datumIzdavanja);
        setDatumIsteka(datumIsteka);
    }

    /**
     * Vraća trenera.
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
     * Vraća licencu.
     * @return licenca kao objekat klase Licenca
     */
    public Licenca getLicenca() {
        return licenca;
    }

    /**
     * Postavlja licencu.
     * @param licenca licenca
     * @throws NullPointerException ako je licenca null
     */
    public void setLicenca(Licenca licenca) {
        Objects.requireNonNull(licenca, "Licenca ne sme biti null");
        this.licenca = licenca;
    }

    /**
     * Vraća datum izdavanja.
     * @return datumIzdavanja kao Date
     */
    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    /**
     * Postavlja datum izdavanja.
     * @param datumIzdavanja datum izdavanja
     * @throws NullPointerException ako je datum izdavanja null
     */
    public void setDatumIzdavanja(Date datumIzdavanja) {
        Objects.requireNonNull(datumIzdavanja, "Datum izdavanja ne sme biti null");
        this.datumIzdavanja = datumIzdavanja;
    }

    /**
     * Vraća datum isteka.
     * @return datumIsteka kao Date
     */
    public Date getDatumIsteka() {
        return datumIsteka;
    }

    /**
     * Postavlja datum isteka.
     * @param datumIsteka datum isteka
     * @throws NullPointerException ako je datum isteka null
     * @throws IllegalArgumentException ako je datum isteka pre datuma izdavanja
     */
    public void setDatumIsteka(Date datumIsteka) {
        Objects.requireNonNull(datumIsteka, "Datum isteka ne sme biti null");
        if (datumIzdavanja != null && datumIsteka.before(datumIzdavanja)) {
            throw new IllegalArgumentException("Datum isteka ne sme biti pre datuma izdavanja");
        }
        this.datumIsteka = datumIsteka;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TrenerLicenca that = (TrenerLicenca) obj;
        return Objects.equals(trener, that.trener) && Objects.equals(licenca, that.licenca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trener, licenca);
    }

    @Override
    public String toString() {
        return trener + " poseduje " + licenca;
    }
}