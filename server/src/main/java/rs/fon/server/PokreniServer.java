package rs.fon.server;

/**
 * Ulazna tačka servera — pokreće {@link Server} nit.
 * @author Jovan Radojičić
 */
public class PokreniServer {

    public static void main(String[] args) {
        new Server().start();
    }
}
