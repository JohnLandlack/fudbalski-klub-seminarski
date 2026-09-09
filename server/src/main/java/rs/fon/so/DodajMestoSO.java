package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Mesto;

/**
 * Sistemska operacija za dodavanje novog mesta.
 *
 * @author Jovan Radojičić
 */
public class DodajMestoSO extends OpstaSO<Mesto, Mesto> {

    private final MestoRepository repository = new MestoRepository();

    /**
     * Proverava da mesto koje se dodaje nije null.
     * @param mesto mesto koje se dodaje
     * @throws NullPointerException ako je mesto null
     */
    @Override
    protected void preduslovi(Mesto mesto) throws Exception {
        Objects.requireNonNull(mesto, "Mesto ne sme biti null");
    }

    /**
     * Dodeljuje mestu sledeći slobodan ID i upisuje ga u bazu.
     * @param mesto mesto koje se dodaje (ID koji eventualno ima se ignoriše)
     * @return mesto sa dodeljenim ID-jem
     * @throws Exception ako upis u bazu ne uspe
     */
    @Override
    protected Mesto izvrsiOperaciju(Mesto mesto) throws Exception {
        mesto.setIdMesta(repository.sledeciId());
        repository.add(mesto);
        return mesto;
    }
}
