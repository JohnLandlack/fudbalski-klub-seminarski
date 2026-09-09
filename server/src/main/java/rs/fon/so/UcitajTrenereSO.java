package rs.fon.so;

import java.util.List;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Trener;

/**
 * Sistemska operacija za preuzimanje svih trenera.
 * @author Jovan Radojičić
 */
public class UcitajTrenereSO extends OpstaSO<Void, List<Trener>> {

    private final TrenerRepository repository = new TrenerRepository();

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
     * Vraća sve trenere iz baze.
     * @param ulaz nekorišćeno, uvek {@code null}
     * @return lista svih trenera
     * @throws Exception ako čitanje iz baze ne uspe
     */
    @Override
    protected List<Trener> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
