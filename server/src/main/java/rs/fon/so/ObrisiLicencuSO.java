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

    @Override
    protected void preduslovi(Licenca licenca) throws Exception {
        Objects.requireNonNull(licenca, "Licenca ne sme biti null");
        if (repository.getById(licenca.getIdLicence()) == null) {
            throw new Exception("Licenca sa ID-jem " + licenca.getIdLicence() + " ne postoji");
        }
    }

    @Override
    protected Void izvrsiOperaciju(Licenca licenca) throws Exception {
        repository.delete(licenca);
        return null;
    }
}
