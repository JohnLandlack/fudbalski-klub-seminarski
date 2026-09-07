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

    @Override
    protected void preduslovi(Mesto mesto) throws Exception {
        Objects.requireNonNull(mesto, "Mesto ne sme biti null");
    }

    @Override
    protected Mesto izvrsiOperaciju(Mesto mesto) throws Exception {
        mesto.setIdMesta(repository.sledeciId());
        repository.add(mesto);
        return mesto;
    }
}
