package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.LicencaRepository;
import rs.fon.domen.Licenca;

/**
 * Sistemska operacija za dodavanje nove licence.
 *
 * @author Jovan Radojičić
 */
public class DodajLicencuSO extends OpstaSO<Licenca, Licenca> {

    private final LicencaRepository repository = new LicencaRepository();

    /**
     * Proverava da licenca koja se dodaje nije null.
     * @param licenca licenca koja se dodaje
     * @throws NullPointerException ako je licenca null
     */
    @Override
    protected void preduslovi(Licenca licenca) throws Exception {
        Objects.requireNonNull(licenca, "Licenca ne sme biti null");
    }

    /**
     * Dodeljuje licenci sledeći slobodan ID i upisuje je u bazu.
     * @param licenca licenca koja se dodaje (ID koji eventualno ima se ignoriše)
     * @return licenca sa dodeljenim ID-jem
     * @throws Exception ako upis u bazu ne uspe
     */
    @Override
    protected Licenca izvrsiOperaciju(Licenca licenca) throws Exception {
        licenca.setIdLicence(repository.sledeciId());
        repository.add(licenca);
        return licenca;
    }
}
