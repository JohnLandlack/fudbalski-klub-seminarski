package rs.fon.klijent.forme;

import java.awt.Frame;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import rs.fon.domen.Oprema;
import rs.fon.klijent.KontrolerKlijent;
import rs.fon.klijent.modeli.ModelTabeleOprema;

/**
 * Forma za pregled, dodavanje i brisanje opreme. Isti {@code GroupLayout} i
 * isti izbor iz padajućih listi kao stari {@code PrikazOpremeForma} — server
 * sada sam dodeljuje ID (vidi {@link rs.fon.baza.OpremaRepository#sledeciId()}).
 * @author Jovan Radojičić
 */
public class OpremaForma extends JDialog {

    private final ModelTabeleOprema model = new ModelTabeleOprema(new java.util.ArrayList<>());
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final JTable tblOprema = new JTable(model);
    private final JButton btnObrisi = new JButton("Obriši opremu");
    private final JButton btnDodaj = new JButton("Dodaj opremu");
    private final JLabel jLabel1 = new JLabel("Dodaj novu opremu");
    private final JLabel jLabel2 = new JLabel("Tip dresa:");
    private final JLabel jLabel3 = new JLabel("Vrste dresa:");
    private final JComboBox<String> cmbTip = new JComboBox<>(new String[]{"Prvi", "Drugi", "Treći"});
    private final JComboBox<String> cmbVrsta = new JComboBox<>(new String[]{"Domaći", "Gostujući", "Trening"});

    public OpremaForma(Frame parent) {
        super(parent, true);
        setTitle("Oprema");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setViewportView(tblOprema);
        btnObrisi.addActionListener(e -> obrisiOpremu());
        btnDodaj.addActionListener(e -> dodajOpremu());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 95, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addGap(107, 107, 107))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTip, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnDodaj, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cmbVrsta, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(6, 6, 6))))
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(btnObrisi, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel1)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cmbTip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbVrsta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(btnDodaj)))
                .addGap(18, 18, 18)
                .addComponent(btnObrisi)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
        osveziTabelu();
        setLocationRelativeTo(parent);
    }

    private void dodajOpremu() {
        String tip = (String) cmbTip.getSelectedItem();
        String vrsta = (String) cmbVrsta.getSelectedItem();

        try {
            Oprema oprema = new Oprema(1, tip, vrsta);
            KontrolerKlijent.getInstance().dodajOpremu(oprema);
            JOptionPane.showMessageDialog(this, "Sistem je uspešno zapamtio opremu!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            osveziTabelu();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sistem ne može da zapamti opremu: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obrisiOpremu() {
        int red = tblOprema.getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(this, "Morate selektovati opremu za brisanje!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Oprema selektovana = model.getOpremaAt(red);

        int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (potvrda == JOptionPane.YES_OPTION) {
            try {
                KontrolerKlijent.getInstance().obrisiOpremu(selektovana);
                JOptionPane.showMessageDialog(this, "Sistem je uspešno obrisao opremu!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                osveziTabelu();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Sistem ne može da obriše opremu: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void osveziTabelu() {
        try {
            List<Oprema> lista = KontrolerKlijent.getInstance().vratiOpremu();
            model.osveziPodatke(lista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju opreme: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
