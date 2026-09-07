package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.UgovorRepository;
import rs.fon.domen.Ugovor;

/**
 * Sistemska operacija za brisanje ugovora. Stavke ugovora se brišu automatski
 * (ON DELETE CASCADE na nivou baze).
 *
 * @author Jovan Radojičić
 */
public class ObrisiUgovorSO extends OpstaSO<Ugovor, Void> {

    private final UgovorRepository repository = new UgovorRepository();

    @Override
    protected void preduslovi(Ugovor ugovor) throws Exception {
        Objects.requireNonNull(ugovor, "Ugovor ne sme biti null");
        if (repository.getById(ugovor.getIdUgovor()) == null) {
            throw new Exception("Ugovor sa ID-jem " + ugovor.getIdUgovor() + " ne postoji");
        }
    }

    @Override
    protected Void izvrsiOperaciju(Ugovor ugovor) throws Exception {
        repository.delete(ugovor);
        return null;
    }
}
