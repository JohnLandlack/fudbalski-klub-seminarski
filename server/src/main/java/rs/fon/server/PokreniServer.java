package rs.fon.server;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import rs.fon.server.forme.ServerskaForma;

/**
 * Ulazna tačka servera — otvara {@link ServerskaForma} sa koje se server
 * pokreće i zaustavlja.
 * @author Jovan Radojičić
 */
public class PokreniServer {

    public static void main(String[] args) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
                    System.out.println("Nimbus nije dostupan, koristi se podrazumevani izgled.");
                }
                break;
            }
        }
        SwingUtilities.invokeLater(() -> new ServerskaForma().setVisible(true));
    }
}
