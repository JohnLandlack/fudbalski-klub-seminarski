package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Mesto;

/**
 * Sistemska operacija za dodavanje novog mesta.
 * * @author Jovan Radojičić
 */
public class DodajMestoSO extends OpstaSO<Mesto, Mesto> {

    private final MestoRepository repository = new MestoRepository();

    @Override
    protected void preduslovi(Mesto mesto) throws Exception {
        Objects.requireNonNull(mesto, "Mesto ne sme biti null");
        if (repository.getById(mesto.getIdMesta()) != null) {
            throw new Exception("Mesto sa ID-jem " + mesto.getIdMesta() + " već postoji");
        }
    }

    @Override
    protected Mesto izvrsiOperaciju(Mesto mesto) throws Exception {
        repository.add(mesto);
        return mesto;
    }
}
