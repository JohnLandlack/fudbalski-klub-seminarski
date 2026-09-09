package rs.fon.so;

import java.util.List;
import rs.fon.baza.LicencaRepository;
import rs.fon.domen.Licenca;

/**
 * Sistemska operacija za preuzimanje svih licenci.
 * @author Jovan Radojičić
 */
public class UcitajLicenceSO extends OpstaSO<Void, List<Licenca>> {

    private final LicencaRepository repository = new LicencaRepository();

    /**
     * Nema preduslova — čitanje je uvek dozvoljeno.
     * @param ulaz nekorišćeno, uvek {@code null}
     * @throws Exception nikad
     */
    @Override
    protected void preduslovi(Void ulaz) throws Exception {
        // Nema preduslova — čitanje je uvek dozvoljeno.
    }

    /**
     * Vraća sve licence iz baze.
     * @param ulaz nekorišćeno, uvek {@code null}
     * @return lista svih licenci
     * @throws Exception ako čitanje iz baze ne uspe
     */
    @Override
    protected List<Licenca> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
