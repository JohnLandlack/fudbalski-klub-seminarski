package rs.fon.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import rs.fon.domen.Igrac;
import rs.fon.domen.Licenca;
import rs.fon.domen.Mesto;
import rs.fon.domen.Oprema;
import rs.fon.domen.Trener;
import rs.fon.domen.TrenerLicenca;
import rs.fon.domen.Ugovor;
import rs.fon.komunikacija.KlijentskiZahtev;
import rs.fon.komunikacija.ServerskiOdgovor;
import rs.fon.so.DodajIgracaSO;
import rs.fon.so.DodajLicencuSO;
import rs.fon.so.DodajMestoSO;
import rs.fon.so.DodajOpremuSO;
import rs.fon.so.DodajTreneraSO;
import rs.fon.so.DodajUgovorSO;
import rs.fon.so.DodeliLicencuTreneruSO;
import rs.fon.so.IzmeniUgovorSO;
import rs.fon.so.ObrisiIgracaSO;
import rs.fon.so.ObrisiUgovorSO;
import rs.fon.so.PreuzmiSveIgraceSO;

/**
 * Nit koja opslužuje jednog povezanog klijenta: čita {@link KlijentskiZahtev}
 * objekte sa soketa, prosleđuje ih odgovarajućoj sistemskoj operaciji na
 * osnovu {@link rs.fon.komunikacija.Operacije} vrednosti, i vraća
 * {@link ServerskiOdgovor} sa rezultatom ili greškom.
 * @author Jovan Radojičić
 */
public class ObradaKlijentskihZahteva extends Thread {

    private final Socket socket;
    private volatile boolean kraj = false;

    public ObradaKlijentskihZahteva(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            while (!kraj) {
                KlijentskiZahtev zahtev = (KlijentskiZahtev) ois.readObject();
                ServerskiOdgovor odgovor = new ServerskiOdgovor();

                try {
                    odgovor.setOdgovor(izvrsiZahtev(zahtev));
                } catch (Exception ex) {
                    odgovor.setGreska(ex);
                }

                oos.writeObject(odgovor);
                oos.flush();
            }
        } catch (Exception ex) {
            System.out.println("Klijent je prekinuo vezu.");
        }
    }

    private Object izvrsiZahtev(KlijentskiZahtev zahtev) throws Exception {
        Object parametar = zahtev.getParametar();
        switch (zahtev.getOperacija()) {
            case DODAJ_MESTO:
                return new DodajMestoSO().izvrsi((Mesto) parametar);
            case DODAJ_LICENCU:
                return new DodajLicencuSO().izvrsi((Licenca) parametar);
            case DODAJ_TRENERA:
                return new DodajTreneraSO().izvrsi((Trener) parametar);
            case DODAJ_OPREMU:
                return new DodajOpremuSO().izvrsi((Oprema) parametar);
            case DODAJ_IGRACA:
                return new DodajIgracaSO().izvrsi((Igrac) parametar);
            case DODAJ_UGOVOR:
                return new DodajUgovorSO().izvrsi((Ugovor) parametar);
            case IZMENI_UGOVOR:
                return new IzmeniUgovorSO().izvrsi((Ugovor) parametar);
            case OBRISI_UGOVOR:
                return new ObrisiUgovorSO().izvrsi((Ugovor) parametar);
            case DODELI_LICENCU_TRENERU:
                return new DodeliLicencuTreneruSO().izvrsi((TrenerLicenca) parametar);
            case PREUZMI_SVE_IGRACE:
                return new PreuzmiSveIgraceSO().izvrsi(null);
            case OBRISI_IGRACA:
                return new ObrisiIgracaSO().izvrsi((Igrac) parametar);
            default:
                throw new IllegalArgumentException("Nepoznata operacija: " + zahtev.getOperacija());
        }
    }

    /**
     * Zaustavlja nit i zatvara soket ka klijentu.
     */
    public void zaustaviNit() {
        kraj = true;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception ex) {
            System.out.println("Greska prilikom zatvaranja soketa: " + ex.getMessage());
        }
    }
}
