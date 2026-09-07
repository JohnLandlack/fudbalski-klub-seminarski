package rs.fon.klijent.forme;

import java.awt.Frame;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import rs.fon.domen.Mesto;
import rs.fon.klijent.KontrolerKlijent;
import rs.fon.klijent.modeli.ModelTabeleMesto;

/**
 * Forma za pregled, dodavanje i brisanje mesta. Isti {@code GroupLayout} kao
 * stari {@code PrikazMestaForma} — server sada sam dodeljuje ID (vidi
 * {@link rs.fon.baza.MestoRepository#sledeciId()}), pa klijentu više nije
 * potrebno polje za ID.
 * @author Jovan Radojičić
 */
public class MestoForma extends JDialog {

    private final ModelTabeleMesto model = new ModelTabeleMesto(new java.util.ArrayList<>());
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final JTable tblMesta = new JTable(model);
    private final JButton btnObrisi = new JButton("Obriši mesto");
    private final JTextField txtNoviNaziv = new JTextField();
    private final JButton btnDodaj = new JButton("Dodaj mesto");
    private final JLabel jLabel1 = new JLabel("Dodaj novo mesto:");
    private final JLabel jLabel2 = new JLabel("Naziv mesta:");
    private final JLabel jLabel3 = new JLabel("Postanski broj:");
    private final JTextField txtPostanskiBroj = new JTextField();

    public MestoForma(Frame parent) {
        super(parent, true);
        setTitle("Mesta");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setViewportView(tblMesta);
        btnObrisi.addActionListener(e -> obrisiMesto());
        btnDodaj.addActionListener(e -> dodajMesto());

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
                        .addGap(95, 95, 95))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNoviNaziv))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnDodaj, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(txtPostanskiBroj)))))
                .addGap(12, 12, 12))
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
                            .addComponent(txtNoviNaziv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPostanskiBroj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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

    private void dodajMesto() {
        String naziv = txtNoviNaziv.getText().trim();
        String pttString = txtPostanskiBroj.getText().trim();

        if (naziv.isEmpty() || pttString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Morate uneti i naziv i poštanski broj!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Mesto mesto = new Mesto(1, naziv, pttString);
            KontrolerKlijent.getInstance().dodajMesto(mesto);
            JOptionPane.showMessageDialog(this, "Sistem je uspešno zapamtio mesto!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

            txtNoviNaziv.setText("");
            txtPostanskiBroj.setText("");
            osveziTabelu();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sistem ne može da zapamti mesto: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obrisiMesto() {
        int red = tblMesta.getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(this, "Morate selektovati mesto u tabeli za brisanje!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Mesto selektovanoMesto = model.getMestoAt(red);

        int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete mesto: "
                + selektovanoMesto.getNaziv() + "?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (potvrda == JOptionPane.YES_OPTION) {
            try {
                KontrolerKlijent.getInstance().obrisiMesto(selektovanoMesto);
                JOptionPane.showMessageDialog(this, "Sistem je uspešno obrisao mesto!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                osveziTabelu();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Sistem ne može da obriše mesto (moguće je da se koristi u ugovorima): "
                        + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void osveziTabelu() {
        try {
            List<Mesto> listaMesta = KontrolerKlijent.getInstance().vratiMesta();
            model.osveziPodatke(listaMesta);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju mesta: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
