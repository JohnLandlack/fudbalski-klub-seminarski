package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Trener;

/**
 * Sistemska operacija za dodavanje novog trenera.
 * * @author Jovan Radojičić
 */
public class DodajTreneraSO extends OpstaSO<Trener, Trener> {

    private final TrenerRepository repository = new TrenerRepository();

    @Override
    protected void preduslovi(Trener trener) throws Exception {
        Objects.requireNonNull(trener, "Trener ne sme biti null");
        if (repository.getById(trener.getIdTrener()) != null) {
            throw new Exception("Trener sa ID-jem " + trener.getIdTrener() + " već postoji");
        }
    }

    @Override
    protected Trener izvrsiOperaciju(Trener trener) throws Exception {
        repository.add(trener);
        return trener;
    }
}
