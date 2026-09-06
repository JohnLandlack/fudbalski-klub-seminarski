package rs.fon.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Glavna serverska nit — otvara serverski soket i za svakog povezanog
 * klijenta pokreće posebnu nit ({@link ObradaKlijentskihZahteva}) koja
 * dalje opslužuje taj klijentov saobraćaj.
 * @author Jovan Radojičić
 */
public class Server extends Thread {

    /** Port na kom server osluškuje konekcije klijenata. */
    public static final int PORT = 9000;

    private ServerSocket serverSocket;
    private volatile boolean kraj = false;
    private final List<ObradaKlijentskihZahteva> klijenti = new ArrayList<>();

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server je pokrenut na portu " + PORT);

            while (!kraj) {
                Socket socket = serverSocket.accept();
                System.out.println("Novi klijent se povezao!");

                ObradaKlijentskihZahteva nit = new ObradaKlijentskihZahteva(socket);
                klijenti.add(nit);
                nit.start();
            }
        } catch (IOException ex) {
            System.out.println("Server je zaustavljen.");
        }
    }

    /**
     * Zaustavlja server: prekida sve klijentske niti i zatvara serverski
     * soket.
     */
    public void zaustaviServer() {
        kraj = true;
        for (ObradaKlijentskihZahteva nit : klijenti) {
            nit.zaustaviNit();
        }
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException ex) {
            System.out.println("Greska prilikom zatvaranja servera: " + ex.getMessage());
        }
    }
}
