package rs.fon.klijent.forme;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import rs.fon.domen.Licenca;
import rs.fon.domen.Trener;
import rs.fon.domen.TrenerLicenca;
import rs.fon.klijent.KontrolerKlijent;

/**
 * Forma za dodelu postojeće licence postojećem treneru.
 * @author Jovan Radojičić
 */
public class DodeliLicencuForma extends JDialog {

    private static final SimpleDateFormat FORMAT_DATUMA = new SimpleDateFormat("dd.MM.yyyy");

    private final JComboBox<Trener> comboTrener = new JComboBox<>();
    private final JComboBox<Licenca> comboLicenca = new JComboBox<>();
    private final JTextField txtDatumIzdavanja = new JTextField(10);
    private final JTextField txtDatumIsteka = new JTextField(10);

    public DodeliLicencuForma(Frame parent) {
        super(parent, "Dodela licence treneru", true);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; add(new JLabel("Trener:"), c);
        c.gridx = 1; add(comboTrener, c);

        c.gridx = 0; c.gridy = 1; add(new JLabel("Licenca:"), c);
        c.gridx = 1; add(comboLicenca, c);

        c.gridx = 0; c.gridy = 2; add(new JLabel("Datum izdavanja (dd.MM.yyyy):"), c);
        c.gridx = 1; add(txtDatumIzdavanja, c);

        c.gridx = 0; c.gridy = 3; add(new JLabel("Datum isteka (dd.MM.yyyy):"), c);
        c.gridx = 1; add(txtDatumIsteka, c);

        JButton btnDodeli = new JButton("Dodeli licencu");
        btnDodeli.addActionListener(e -> dodeliLicencu());
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2; add(btnDodeli, c);

        popuniKomboBoksove();

        setSize(400, 250);
        setLocationRelativeTo(parent);
    }

    private void popuniKomboBoksove() {
        try {
            for (Trener trener : KontrolerKlijent.getInstance().vratiTrenere()) {
                comboTrener.addItem(trener);
            }
            for (Licenca licenca : KontrolerKlijent.getInstance().vratiLicence()) {
                comboLicenca.addItem(licenca);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dodeliLicencu() {
        Trener trener = (Trener) comboTrener.getSelectedItem();
        Licenca licenca = (Licenca) comboLicenca.getSelectedItem();
        if (trener == null || licenca == null) {
            JOptionPane.showMessageDialog(this, "Morate izabrati trenera i licencu.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            FORMAT_DATUMA.setLenient(false);
            Date datumIzdavanja = FORMAT_DATUMA.parse(txtDatumIzdavanja.getText().trim());
            Date datumIsteka = FORMAT_DATUMA.parse(txtDatumIsteka.getText().trim());

            TrenerLicenca trenerLicenca = new TrenerLicenca(trener, licenca, datumIzdavanja, datumIsteka);
            KontrolerKlijent.getInstance().dodeliLicencuTreneru(trenerLicenca);

            JOptionPane.showMessageDialog(this, "Licenca je uspešno dodeljena.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Datumi moraju biti u formatu dd.MM.yyyy.", "Greška", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
