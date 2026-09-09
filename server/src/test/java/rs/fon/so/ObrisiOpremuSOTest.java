package rs.fon.so;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertNull;
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

class ObrisiOpremuSOTest extends BazaTestBase {

    private final ObrisiOpremuSO so = new ObrisiOpremuSO();
    private final OpremaRepository repository = new OpremaRepository();
    private Oprema oprema;

    @BeforeEach
    void dodajOpremu() throws Exception {
        oprema = new Oprema(1, "Prvi", "Domaći");
        repository.add(oprema);
    }

    @Test
    void izvrsiOperaciju_briseOpremu() throws Exception {
        so.preduslovi(oprema);
        so.izvrsiOperaciju(oprema);

        assertNull(repository.getById(oprema.getIdOpreme()));
    }

    @Test
    void preduslovi_bacaException_akoOpremaNePostoji() {
        Oprema nepostojeca = new Oprema(999, "Nepostojeca", "Nepostojeca");
        assertThrows(Exception.class, () -> so.preduslovi(nepostojeca));
    }

    @Test
    void izvrsiOperaciju_bacaException_akoJeOpremaUUpotrebi() throws Exception {
        Trener trener = new Trener(1, "Marko", "Markovic", "marko123", "sifra123");
        new TrenerRepository().add(trener);
        Mesto mesto = new Mesto(1, "Beograd", "11000");
        new MestoRepository().add(mesto);
        Igrac igrac = new Igrac(1, "Petar", "Petrovic", "0641234567", "Napadac", mesto);
        new IgracRepository().add(igrac);
        Ugovor ugovor = new Ugovor(1, new Date(), trener, igrac);
        new UgovorRepository().add(ugovor);
        new StavkaUgovoraRepository().add(new StavkaUgovora(ugovor, 1, new Date(), 1000, oprema));

        assertThrows(Exception.class, () -> so.izvrsiOperaciju(oprema));
    }
}
