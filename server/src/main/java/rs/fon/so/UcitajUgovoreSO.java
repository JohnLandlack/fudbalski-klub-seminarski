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

    @Override
    protected void preduslovi(Void ulaz) throws Exception {
        // Nema preduslova — čitanje je uvek dozvoljeno.
    }

    @Override
    protected List<Ugovor> izvrsiOperaciju(Void ulaz) throws Exception {
        return repository.getAll();
    }
}
