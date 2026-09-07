package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.OpremaRepository;
import rs.fon.domen.Oprema;

/**
 * Sistemska operacija za dodavanje nove opreme.
 * * @author Jovan Radojičić
 */
public class DodajOpremuSO extends OpstaSO<Oprema, Oprema> {

    private final OpremaRepository repository = new OpremaRepository();

    @Override
    protected void preduslovi(Oprema oprema) throws Exception {
        Objects.requireNonNull(oprema, "Oprema ne sme biti null");
    }

    @Override
    protected Oprema izvrsiOperaciju(Oprema oprema) throws Exception {
        oprema.setIdOpreme(repository.sledeciId());
        repository.add(oprema);
        return oprema;
    }
}
