package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Trener;

/**
 * Sistemska operacija za dodavanje novog trenera.
 *
 * @author Jovan Radojičić
 */
public class DodajTreneraSO extends OpstaSO<Trener, Trener> {

    private final TrenerRepository repository = new TrenerRepository();

    @Override
    protected void preduslovi(Trener trener) throws Exception {
        Objects.requireNonNull(trener, "Trener ne sme biti null");
    }

    @Override
    protected Trener izvrsiOperaciju(Trener trener) throws Exception {
        trener.setIdTrener(repository.sledeciId());
        repository.add(trener);
        return trener;
    }
}
