package rs.fon.klijent.forme;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import rs.fon.domen.Trener;
import rs.fon.klijent.KontrolerKlijent;

/**
 * Forma za prijavu trenera na sistem. Raspored polja veran starom projektu
 * (isti GroupLayout).
 * @author Jovan Radojičić
 */
public class LoginForma extends JFrame {

    private final JLabel jLabel1 = new JLabel("Username:");
    private final JLabel jLabel2 = new JLabel("Password:");
    private final JTextField txtUsername = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();
    private final JButton btnLogin = new JButton("Uloguj se");

    public LoginForma() {
        setTitle("Login forma");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnLogin.addActionListener(e -> prijaviSe());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassword))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsername)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnLogin)
                .addGap(23, 23, 23))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void prijaviSe() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Morate uneti korisničko ime i lozinku!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Trener ulogovani = KontrolerKlijent.getInstance().login(username, password);
            JOptionPane.showMessageDialog(this, "Korisnicko ime i sifra su ispravni.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            new GlavnaKlijentskaForma(ulogovani).setVisible(true);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Korisnicko ime i sifra nisu ispravni", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
