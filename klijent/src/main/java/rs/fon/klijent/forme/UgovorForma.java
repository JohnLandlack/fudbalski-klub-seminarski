package rs.fon.klijent.forme;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import rs.fon.domen.Igrac;
import rs.fon.domen.Oprema;
import rs.fon.domen.StavkaUgovora;
import rs.fon.domen.Trener;
import rs.fon.domen.Ugovor;
import rs.fon.klijent.KontrolerKlijent;
import rs.fon.klijent.modeli.ModelTabeleStavke;
import rs.fon.klijent.modeli.ModelTabeleUgovor;

/**
 * Forma za pregled ugovora (sa pretragom po treneru/igraču), unos novog
 * ugovora (sa stavkama), izmenu i brisanje postojećeg ugovora. Isti skup
 * polja i ista logika kao stari {@code UgovorForma} — trener novog ugovora
 * je uvek ulogovani trener (ne bira se iz liste, kao ni u starom projektu).
 * @author Jovan Radojičić
 */
public class UgovorForma extends JDialog {

    private static final SimpleDateFormat FORMAT_DATUMA = new SimpleDateFormat("dd.MM.yyyy");

    private final Frame parentFrame;
    private final Trener ulogovani;

    private final ModelTabeleUgovor modelUgovora = new ModelTabeleUgovor(new ArrayList<>());
    private final JTable tabelaUgovora = new JTable(modelUgovora);
    private List<Ugovor> sviUgovori = new ArrayList<>();

    private final ModelTabeleStavke modelStavki = new ModelTabeleStavke();
    private final JTable tabelaStavki = new JTable(modelStavki);

    private final JTextField txtUlogovaniTrener = new JTextField(15);
    private final JComboBox<Igrac> comboIgrac = new JComboBox<>();
    private final JTextField txtDatumPotpisivanja = new JTextField(10);

    private final JComboBox<Oprema> comboOprema = new JComboBox<>();
    private final JTextField txtPlata = new JTextField(8);
    private final JTextField txtVazenjeDo = new JTextField(10);

    private final JTextField txtImeIgrac = new JTextField(10);
    private final JTextField txtPrezimeIgrac = new JTextField(10);
    private final JTextField txtImeTrener = new JTextField(10);
    private final JTextField txtPrezimeTrener = new JTextField(10);

    public UgovorForma(Frame parent, Trener ulogovani) {
        super(parent, "Unos ugovora", true);
        this.parentFrame = parent;
        this.ulogovani = ulogovani;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        txtUlogovaniTrener.setText(ulogovani.getIme() + " " + ulogovani.getPrezime());
        txtUlogovaniTrener.setEditable(false);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, napraviPanelUgovora(), napraviPanelNovogUgovora());
        split.setResizeWeight(0.45);
        add(split, BorderLayout.CENTER);

        popuniKomboBoksove();
        osveziTabeluUgovora();

        setSize(850, 700);
        setLocationRelativeTo(parent);
    }

    private JPanel napraviPanelUgovora() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel filter = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; filter.add(new JLabel("Ime igrača:"), c);
        c.gridx = 1; filter.add(txtImeIgrac, c);
        c.gridx = 2; filter.add(new JLabel("Prezime igrača:"), c);
        c.gridx = 3; filter.add(txtPrezimeIgrac, c);

        c.gridx = 0; c.gridy = 1; filter.add(new JLabel("Ime trenera:"), c);
        c.gridx = 1; filter.add(txtImeTrener, c);
        c.gridx = 2; filter.add(new JLabel("Prezime trenera:"), c);
        c.gridx = 3; filter.add(txtPrezimeTrener, c);

        JButton btnFiltriraj = new JButton("Filtriraj");
        btnFiltriraj.addActionListener(e -> filtriraj());
        c.gridx = 0; c.gridy = 2; filter.add(btnFiltriraj, c);
        JButton btnResetujFilter = new JButton("Resetuj filter");
        btnResetujFilter.addActionListener(e -> resetujFilter());
        c.gridx = 1; filter.add(btnResetujFilter, c);

        panel.add(filter, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabelaUgovora), BorderLayout.CENTER);

        JPanel dugmici = new JPanel();
        JButton btnIzmeni = new JButton("Izmeni ugovor");
        btnIzmeni.addActionListener(e -> izmeniSelektovaniUgovor());
        JButton btnObrisi = new JButton("Obriši ugovor");
        btnObrisi.addActionListener(e -> obrisiSelektovaniUgovor());
        dugmici.add(btnIzmeni);
        dugmici.add(btnObrisi);
        panel.add(dugmici, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel napraviPanelNovogUgovora() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Unos novog ugovora:"), BorderLayout.NORTH);

        JPanel osnovniPodaci = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; osnovniPodaci.add(new JLabel("Trener:"), c);
        c.gridx = 1; osnovniPodaci.add(txtUlogovaniTrener, c);

        c.gridx = 0; c.gridy = 1; osnovniPodaci.add(new JLabel("Igrač:"), c);
        c.gridx = 1; osnovniPodaci.add(comboIgrac, c);
        c.gridx = 2; osnovniPodaci.add(new JLabel("Datum potpisivanja (dd.MM.yyyy):"), c);
        c.gridx = 3; osnovniPodaci.add(txtDatumPotpisivanja, c);

        c.gridx = 0; c.gridy = 2; osnovniPodaci.add(new JLabel("Plata:"), c);
        c.gridx = 1; osnovniPodaci.add(txtPlata, c);
        c.gridx = 2; osnovniPodaci.add(new JLabel("Oprema:"), c);
        c.gridx = 3; osnovniPodaci.add(comboOprema, c);

        c.gridx = 0; c.gridy = 3; osnovniPodaci.add(new JLabel("Važenje do (dd.MM.yyyy):"), c);
        c.gridx = 1; osnovniPodaci.add(txtVazenjeDo, c);
        JButton btnDodajStavku = new JButton("Dodaj stavku");
        btnDodajStavku.addActionListener(e -> dodajStavku());
        c.gridx = 2; c.gridwidth = 2; osnovniPodaci.add(btnDodajStavku, c);

        panel.add(osnovniPodaci, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabelaStavki), BorderLayout.CENTER);

        JPanel dugmici = new JPanel();
        JButton btnUkloniStavku = new JButton("Ukloni selektovanu stavku");
        btnUkloniStavku.addActionListener(e -> ukloniStavku());
        JButton btnSacuvaj = new JButton("Sačuvaj ugovor");
        btnSacuvaj.addActionListener(e -> sacuvajUgovor());
        dugmici.add(btnUkloniStavku);
        dugmici.add(btnSacuvaj);
        panel.add(dugmici, BorderLayout.SOUTH);

        return panel;
    }

    private void popuniKomboBoksove() {
        try {
            for (Igrac igrac : KontrolerKlijent.getInstance().vratiIgrace()) {
                comboIgrac.addItem(igrac);
            }
            for (Oprema oprema : KontrolerKlijent.getInstance().vratiOpremu()) {
                comboOprema.addItem(oprema);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dodajStavku() {
        Oprema oprema = (Oprema) comboOprema.getSelectedItem();
        if (oprema == null) {
            JOptionPane.showMessageDialog(this, "Ne postoji nijedna oprema — prvo dodajte opremu.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int plata = Integer.parseInt(txtPlata.getText().trim());
            FORMAT_DATUMA.setLenient(false);
            Date vazenjeDo = FORMAT_DATUMA.parse(txtVazenjeDo.getText().trim());

            StavkaUgovora stavka = new StavkaUgovora();
            stavka.setPlata(plata);
            stavka.setOprema(oprema);
            stavka.setVazenjeUgovora(vazenjeDo);
            modelStavki.dodajStavku(stavka);

            txtPlata.setText("");
            txtVazenjeDo.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Plata mora biti ceo broj.", "Greška", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Datum mora biti u formatu dd.MM.yyyy.", "Greška", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ukloniStavku() {
        int red = tabelaStavki.getSelectedRow();
        if (red != -1) {
            modelStavki.ukloniStavku(red);
        }
    }

    private void sacuvajUgovor() {
        Igrac igrac = (Igrac) comboIgrac.getSelectedItem();
        if (igrac == null) {
            JOptionPane.showMessageDialog(this, "Morate izabrati igrača.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (modelStavki.getStavke().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ugovor mora imati bar jednu stavku.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            FORMAT_DATUMA.setLenient(false);
            Date datumPotpisivanja = FORMAT_DATUMA.parse(txtDatumPotpisivanja.getText().trim());

            Ugovor ugovor = new Ugovor(1, datumPotpisivanja, ulogovani, igrac);
            ugovor.setStavke(modelStavki.getStavke());

            KontrolerKlijent.getInstance().dodajUgovor(ugovor);
            JOptionPane.showMessageDialog(this, "Sistem je uspešno zapamtio ugovor sa stavkama!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

            modelStavki.osveziPodatke(new ArrayList<>());
            txtDatumPotpisivanja.setText("");
            osveziTabeluUgovora();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Datum potpisivanja mora biti u formatu dd.MM.yyyy.", "Greška", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sistem ne može da zapamti ugovor:\n" + ex, "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void izmeniSelektovaniUgovor() {
        int red = tabelaUgovora.getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(this, "Sistem ne može da učita ugovor. Morate selektovati ugovor iz tabele!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Ugovor ugovor = modelUgovora.getUgovorAt(red);
        IzmeniUgovorForma forma = new IzmeniUgovorForma(parentFrame, ugovor);
        forma.setVisible(true);
        osveziTabeluUgovora();
    }

    private void obrisiSelektovaniUgovor() {
        int red = tabelaUgovora.getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(this, "Morate selektovati ugovor želite da obrišete!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Ugovor ugovor = modelUgovora.getUgovorAt(red);
        int odgovor = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete ovaj ugovor i sve njegove stavke?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (odgovor == JOptionPane.YES_OPTION) {
            try {
                KontrolerKlijent.getInstance().obrisiUgovor(ugovor);
                JOptionPane.showMessageDialog(this, "Sistem je uspešno obrisao ugovor!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                osveziTabeluUgovora();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Sistem ne može da obriše ugovor: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void filtriraj() {
        try {
            String imeT = txtImeTrener.getText().trim().toLowerCase();
            String prezimeT = txtPrezimeTrener.getText().trim().toLowerCase();
            String imeI = txtImeIgrac.getText().trim().toLowerCase();
            String prezimeI = txtPrezimeIgrac.getText().trim().toLowerCase();

            List<Ugovor> filtrirani = new ArrayList<>();
            for (Ugovor u : sviUgovori) {
                if (u.getTrener().getIme().toLowerCase().contains(imeT)
                        && u.getTrener().getPrezime().toLowerCase().contains(prezimeT)
                        && u.getIgrac().getIme().toLowerCase().contains(imeI)
                        && u.getIgrac().getPrezime().toLowerCase().contains(prezimeI)) {
                    filtrirani.add(u);
                }
            }

            modelUgovora.osveziPodatke(filtrirani);

            if (filtrirani.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sistem ne moze da nadje ugovore po zadatim kriterijumima", "Sistem", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sistem je nasao ugovore po zadatim kriterijumima", "Sistem", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri filtriranju: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetujFilter() {
        txtImeIgrac.setText("");
        txtPrezimeIgrac.setText("");
        txtImeTrener.setText("");
        txtPrezimeTrener.setText("");
        modelUgovora.osveziPodatke(sviUgovori);
    }

    private void osveziTabeluUgovora() {
        try {
            sviUgovori = KontrolerKlijent.getInstance().vratiUgovore();
            modelUgovora.osveziPodatke(sviUgovori);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju ugovora: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
