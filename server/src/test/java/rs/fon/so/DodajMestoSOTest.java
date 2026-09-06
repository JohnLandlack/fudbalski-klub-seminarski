package rs.fon.so;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import rs.fon.baza.MestoRepository;
import rs.fon.domen.Mesto;

class DodajMestoSOTest extends BazaTestBase {

    private final DodajMestoSO so = new DodajMestoSO();

    @Test
    void izvrsiOperaciju_dodajeMesto() throws Exception {
        Mesto mesto = new Mesto(1, "Novo Mesto", "99000");

        so.preduslovi(mesto);
        Mesto rezultat = so.izvrsiOperaciju(mesto);

        assertEquals(mesto, rezultat);
        assertEquals(mesto, new MestoRepository().getById(1));
    }

    @Test
    void preduslovi_bacaException_akoIdVecPostoji() throws Exception {
        Mesto mesto = new Mesto(1, "Postojece Mesto", "99001");
        new MestoRepository().add(mesto);

        Mesto duplikat = new Mesto(1, "Drugo Ime", "99002");
        assertThrows(Exception.class, () -> so.preduslovi(duplikat));
    }
}
