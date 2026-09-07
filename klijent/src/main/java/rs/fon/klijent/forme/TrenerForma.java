package rs.fon.klijent.forme;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import rs.fon.domen.Trener;
import rs.fon.klijent.KontrolerKlijent;
import rs.fon.klijent.modeli.ModelTabeleTrener;

/**
 * Forma za pregled i dodavanje trenera, sa prečicom ka dodeli licence.
 * @author Jovan Radojičić
 */
public class TrenerForma extends JDialog {

    private final ModelTabeleTrener model = new ModelTabeleTrener(new java.util.ArrayList<>());
    private final JTextField txtIme = new JTextField(12);
    private final JTextField txtPrezime = new JTextField(12);
    private final JTextField txtKorisnickoIme = new JTextField(12);
    private final JTextField txtSifra = new JTextField(12);

    public TrenerForma(Frame parent) {
        super(parent, "Treneri", true);

        JTable tabela = new JTable(model);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(napraviFormuZaDodavanje(parent), BorderLayout.EAST);

        osveziTabelu();

        setSize(650, 350);
        setLocationRelativeTo(parent);
    }

    private JPanel napraviFormuZaDodavanje(Frame parent) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        panel.add(new JLabel("Dodaj novog trenera:"), c);
        c.gridwidth = 1;

        c.gridx = 0; c.gridy = 1; panel.add(new JLabel("Ime:"), c);
        c.gridx = 1; panel.add(txtIme, c);

        c.gridx = 0; c.gridy = 2; panel.add(new JLabel("Prezime:"), c);
        c.gridx = 1; panel.add(txtPrezime, c);

        c.gridx = 0; c.gridy = 3; panel.add(new JLabel("Korisničko ime:"), c);
        c.gridx = 1; panel.add(txtKorisnickoIme, c);

        c.gridx = 0; c.gridy = 4; panel.add(new JLabel("Šifra:"), c);
        c.gridx = 1; panel.add(txtSifra, c);

        JButton btnDodaj = new JButton("Dodaj trenera");
        btnDodaj.addActionListener(e -> dodajTrenera());
        c.gridx = 0; c.gridy = 5; c.gridwidth = 2; panel.add(btnDodaj, c);

        JButton btnDodeliLicencu = new JButton("Dodeli licencu treneru...");
        btnDodeliLicencu.addActionListener(e -> new DodeliLicencuForma(parent).setVisible(true));
        c.gridx = 0; c.gridy = 6; c.gridwidth = 2; panel.add(btnDodeliLicencu, c);

        return panel;
    }

    private void dodajTrenera() {
        try {
            Trener trener = new Trener(1, txtIme.getText().trim(), txtPrezime.getText().trim(),
                    txtKorisnickoIme.getText().trim(), txtSifra.getText().trim());
            KontrolerKlijent.getInstance().dodajTrenera(trener);
            JOptionPane.showMessageDialog(this, "Trener je uspešno dodat.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            txtIme.setText("");
            txtPrezime.setText("");
            txtKorisnickoIme.setText("");
            txtSifra.setText("");
            osveziTabelu();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void osveziTabelu() {
        try {
            List<Trener> lista = KontrolerKlijent.getInstance().vratiTrenere();
            model.osveziPodatke(lista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju trenera: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
