package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Mesto;

/**
 * Sistemska operacija za brisanje mesta. Baza sprečava brisanje (RESTRICT)
 * ako mesto ima povezane igrače.
 * @author Jovan Radojičić
 */
public class ObrisiMestoSO extends OpstaSO<Mesto, Void> {

    private final MestoRepository repository = new MestoRepository();

    /**
     * Proverava da mesto koje se briše nije null i da postoji u bazi.
     * @param mesto mesto koje se briše
     * @throws NullPointerException ako je mesto null
     * @throws Exception ako mesto sa datim ID-jem ne postoji u bazi
     */
    @Override
    protected void preduslovi(Mesto mesto) throws Exception {
        Objects.requireNonNull(mesto, "Mesto ne sme biti null");
        if (repository.getById(mesto.getIdMesta()) == null) {
            throw new Exception("Mesto sa ID-jem " + mesto.getIdMesta() + " ne postoji");
        }
    }

    /**
     * Briše mesto iz baze.
     * @param mesto mesto koje se briše
     * @return uvek {@code null}
     * @throws Exception ako mesto ima povezane igrače (baza sprečava
     * brisanje) ili brisanje iz nekog drugog razloga ne uspe
     */
    @Override
    protected Void izvrsiOperaciju(Mesto mesto) throws Exception {
        repository.delete(mesto);
        return null;
    }
}
