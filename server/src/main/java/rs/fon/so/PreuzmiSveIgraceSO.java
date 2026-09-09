package rs.fon.so;

import java.util.List;
import rs.fon.baza.IgracRepository;
import rs.fon.domen.Igrac;

/**
 * Sistemska operacija za preuzimanje svih igrača.
 *
 * @author Jovan Radojičić
 */
public class PreuzmiSveIgraceSO extends OpstaSO<Void, List<Igrac>> {

    private final IgracRepository repository = new IgracRepository();

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
     * Vraća sve igrače iz baze.
     * @param ulaz nekorišćeno, uvek {@code null}
     * @return lista svih igrača
     * @throws Exception ako čitanje iz baze ne uspe
     */
    @Override
    protected List<Igrac> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
