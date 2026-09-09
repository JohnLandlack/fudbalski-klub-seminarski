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

    /**
     * Proverava da su prosleđeni podaci za prijavu potpuni (korisničko ime i
     * šifra nisu null).
     * @param trener trener sa unetim korisničkim imenom i šifrom
     * @throws NullPointerException ako je trener, korisničko ime ili šifra null
     */
    @Override
    protected void preduslovi(Trener trener) throws Exception {
        Objects.requireNonNull(trener, "Trener ne sme biti null");
        Objects.requireNonNull(trener.getKorisnickoIme(), "Korisnicko ime ne sme biti null");
        Objects.requireNonNull(trener.getSifra(), "Sifra ne sme biti null");
    }

    /**
     * Traži trenera sa datim korisničkim imenom i proverava da li se
     * uneta šifra poklapa sa njegovom.
     * @param trener trener sa unetim korisničkim imenom i šifrom
     * @return trener iz baze čiji su podaci potvrđeni kao ispravni
     * @throws Exception ako korisničko ime ne postoji ili je uneta šifra pogrešna
     */
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
