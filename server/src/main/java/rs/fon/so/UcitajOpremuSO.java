package rs.fon.so;

import java.util.List;
import rs.fon.baza.OpremaRepository;
import rs.fon.domen.Oprema;

/**
 * Sistemska operacija za preuzimanje sve opreme.
 * @author Jovan Radojičić
 */
public class UcitajOpremuSO extends OpstaSO<Void, List<Oprema>> {

    private final OpremaRepository repository = new OpremaRepository();

    /**
     * Nema preduslova — čitanje je uvek dozvoljeno.
     * @param ulaz nekorišćeno, uvek {@code null}
     * @throws Exception nikad
     */
    @Override
    protected void preduslovi(Void ulaz) throws Exception {
        // Nema preduslova — čitanje je uvek dozvoljeno.
    }

    /**
     * Vraća svu opremu iz baze.
     * @param ulaz nekorišćeno, uvek {@code null}
     * @return lista sve opreme
     * @throws Exception ako čitanje iz baze ne uspe
     */
    @Override
    protected List<Oprema> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
