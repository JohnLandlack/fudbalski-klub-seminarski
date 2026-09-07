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

    @Override
    protected void preduslovi(Void ulaz) throws Exception {
        // Nema preduslova — čitanje je uvek dozvoljeno.
    }

    @Override
    protected List<Igrac> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
