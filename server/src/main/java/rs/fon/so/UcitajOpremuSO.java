package rs.fon.so;

import java.util.List;
import rs.fon.baza.OpremaRepository;
import rs.fon.domen.Oprema;

/**
 * Sistemska operacija za preuzimanje sve opreme.
 * @author Jovan Radojičić
 */
public class UcitajOpremuSO extends OpstaSO<Void, List<Oprema>> {

    private final OpremaRepository repository = new OpremaRepository();

    @Override
    protected void preduslovi(Void ulaz) throws Exception {
        // Nema preduslova — čitanje je uvek dozvoljeno.
    }

    @Override
    protected List<Oprema> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
