package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.OpremaRepository;
import rs.fon.domen.Oprema;

/**
 * Sistemska operacija za brisanje opreme. Baza sprečava brisanje
 * (RESTRICT) ako je oprema u upotrebi u nekoj stavci ugovora.
 * @author Jovan Radojičić
 */
public class ObrisiOpremuSO extends OpstaSO<Oprema, Void> {

    private final OpremaRepository repository = new OpremaRepository();

    /**
     * Proverava da oprema koja se briše nije null i da postoji u bazi.
     * @param oprema oprema koja se briše
     * @throws NullPointerException ako je oprema null
     * @throws Exception ako oprema sa datim ID-jem ne postoji u bazi
     */
    @Override
    protected void preduslovi(Oprema oprema) throws Exception {
        Objects.requireNonNull(oprema, "Oprema ne sme biti null");
        if (repository.getById(oprema.getIdOpreme()) == null) {
            throw new Exception("Oprema sa ID-jem " + oprema.getIdOpreme() + " ne postoji");
        }
    }

    /**
     * Briše opremu iz baze.
     * @param oprema oprema koja se briše
     * @return uvek {@code null}
     * @throws Exception ako je oprema u upotrebi u nekoj stavci ugovora
     * (baza sprečava brisanje) ili brisanje iz nekog drugog razloga ne uspe
     */
    @Override
    protected Void izvrsiOperaciju(Oprema oprema) throws Exception {
        repository.delete(oprema);
        return null;
    }
}
