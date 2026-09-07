package rs.fon.so;

import java.util.List;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Trener;

/**
 * Sistemska operacija za preuzimanje svih trenera.
 * @author Jovan Radojičić
 */
public class UcitajTrenereSO extends OpstaSO<Void, List<Trener>> {

    private final TrenerRepository repository = new TrenerRepository();

    @Override
    protected void preduslovi(Void ulaz) throws Exception {
        // Nema preduslova — čitanje je uvek dozvoljeno.
    }

    @Override
    protected List<Trener> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
