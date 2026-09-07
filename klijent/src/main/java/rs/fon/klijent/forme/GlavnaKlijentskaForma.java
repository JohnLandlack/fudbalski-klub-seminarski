package rs.fon.klijent.forme;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import rs.fon.domen.Trener;

/**
 * Glavni prozor klijentske aplikacije. Raspored (label + dugme za odjavu) i
 * meni-struktura verni starom projektu (isti GroupLayout, ista podela
 * menija).
 * @author Jovan Radojičić
 */
public class GlavnaKlijentskaForma extends JFrame {

    private final Trener ulogovani;
    private final JLabel lblUlogovani = new JLabel("Ulogovani:");
    private final JButton btnOdjaviSe = new JButton("Odjavi se");

    public GlavnaKlijentskaForma(Trener ulogovani) {
        this.ulogovani = ulogovani;
        setTitle("Klijentska forma");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        lblUlogovani.setText("Ulogovani: " + ulogovani.getIme() + " " + ulogovani.getPrezime());
        btnOdjaviSe.addActionListener(e -> odjaviSe());

        setJMenuBar(napraviMeni());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblUlogovani, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
                .addComponent(btnOdjaviSe, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(207, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUlogovani)
                    .addComponent(btnOdjaviSe))
                .addGap(11, 11, 11))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private JMenuBar napraviMeni() {
        JMenuBar meniBar = new JMenuBar();

        JMenu meniUgovori = new JMenu("Ugovori");
        JMenuItem stavkaUnosUgovora = new JMenuItem("Unos novog ugovora");
        stavkaUnosUgovora.addActionListener(e -> new UgovorForma(this, ulogovani).setVisible(true));
        meniUgovori.add(stavkaUnosUgovora);
        meniBar.add(meniUgovori);

        JMenu meniIgraci = new JMenu("Igrači");
        JMenuItem stavkaIgraci = new JMenuItem("Upravljanje igračima");
        stavkaIgraci.addActionListener(e -> new PrikazIgracaForma(this).setVisible(true));
        meniIgraci.add(stavkaIgraci);
        meniBar.add(meniIgraci);

        JMenu meniTreneri = new JMenu("Treneri");
        JMenuItem stavkaTreneri = new JMenuItem("Upravljanje trenerima");
        stavkaTreneri.addActionListener(e -> new TrenerForma(this).setVisible(true));
        meniTreneri.add(stavkaTreneri);
        meniBar.add(meniTreneri);

        JMenu meniSifarnici = new JMenu("Šifarnici");
        JMenuItem stavkaMesta = new JMenuItem("Mesta");
        stavkaMesta.addActionListener(e -> new MestoForma(this).setVisible(true));
        meniSifarnici.add(stavkaMesta);
        JMenuItem stavkaOpreme = new JMenuItem("Opreme");
        stavkaOpreme.addActionListener(e -> new OpremaForma(this).setVisible(true));
        meniSifarnici.add(stavkaOpreme);
        JMenuItem stavkaLicence = new JMenuItem("Licence");
        stavkaLicence.addActionListener(e -> new LicencaForma(this).setVisible(true));
        meniSifarnici.add(stavkaLicence);
        meniBar.add(meniSifarnici);

        return meniBar;
    }

    private void odjaviSe() {
        int odgovor = JOptionPane.showConfirmDialog(
                this,
                "Da li ste sigurni da želite da se odjavite i izađete iz aplikacije?",
                "Potvrda odjave",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (odgovor == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
