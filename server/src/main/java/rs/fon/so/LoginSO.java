package rs.fon.so;

import java.util.List;
import java.util.Objects;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.Trener;

/**
 * Sistemska operacija za prijavu trenera na sistem — proverava korisničko
 * ime i šifru.
 * @author Jovan Radojičić
 */
public class LoginSO extends OpstaSO<Trener, Trener> {

    private final TrenerRepository repository = new TrenerRepository();

    @Override
    protected void preduslovi(Trener trener) throws Exception {
        Objects.requireNonNull(trener, "Trener ne sme biti null");
        Objects.requireNonNull(trener.getKorisnickoIme(), "Korisnicko ime ne sme biti null");
        Objects.requireNonNull(trener.getSifra(), "Sifra ne sme biti null");
    }

    @Override
    protected Trener izvrsiOperaciju(Trener trener) throws Exception {
        List<Trener> sviTreneri = repository.getAll();
        for (Trener kandidat : sviTreneri) {
            if (kandidat.getKorisnickoIme().equals(trener.getKorisnickoIme())) {
                if (!kandidat.getSifra().equals(trener.getSifra())) {
                    throw new Exception("Pogrešna šifra");
                }
                return kandidat;
            }
        }
        throw new Exception("Trener sa korisničkim imenom " + trener.getKorisnickoIme() + " ne postoji");
    }
}
