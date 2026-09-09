package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.LicencaRepository;
import rs.fon.domen.Licenca;

/**
 * Sistemska operacija za brisanje licence. Baza sprečava brisanje
 * (RESTRICT) ako je licenca dodeljena nekom treneru.
 * @author Jovan Radojičić
 */
public class ObrisiLicencuSO extends OpstaSO<Licenca, Void> {

    private final LicencaRepository repository = new LicencaRepository();

    /**
     * Proverava da licenca koja se briše nije null i da postoji u bazi.
     * @param licenca licenca koja se briše
     * @throws NullPointerException ako je licenca null
     * @throws Exception ako licenca sa datim ID-jem ne postoji u bazi
     */
    @Override
    protected void preduslovi(Licenca licenca) throws Exception {
        Objects.requireNonNull(licenca, "Licenca ne sme biti null");
        if (repository.getById(licenca.getIdLicence()) == null) {
            throw new Exception("Licenca sa ID-jem " + licenca.getIdLicence() + " ne postoji");
        }
    }

    /**
     * Briše licencu iz baze.
     * @param licenca licenca koja se briše
     * @return uvek {@code null}
     * @throws Exception ako je licenca dodeljena nekom treneru (baza
     * sprečava brisanje) ili brisanje iz nekog drugog razloga ne uspe
     */
    @Override
    protected Void izvrsiOperaciju(Licenca licenca) throws Exception {
        repository.delete(licenca);
        return null;
    }
}
