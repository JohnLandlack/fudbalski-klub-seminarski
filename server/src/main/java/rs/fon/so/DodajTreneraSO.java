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

    /**
     * Proverava da trener koji se dodaje nije null.
     * @param trener trener koji se dodaje
     * @throws NullPointerException ako je trener null
     */
    @Override
    protected void preduslovi(Trener trener) throws Exception {
        Objects.requireNonNull(trener, "Trener ne sme biti null");
    }

    /**
     * Dodeljuje treneru sledeći slobodan ID i upisuje ga u bazu.
     * @param trener trener koji se dodaje (ID koji eventualno ima se ignoriše)
     * @return trener sa dodeljenim ID-jem
     * @throws Exception ako upis u bazu ne uspe
     */
    @Override
    protected Trener izvrsiOperaciju(Trener trener) throws Exception {
        trener.setIdTrener(repository.sledeciId());
        repository.add(trener);
        return trener;
    }
}
