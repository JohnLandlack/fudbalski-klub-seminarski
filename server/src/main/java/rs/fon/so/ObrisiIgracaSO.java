package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.IgracRepository;
import rs.fon.domen.Igrac;

/**
 * Sistemska operacija za brisanje igrača. Baza sprečava brisanje (RESTRICT)
 * ako igrač ima aktivan ugovor.
 *
 * @author Jovan Radojičić
 */
public class ObrisiIgracaSO extends OpstaSO<Igrac, Void> {

    private final IgracRepository repository = new IgracRepository();

    /**
     * Proverava da igrač koji se briše nije null i da postoji u bazi.
     * @param igrac igrač koji se briše
     * @throws NullPointerException ako je igrač null
     * @throws Exception ako igrač sa datim ID-jem ne postoji u bazi
     */
    @Override
    protected void preduslovi(Igrac igrac) throws Exception {
        Objects.requireNonNull(igrac, "Igrac ne sme biti null");
        if (repository.getById(igrac.getIdIgrac()) == null) {
            throw new Exception("Igrac sa ID-jem " + igrac.getIdIgrac() + " ne postoji");
        }
    }

    /**
     * Briše igrača iz baze.
     * @param igrac igrač koji se briše
     * @return uvek {@code null}
     * @throws Exception ako igrač ima aktivan ugovor (baza sprečava
     * brisanje) ili brisanje iz nekog drugog razloga ne uspe
     */
    @Override
    protected Void izvrsiOperaciju(Igrac igrac) throws Exception {
        repository.delete(igrac);
        return null;
    }
}
