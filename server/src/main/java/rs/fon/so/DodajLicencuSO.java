package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.LicencaRepository;
import rs.fon.domen.Licenca;

/**
 * Sistemska operacija za dodavanje nove licence.
 * * @author Jovan Radojičić
 */
public class DodajLicencuSO extends OpstaSO<Licenca, Licenca> {

    private final LicencaRepository repository = new LicencaRepository();

    @Override
    protected void preduslovi(Licenca licenca) throws Exception {
        Objects.requireNonNull(licenca, "Licenca ne sme biti null");
        if (repository.getById(licenca.getIdLicence()) != null) {
            throw new Exception("Licenca sa ID-jem " + licenca.getIdLicence() + " već postoji");
        }
    }

    @Override
    protected Licenca izvrsiOperaciju(Licenca licenca) throws Exception {
        repository.add(licenca);
        return licenca;
    }
}
