package rs.fon.so;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import rs.fon.baza.StavkaUgovoraRepository;
import rs.fon.baza.UgovorRepository;
import rs.fon.domen.StavkaUgovora;
import rs.fon.domen.Ugovor;

/**
 * Sistemska operacija za izmenu postojećeg ugovora. Sinhronizuje stavke
 * ugovora sa bazom: nove stavke dodaje, izmenjene ažurira, a stavke koje više
 * ne postoje u ugovoru briše.
 *
 * @author Jovan Radojičić
 */
public class IzmeniUgovorSO extends OpstaSO<Ugovor, Ugovor> {

    private final UgovorRepository repository = new UgovorRepository();
    private final StavkaUgovoraRepository stavkaRepository = new StavkaUgovoraRepository();

    /**
     * Proverava da ugovor koji se menja nije null i da postoji u bazi.
     * @param ugovor ugovor sa izmenjenim podacima
     * @throws NullPointerException ako je ugovor null
     * @throws Exception ako ugovor sa datim ID-jem ne postoji u bazi
     */
    @Override
    protected void preduslovi(Ugovor ugovor) throws Exception {
        Objects.requireNonNull(ugovor, "Ugovor ne sme biti null");
        if (repository.getById(ugovor.getIdUgovor()) == null) {
            throw new Exception("Ugovor sa ID-jem " + ugovor.getIdUgovor() + " ne postoji");
        }
    }

    /**
     * Ažurira osnovne podatke ugovora i sinhronizuje njegove stavke sa bazom:
     * nove stavke dodaje, izmenjene ažurira, a stavke koje više ne postoje u
     * ugovoru briše.
     * @param ugovor ugovor sa izmenjenim podacima i konačnim spiskom stavki
     * @return izmenjeni ugovor
     * @throws Exception ako ažuriranje ugovora ili neke od stavki ne uspe
     */
    @Override
    protected Ugovor izvrsiOperaciju(Ugovor ugovor) throws Exception {
        repository.edit(ugovor);

        List<StavkaUgovora> stareStavke = stavkaRepository.getByUgovor(ugovor.getIdUgovor());
        Map<Integer, StavkaUgovora> mapaStarih = new HashMap<>();
        for (StavkaUgovora stara : stareStavke) {
            mapaStarih.put(stara.getRbStavkaUgovora(), stara);
        }

        Map<Integer, StavkaUgovora> mapaNovih = new HashMap<>();
        for (StavkaUgovora nova : ugovor.getStavke()) {
            mapaNovih.put(nova.getRbStavkaUgovora(), nova);
        }

        for (StavkaUgovora stara : stareStavke) {
            if (!mapaNovih.containsKey(stara.getRbStavkaUgovora())) {
                stavkaRepository.delete(stara);
            }
        }

        for (StavkaUgovora nova : ugovor.getStavke()) {
            nova.setUgovor(ugovor);
            if (mapaStarih.containsKey(nova.getRbStavkaUgovora())) {
                stavkaRepository.edit(nova);
            } else {
                stavkaRepository.add(nova);
            }
        }

        return ugovor;
    }
}
