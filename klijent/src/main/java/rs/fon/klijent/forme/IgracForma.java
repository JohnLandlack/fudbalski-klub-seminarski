package rs.fon.klijent.forme;

import java.awt.Frame;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;
import rs.fon.klijent.KontrolerKlijent;

/**
 * Forma za unos novog igrača ili izmenu postojećeg. Isti {@code GroupLayout}
 * kao stari {@code IgracForma} — ID polje je, kao i u starom kodu, uvek
 * neizmenjivo (server ga sam dodeljuje, vidi
 * {@link rs.fon.baza.IgracRepository#sledeciId()}; kod unosa prikazuje
 * "Automatski", kod izmene stvarni ID).
 * @author Jovan Radojičić
 */
public class IgracForma extends JDialog {

    private final JLabel jLabel1 = new JLabel("Id:");
    private final JLabel jLabel2 = new JLabel("Ime:");
    private final JLabel jLabel3 = new JLabel("Prezime:");
    private final JLabel jLabel4 = new JLabel("Telefon:");
    private final JLabel jLabel5 = new JLabel("Pozicija:");
    private final JLabel jLabel6 = new JLabel("Mesto:");
    private final JButton jButton1 = new JButton("Sacuvaj");
    private final JTextField txtId = new JTextField();
    private final JTextField txtIme = new JTextField();
    private final JTextField txtPrezime = new JTextField();
    private final JTextField txtTelefon = new JTextField();
    private final JComboBox<String> cbPozicija = new JComboBox<>();
    private final JComboBox<Mesto> cbMesto = new JComboBox<>();

    private Igrac igrac;
    private boolean izmena = false;

    public IgracForma(Frame parent) {
        super(parent, true);
        initComponents();
        setTitle("Unos novog igrača");
        this.izmena = false;
        txtId.setText("Automatski");
        popuniMesta();
        popuniPozicije();
        setLocationRelativeTo(parent);
    }

    public IgracForma(Frame parent, Igrac selektovaniIgrac) {
        super(parent, true);
        initComponents();
        setTitle("Izmena podataka o igraču");
        this.igrac = selektovaniIgrac;
        this.izmena = true;

        popuniMesta();
        popuniPozicije();

        txtId.setText(String.valueOf(igrac.getIdIgrac()));
        txtIme.setText(igrac.getIme());
        txtPrezime.setText(igrac.getPrezime());
        txtTelefon.setText(igrac.getTelefon());
        cbPozicija.setSelectedItem(igrac.getPozicija());
        cbMesto.setSelectedItem(igrac.getMesto());
        setLocationRelativeTo(parent);
    }

    private void popuniMesta() {
        try {
            List<Mesto> mesta = KontrolerKlijent.getInstance().vratiMesta();
            cbMesto.removeAllItems();
            for (Mesto m : mesta) {
                cbMesto.addItem(m);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju mesta: " + ex.getMessage());
        }
    }

    private void popuniPozicije() {
        cbPozicija.removeAllItems();
        cbPozicija.addItem("Plejmejker");
        cbPozicija.addItem("Bek");
        cbPozicija.addItem("Krilo");
        cbPozicija.addItem("Krilni centar");
        cbPozicija.addItem("Centar");
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        txtId.setEditable(false);
        jButton1.addActionListener(e -> jButton1ActionPerformed());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIme))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrezime))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefon))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbPozicija, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMesto, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrezime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbPozicija, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbMesto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jButton1)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1ActionPerformed() {
        String ime = txtIme.getText().trim();
        String prezime = txtPrezime.getText().trim();
        String telefon = txtTelefon.getText().trim();
        String pozicija = (String) cbPozicija.getSelectedItem();
        Mesto mesto = (Mesto) cbMesto.getSelectedItem();

        if (ime.isEmpty() || prezime.isEmpty() || telefon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sistem ne moze da zapamti igraca", "Upozorenje", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (!izmena) {
                Igrac novi = new Igrac(1, ime, prezime, telefon, pozicija, mesto);
                KontrolerKlijent.getInstance().dodajIgraca(novi);
                JOptionPane.showMessageDialog(this, "Sistem je zapamtio igraca", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            } else {
                igrac.setIme(ime);
                igrac.setPrezime(prezime);
                igrac.setTelefon(telefon);
                igrac.setPozicija(pozicija);
                igrac.setMesto(mesto);

                KontrolerKlijent.getInstance().izmeniIgraca(igrac);
                JOptionPane.showMessageDialog(this, "Sistem je zapamtio igraca", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sistem ne moze da zapamti igraca: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
