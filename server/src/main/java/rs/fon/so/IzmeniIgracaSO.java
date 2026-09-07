package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Igrac;

/**
 * Sistemska operacija za izmenu postojećeg igrača.
 * @author Jovan Radojičić
 */
public class IzmeniIgracaSO extends OpstaSO<Igrac, Igrac> {

    private final IgracRepository repository = new IgracRepository();
    private final MestoRepository mestoRepository = new MestoRepository();

    @Override
    protected void preduslovi(Igrac igrac) throws Exception {
        Objects.requireNonNull(igrac, "Igrac ne sme biti null");
        if (repository.getById(igrac.getIdIgrac()) == null) {
            throw new Exception("Igrac sa ID-jem " + igrac.getIdIgrac() + " ne postoji");
        }
        if (mestoRepository.getById(igrac.getMesto().getIdMesta()) == null) {
            throw new Exception("Mesto sa ID-jem " + igrac.getMesto().getIdMesta() + " ne postoji");
        }
    }

    @Override
    protected Igrac izvrsiOperaciju(Igrac igrac) throws Exception {
        repository.edit(igrac);
        return igrac;
    }
}
