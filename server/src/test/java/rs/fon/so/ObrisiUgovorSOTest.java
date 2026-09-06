package rs.fon.so;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.fon.baza.IgracRepository;
import rs.fon.baza.MestoRepository;
import rs.fon.baza.OpremaRepository;
import rs.fon.baza.StavkaUgovoraRepository;
import rs.fon.baza.TrenerRepository;
import rs.fon.baza.UgovorRepository;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;
import rs.fon.domen.Oprema;
import rs.fon.domen.StavkaUgovora;
import rs.fon.domen.Trener;
import rs.fon.domen.Ugovor;

class ObrisiUgovorSOTest extends BazaTestBase {

    private final ObrisiUgovorSO so = new ObrisiUgovorSO();
    private final UgovorRepository ugovorRepository = new UgovorRepository();
    private final StavkaUgovoraRepository stavkaRepository = new StavkaUgovoraRepository();
    private Ugovor ugovor;

    @BeforeEach
    void pripremiUgovorSaStavkom() throws Exception {
        Trener trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        new TrenerRepository().add(trener);

        Mesto mesto = new Mesto(1, "Beograd", "11000");
        new MestoRepository().add(mesto);
        Igrac igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);
        new IgracRepository().add(igrac);

        ugovor = new Ugovor(1, new Date(), trener, igrac);
        ugovorRepository.add(ugovor);

        Oprema oprema = new Oprema(1, "Domaci", "Letnji");
        new OpremaRepository().add(oprema);
        stavkaRepository.add(new StavkaUgovora(ugovor, 1, new Date(), 1000, oprema));
    }

    @Test
    void izvrsiOperaciju_brisePostojeciUgovorISveStavke() throws Exception {
        so.preduslovi(ugovor);
        so.izvrsiOperaciju(ugovor);

        assertNull(ugovorRepository.getById(ugovor.getIdUgovor()));
        assertTrue(stavkaRepository.getByUgovor(ugovor.getIdUgovor()).isEmpty());
    }

    @Test
    void preduslovi_bacaException_akoUgovorNePostoji() {
        Ugovor nepostojeci = new Ugovor(999, new Date(), ugovor.getTrener(), ugovor.getIgrac());
        assertThrows(Exception.class, () -> so.preduslovi(nepostojeci));
    }
}
