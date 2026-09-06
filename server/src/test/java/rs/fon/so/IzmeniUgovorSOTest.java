package rs.fon.so;

import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

class IzmeniUgovorSOTest extends BazaTestBase {

    private final IzmeniUgovorSO so = new IzmeniUgovorSO();
    private final StavkaUgovoraRepository stavkaRepository = new StavkaUgovoraRepository();
    private Ugovor ugovor;
    private Oprema oprema;

    @BeforeEach
    void pripremiUgovorSaStavkom() throws Exception {
        Trener trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        new TrenerRepository().add(trener);

        Mesto mesto = new Mesto(1, "Beograd", "11000");
        new MestoRepository().add(mesto);
        Igrac igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);
        new IgracRepository().add(igrac);

        ugovor = new Ugovor(1, new Date(), trener, igrac);
        new UgovorRepository().add(ugovor);

        oprema = new Oprema(1, "Domaci", "Letnji");
        new OpremaRepository().add(oprema);

        StavkaUgovora prvaStavka = new StavkaUgovora(ugovor, 1, new Date(), 1000, oprema);
        stavkaRepository.add(prvaStavka);
    }

    @Test
    void izvrsiOperaciju_dodajeNovuStavkuIBrisiStaru() throws Exception {
        StavkaUgovora novaStavka = new StavkaUgovora(ugovor, 2, new Date(), 2000, oprema);
        ugovor.setStavke(List.of(novaStavka));

        so.preduslovi(ugovor);
        so.izvrsiOperaciju(ugovor);

        List<StavkaUgovora> stavke = stavkaRepository.getByUgovor(ugovor.getIdUgovor());
        assertEquals(1, stavke.size());
        assertEquals(2, stavke.get(0).getRbStavkaUgovora());
    }

    @Test
    void izvrsiOperaciju_izmenjujePostojecuStavku() throws Exception {
        StavkaUgovora izmenjenaStavka = new StavkaUgovora(ugovor, 1, new Date(), 5000, oprema);
        ugovor.setStavke(List.of(izmenjenaStavka));

        so.preduslovi(ugovor);
        so.izvrsiOperaciju(ugovor);

        List<StavkaUgovora> stavke = stavkaRepository.getByUgovor(ugovor.getIdUgovor());
        assertEquals(1, stavke.size());
        assertEquals(5000, stavke.get(0).getPlata());
    }

    @Test
    void preduslovi_bacaException_akoUgovorNePostoji() {
        Ugovor nepostojeci = new Ugovor(999, new Date(), ugovor.getTrener(), ugovor.getIgrac());
        assertThrows(Exception.class, () -> so.preduslovi(nepostojeci));
    }
}
