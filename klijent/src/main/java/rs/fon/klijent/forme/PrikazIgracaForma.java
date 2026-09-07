package rs.fon.klijent.forme;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
import rs.fon.domen.Igrac;
import rs.fon.klijent.KontrolerKlijent;
import rs.fon.klijent.modeli.ModelTabeleIgrac;

/**
 * Forma za pregled, pretragu, dodavanje, izmenu i brisanje igrača. Raspored
 * veran starom {@code PrikazIgracaForma} (isti GroupLayout).
 * @author Jovan Radojičić
 */
public class PrikazIgracaForma extends JDialog {

    private final JLabel jLabel1 = new JLabel("Pretraga:");
    private final JTextField txtPretraga = new JTextField();
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final JTable tblIgraci = new JTable();
    private final JButton btnDodaj = new JButton("Dodaj novog igrača");
    private final JButton btnIzmeni = new JButton("Izmeni odabranog igrača");
    private final JButton btnObrisi = new JButton("Obriši igrača");
    private final JButton btnFiltriraj = new JButton("Filtriraj");
    private final JButton btnResetujFilter = new JButton("Resetuj filter");

    private List<Igrac> originalnaLista = new ArrayList<>();
    private ModelTabeleIgrac model;

    public PrikazIgracaForma(Frame parent) {
        super(parent, true);
        setTitle("Upravljanje igračima");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        tblIgraci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                tblIgraciMouseClicked();
            }
        });
        jScrollPane1.setViewportView(tblIgraci);

        btnDodaj.addActionListener(e -> btnDodajActionPerformed());
        btnIzmeni.addActionListener(e -> btnIzmeniActionPerformed());
        btnObrisi.addActionListener(e -> btnObrisiActionPerformed());
        btnFiltriraj.addActionListener(e -> btnFiltrirajActionPerformed());
        btnResetujFilter.addActionListener(e -> btnResetujFilterActionPerformed());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPretraga, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFiltriraj, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnResetujFilter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnDodaj, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnIzmeni, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnObrisi, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPretraga, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltriraj)
                    .addComponent(btnResetujFilter))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDodaj)
                        .addComponent(btnIzmeni))
                    .addComponent(btnObrisi))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(parent);
        osveziTabelu();
    }

    private void osveziTabelu() {
        try {
            originalnaLista = KontrolerKlijent.getInstance().vratiIgrace();
            model = new ModelTabeleIgrac(originalnaLista);
            tblIgraci.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju igrača: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnObrisiActionPerformed() {
        int red = tblIgraci.getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(this, "Sistem ne moze da obrise igraca", "Upozorenje", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Igrac selektovani = model.getIgracAt(red);
        int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete igrača: "
                + selektovani.getIme() + " " + selektovani.getPrezime() + "?", "Potvrda", JOptionPane.YES_NO_OPTION);

        if (potvrda == JOptionPane.YES_OPTION) {
            try {
                KontrolerKlijent.getInstance().obrisiIgraca(selektovani);
                JOptionPane.showMessageDialog(this, "Sistem je obrisao igraca", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                osveziTabelu();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Sistem ne može da obrise igraca: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnFiltrirajActionPerformed() {
        if (model != null) {
            String tekst = txtPretraga.getText().trim();
            model.osveziSaFilterom(originalnaLista, tekst);

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Sistem ne moze da nadje igrace po zadatim kriterijumima", "Sistem", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sistem je nasao igrace po zadatim kriterijumima", "Sistem", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void btnDodajActionPerformed() {
        IgracForma forma = new IgracForma((Frame) getParent());
        forma.setVisible(true);
        osveziTabelu();
    }

    private void btnIzmeniActionPerformed() {
        int red = tblIgraci.getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(this, "Morate selektovati igrača koga želite menjati!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Igrac selektovani = model.getIgracAt(red);
        IgracForma forma = new IgracForma((Frame) getParent(), selektovani);
        forma.setVisible(true);
        osveziTabelu();
    }

    private void btnResetujFilterActionPerformed() {
        txtPretraga.setText("");
        if (model != null) {
            model.osveziSaFilterom(originalnaLista, "");
        }
    }

    private void tblIgraciMouseClicked() {
        int red = tblIgraci.getSelectedRow();
        if (red != -1) {
            JOptionPane.showMessageDialog(this, "Sistem je nasao igraca", "Sistem", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
