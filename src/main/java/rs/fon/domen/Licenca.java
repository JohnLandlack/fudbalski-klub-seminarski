package rs.fon.domen;

import java.util.Objects;

/**
 * Predstavlja licencu u fudbalskom klubu.
 * * @author Jovan Radojičić
 */
public class Licenca {
    
    /** Jedinstveni identifikator licence. */
    private int idLicence;
    /** Tip licence (npr. Trenerska, Medicinska). */
    private String tipLicence;
    /** Nivo licence (npr. PRO, A, B). */
    private String nivoLicence;

    public Licenca() {
    }

    public Licenca(int idLicence, String tipLicence, String nivoLicence) {
        setIdLicence(idLicence);
        setTipLicence(tipLicence);
        setNivoLicence(nivoLicence);
    }

    /**
     * Vraća ID licence.
     * @return idLicence kao int
     */
    public int getIdLicence() {
        return idLicence;
    }

    /**
     * Postavlja ID licence.
     * @param idLicence identifikator licence
     * @throws IllegalArgumentException ako je id nula ili manji
     */
    public void setIdLicence(int idLicence) {
        if (idLicence <= 0) throw new IllegalArgumentException("ID mora biti veci od 0");
        this.idLicence = idLicence;
    }

    /**
     * Vraća tip licence.
     * @return tipLicence kao String
     */
    public String getTipLicence() {
        return tipLicence;
    }

    /**
     * Postavlja tip licence.
     * @param tipLicence tip licence
     * @throws NullPointerException ako je tip null
     * @throws IllegalArgumentException ako je tip prazan
     */
    public void setTipLicence(String tipLicence) {
        Objects.requireNonNull(tipLicence, "Tip ne sme biti null");
        if (tipLicence.isBlank()) throw new IllegalArgumentException("Tip ne sme biti prazan");
        this.tipLicence = tipLicence;
    }

    /**
     * Vraća nivo licence.
     * @return nivoLicence kao String
     */
    public String getNivoLicence() {
        return nivoLicence;
    }

    /**
     * Postavlja nivo licence.
     * @param nivoLicence nivo licence
     * @throws NullPointerException ako je nivo null
     * @throws IllegalArgumentException ako je nivo prazan
     */
    public void setNivoLicence(String nivoLicence) {
        Objects.requireNonNull(nivoLicence, "Nivo ne sme biti null");
        if (nivoLicence.isBlank()) throw new IllegalArgumentException("Nivo ne sme biti prazan");
        this.nivoLicence = nivoLicence;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Licenca licenca = (Licenca) obj;
        return idLicence == licenca.idLicence;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLicence);
    }

    @Override
    public String toString() {
        return tipLicence + " - " + nivoLicence;
    }
}