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
    }

    @Override
    protected Licenca izvrsiOperaciju(Licenca licenca) throws Exception {
        licenca.setIdLicence(repository.sledeciId());
        repository.add(licenca);
        return licenca;
    }
}
