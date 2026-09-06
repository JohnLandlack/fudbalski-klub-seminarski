package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.IgracRepository;
import rs.fon.domen.Igrac;

/**
 * Sistemska operacija za brisanje igrača. Baza sprečava brisanje (RESTRICT)
 * ako igrač ima aktivan ugovor.
 * * @author Jovan Radojičić
 */
public class ObrisiIgracaSO extends OpstaSO<Igrac, Void> {

    private final IgracRepository repository = new IgracRepository();

    @Override
    protected void preduslovi(Igrac igrac) throws Exception {
        Objects.requireNonNull(igrac, "Igrac ne sme biti null");
        if (repository.getById(igrac.getIdIgrac()) == null) {
            throw new Exception("Igrac sa ID-jem " + igrac.getIdIgrac() + " ne postoji");
        }
    }

    @Override
    protected Void izvrsiOperaciju(Igrac igrac) throws Exception {
        repository.delete(igrac);
        return null;
    }
}
