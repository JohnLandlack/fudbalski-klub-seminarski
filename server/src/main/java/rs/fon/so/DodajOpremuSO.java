package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.OpremaRepository;
import rs.fon.domen.Oprema;

/**
 * Sistemska operacija za dodavanje nove opreme.
 *
 * @author Jovan Radojičić
 */
public class DodajOpremuSO extends OpstaSO<Oprema, Oprema> {

    private final OpremaRepository repository = new OpremaRepository();

    /**
     * Proverava da oprema koja se dodaje nije null.
     * @param oprema oprema koja se dodaje
     * @throws NullPointerException ako je oprema null
     */
    @Override
    protected void preduslovi(Oprema oprema) throws Exception {
        Objects.requireNonNull(oprema, "Oprema ne sme biti null");
    }

    /**
     * Dodeljuje opremi sledeći slobodan ID i upisuje je u bazu.
     * @param oprema oprema koja se dodaje (ID koji eventualno ima se ignoriše)
     * @return oprema sa dodeljenim ID-jem
     * @throws Exception ako upis u bazu ne uspe
     */
    @Override
    protected Oprema izvrsiOperaciju(Oprema oprema) throws Exception {
        oprema.setIdOpreme(repository.sledeciId());
        repository.add(oprema);
        return oprema;
    }
}
