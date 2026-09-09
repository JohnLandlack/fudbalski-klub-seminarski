package rs.fon.so;

import java.util.List;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Mesto;

/**
 * Sistemska operacija za preuzimanje svih mesta.
 * @author Jovan Radojičić
 */
public class UcitajMestaSO extends OpstaSO<Void, List<Mesto>> {

    private final MestoRepository repository = new MestoRepository();

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
     * Vraća sva mesta iz baze.
     * @param ulaz nekorišćeno, uvek {@code null}
     * @return lista svih mesta
     * @throws Exception ako čitanje iz baze ne uspe
     */
    @Override
    protected List<Mesto> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
