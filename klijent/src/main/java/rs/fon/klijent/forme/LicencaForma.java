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
import rs.fon.domen.Licenca;
import rs.fon.klijent.KontrolerKlijent;
import rs.fon.klijent.modeli.ModelTabeleLicenca;

/**
 * Forma za pregled, dodavanje i brisanje licenci. Isti {@code GroupLayout} i
 * isti izbor iz padajućih listi kao stari {@code PrikazLicenciForma}
 * (ispravljen copy-paste tekst naslova — "Dodaj novo mesto:" u starom kodu,
 * ovde "Dodaj novu licencu:"). Server sada sam dodeljuje ID (vidi
 * {@link rs.fon.baza.LicencaRepository#sledeciId()}).
 * @author Jovan Radojičić
 */
public class LicencaForma extends JDialog {

    private final ModelTabeleLicenca model = new ModelTabeleLicenca(new java.util.ArrayList<>());
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final JTable tblLicence = new JTable(model);
    private final JButton btnObrisi = new JButton("Obriši licencu");
    private final JButton btnDodaj = new JButton("Dodaj licencu");
    private final JLabel jLabel1 = new JLabel("Dodaj novu licencu:");
    private final JLabel jLabel2 = new JLabel("Tip licence:");
    private final JLabel jLabel3 = new JLabel("Nivo licence:");
    private final JComboBox<String> cmbTip = new JComboBox<>(new String[]{"Trenerska", "Medicinska", "Kondiciona", "Skauting"});
    private final JComboBox<String> cmbNivo = new JComboBox<>(new String[]{"Početni", "Medior", "Pro", "Ekspert"});

    public LicencaForma(Frame parent) {
        super(parent, true);
        setTitle("Licence");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setViewportView(tblLicence);
        btnObrisi.addActionListener(e -> obrisiLicencu());
        btnDodaj.addActionListener(e -> dodajLicencu());

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
                                        .addComponent(btnDodaj, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cmbNivo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(6, 6, 6))))
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(btnObrisi, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(cmbNivo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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

    private void dodajLicencu() {
        String tip = (String) cmbTip.getSelectedItem();
        String nivo = (String) cmbNivo.getSelectedItem();

        try {
            Licenca licenca = new Licenca(1, tip, nivo);
            KontrolerKlijent.getInstance().dodajLicencu(licenca);
            JOptionPane.showMessageDialog(this, "Sistem je zapamtio licencu", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            osveziTabelu();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sistem ne moze da zapamti licencu: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obrisiLicencu() {
        int red = tblLicence.getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(this, "Morate selektovati licencu za brisanje!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Licenca selektovana = model.getLicencaAt(red);

        int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (potvrda == JOptionPane.YES_OPTION) {
            try {
                KontrolerKlijent.getInstance().obrisiLicencu(selektovana);
                JOptionPane.showMessageDialog(this, "Sistem je uspešno obrisao licencu!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                osveziTabelu();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Sistem ne može da obriše licencu: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void osveziTabelu() {
        try {
            List<Licenca> lista = KontrolerKlijent.getInstance().vratiLicence();
            model.osveziPodatke(lista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju licenci: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
