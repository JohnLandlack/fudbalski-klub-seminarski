package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.LicencaRepository;
import rs.fon.baza.TrenerLicencaRepository;
import rs.fon.baza.TrenerRepository;
import rs.fon.domen.TrenerLicenca;

/**
 * Sistemska operacija za dodelu licence treneru.
 *
 * @author Jovan Radojičić
 */
public class DodeliLicencuTreneruSO extends OpstaSO<TrenerLicenca, TrenerLicenca> {

    private final TrenerLicencaRepository repository = new TrenerLicencaRepository();
    private final TrenerRepository trenerRepository = new TrenerRepository();
    private final LicencaRepository licencaRepository = new LicencaRepository();

    /**
     * Proverava da dodela nije null, da trener i licenca postoje u bazi i da
     * trener već ne poseduje tu licencu.
     * @param trenerLicenca dodela licence treneru koja se pravi
     * @throws NullPointerException ako je dodela null
     * @throws Exception ako trener ili licenca ne postoje u bazi, ili ako
     * trener već poseduje datu licencu
     */
    @Override
    protected void preduslovi(TrenerLicenca trenerLicenca) throws Exception {
        Objects.requireNonNull(trenerLicenca, "TrenerLicenca ne sme biti null");
        int idTrener = trenerLicenca.getTrener().getIdTrener();
        int idLicence = trenerLicenca.getLicenca().getIdLicence();
        if (trenerRepository.getById(idTrener) == null) {
            throw new Exception("Trener sa ID-jem " + idTrener + " ne postoji");
        }
        if (licencaRepository.getById(idLicence) == null) {
            throw new Exception("Licenca sa ID-jem " + idLicence + " ne postoji");
        }
        if (repository.getById(idTrener, idLicence) != null) {
            throw new Exception("Trener već poseduje ovu licencu");
        }
    }

    /**
     * Upisuje dodelu licence treneru u bazu.
     * @param trenerLicenca dodela licence treneru koja se upisuje
     * @return upisana dodela
     * @throws Exception ako upis u bazu ne uspe
     */
    @Override
    protected TrenerLicenca izvrsiOperaciju(TrenerLicenca trenerLicenca) throws Exception {
        repository.add(trenerLicenca);
        return trenerLicenca;
    }
}
