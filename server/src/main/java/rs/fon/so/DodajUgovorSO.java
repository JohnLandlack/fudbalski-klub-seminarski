package rs.fon.so;

import java.util.Objects;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.StavkaUgovoraRepository;
import rs.fon.baza.TrenerRepository;
import rs.fon.baza.UgovorRepository;
import rs.fon.domen.StavkaUgovora;
import rs.fon.domen.Ugovor;

/**
 * Sistemska operacija za dodavanje novog ugovora, zajedno sa njegovim
 * stavkama (ako ih ugovor već ima postavljene).
 * * @author Jovan Radojičić
 */
public class DodajUgovorSO extends OpstaSO<Ugovor, Ugovor> {

    private final UgovorRepository repository = new UgovorRepository();
    private final TrenerRepository trenerRepository = new TrenerRepository();
    private final IgracRepository igracRepository = new IgracRepository();
    private final StavkaUgovoraRepository stavkaRepository = new StavkaUgovoraRepository();

    @Override
    protected void preduslovi(Ugovor ugovor) throws Exception {
        Objects.requireNonNull(ugovor, "Ugovor ne sme biti null");
        if (trenerRepository.getById(ugovor.getTrener().getIdTrener()) == null) {
            throw new Exception("Trener sa ID-jem " + ugovor.getTrener().getIdTrener() + " ne postoji");
        }
        if (igracRepository.getById(ugovor.getIgrac().getIdIgrac()) == null) {
            throw new Exception("Igrac sa ID-jem " + ugovor.getIgrac().getIdIgrac() + " ne postoji");
        }
    }

    @Override
    protected Ugovor izvrsiOperaciju(Ugovor ugovor) throws Exception {
        ugovor.setIdUgovor(repository.sledeciId());
        repository.add(ugovor);
        for (StavkaUgovora stavka : ugovor.getStavke()) {
            stavka.setUgovor(ugovor);
            stavkaRepository.add(stavka);
        }
        return ugovor;
    }
}
