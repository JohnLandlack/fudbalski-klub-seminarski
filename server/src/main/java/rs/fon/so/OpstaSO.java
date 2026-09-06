package rs.fon.so;

import rs.fon.baza.Konekcija;

/**
 * Generička osnova za sistemske operacije — template method koji garantuje
 * da se svaka operacija izvrši transakciono: provera preduslova, izvršenje,
 * pa commit; na bilo koju grešku, rollback.
 * * @param <T> tip ulaznog objekta operacije
 * @param <R> tip rezultata operacije
 * @author Jovan Radojičić
 */
public abstract class OpstaSO<T, R> {

    /**
     * Izvršava sistemsku operaciju nad ulaznim objektom: proverava preduslove,
     * izvršava operaciju i potvrđuje transakciju; na grešku poništava
     * transakciju i prosleđuje izuzetak dalje.
     * @param ulaz ulazni objekat operacije
     * @return rezultat operacije
     * @throws Exception ako preduslovi nisu ispunjeni ili operacija ne uspe
     */
    public final R izvrsi(T ulaz) throws Exception {
        try {
            preduslovi(ulaz);
            R rezultat = izvrsiOperaciju(ulaz);
            Konekcija.getConnection().commit();
            return rezultat;
        } catch (Exception ex) {
            Konekcija.getConnection().rollback();
            throw ex;
        }
    }

    /**
     * Proverava da li su ispunjeni preduslovi za izvršenje operacije.
     * @param ulaz ulazni objekat operacije
     * @throws Exception ako preduslovi nisu ispunjeni
     */
    protected abstract void preduslovi(T ulaz) throws Exception;

    /**
     * Izvršava samu poslovnu logiku operacije.
     * @param ulaz ulazni objekat operacije
     * @return rezultat operacije
     * @throws Exception ako izvršenje ne uspe
     */
    protected abstract R izvrsiOperaciju(T ulaz) throws Exception;
}
