package rs.fon.klijent;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import rs.fon.klijent.forme.LoginForma;

/**
 * Ulazna tačka klijentske aplikacije.
 * @author Jovan Radojičić
 */
public class PokreniKlijent {

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
        SwingUtilities.invokeLater(() -> new LoginForma().setVisible(true));
    }
}
