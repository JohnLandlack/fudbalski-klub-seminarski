package rs.fon.so;

import java.util.List;
import rs.fon.baza.UgovorRepository;
import rs.fon.domen.Ugovor;

/**
 * Sistemska operacija za preuzimanje svih ugovora.
 * @author Jovan Radojičić
 */
public class UcitajUgovoreSO extends OpstaSO<Void, List<Ugovor>> {

    private final UgovorRepository repository = new UgovorRepository();

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
     * Vraća sve ugovore iz baze.
     * @param ulaz nekorišćeno, uvek {@code null}
     * @return lista svih ugovora
     * @throws Exception ako čitanje iz baze ne uspe
     */
    @Override
    protected List<Ugovor> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
