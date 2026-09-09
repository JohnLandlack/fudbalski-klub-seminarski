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

    /**
     * Proverava da igrač koji se menja nije null, da postoji u bazi i da
     * mesto na koje se poziva stvarno postoji.
     * @param igrac igrač sa izmenjenim podacima
     * @throws NullPointerException ako je igrač null
     * @throws Exception ako igrač sa datim ID-jem ili njegovo mesto ne postoje u bazi
     */
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

    /**
     * Ažurira podatke igrača u bazi.
     * @param igrac igrač sa izmenjenim podacima
     * @return izmenjeni igrač
     * @throws Exception ako ažuriranje ne uspe
     */
    @Override
    protected Igrac izvrsiOperaciju(Igrac igrac) throws Exception {
        repository.edit(igrac);
        return igrac;
    }
}
