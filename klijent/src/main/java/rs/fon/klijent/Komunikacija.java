package rs.fon.klijent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import rs.fon.komunikacija.KlijentskiZahtev;
import rs.fon.komunikacija.ServerskiOdgovor;

/**
 * Singleton koji drži soket konekciju ka serveru i razmenjuje
 * {@link KlijentskiZahtev}/{@link ServerskiOdgovor} objekte.
 * @author Jovan Radojičić
 */
public class Komunikacija {

    /** Port na kom server osluškuje konekcije klijenata. */
    private static final int PORT = 9000;

    private static Komunikacija instance;

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    private Komunikacija() throws IOException {
        Socket socket = new Socket("localhost", PORT);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Vraća jedinu instancu komunikacije, uspostavljajući konekciju ka
     * serveru pri prvom pozivu.
     * @return instanca komunikacije
     * @throws IOException ako konekcija ka serveru ne može da se uspostavi
     */
    public static synchronized Komunikacija getInstance() throws IOException {
        if (instance == null) {
            instance = new Komunikacija();
        }
        return instance;
    }

    /**
     * Šalje zahtev serveru i čeka odgovarajući odgovor.
     * @param zahtev zahtev koji se šalje
     * @return odgovor servera
     * @throws IOException ako slanje ili prijem preko soketa ne uspe
     * @throws ClassNotFoundException ako primljeni objekat ne može da se deserijalizuje
     */
    public synchronized ServerskiOdgovor posaljiZahtev(KlijentskiZahtev zahtev) throws IOException, ClassNotFoundException {
        oos.writeObject(zahtev);
        oos.flush();
        return (ServerskiOdgovor) ois.readObject();
    }
}
