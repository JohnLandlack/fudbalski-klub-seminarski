package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Igrac;

/**
 * Sistemska operacija za dodavanje novog igrača.
 *
 * @author Jovan Radojičić
 */
public class DodajIgracaSO extends OpstaSO<Igrac, Igrac> {

    private final IgracRepository repository = new IgracRepository();
    private final MestoRepository mestoRepository = new MestoRepository();

    /**
     * Proverava da igrač koji se dodaje nije null i da mesto na koje se
     * poziva stvarno postoji u bazi.
     * @param igrac igrač koji se dodaje
     * @throws NullPointerException ako je igrač null
     * @throws Exception ako mesto igrača ne postoji u bazi
     */
    @Override
    protected void preduslovi(Igrac igrac) throws Exception {
        Objects.requireNonNull(igrac, "Igrac ne sme biti null");
        if (mestoRepository.getById(igrac.getMesto().getIdMesta()) == null) {
            throw new Exception("Mesto sa ID-jem " + igrac.getMesto().getIdMesta() + " ne postoji");
        }
    }

    /**
     * Dodeljuje igraču sledeći slobodan ID i upisuje ga u bazu.
     * @param igrac igrač koji se dodaje (ID koji eventualno ima se ignoriše)
     * @return igrač sa dodeljenim ID-jem
     * @throws Exception ako upis u bazu ne uspe
     */
    @Override
    protected Igrac izvrsiOperaciju(Igrac igrac) throws Exception {
        igrac.setIdIgrac(repository.sledeciId());
        repository.add(igrac);
        return igrac;
    }
}
