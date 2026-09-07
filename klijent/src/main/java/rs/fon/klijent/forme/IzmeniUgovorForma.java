package rs.fon.klijent.forme;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import rs.fon.domen.Igrac;
import rs.fon.domen.Oprema;
import rs.fon.domen.StavkaUgovora;
import rs.fon.domen.Trener;
import rs.fon.domen.Ugovor;
import rs.fon.klijent.KontrolerKlijent;
import rs.fon.klijent.modeli.ModelTabeleStavke;

/**
 * Forma za izmenu postojećeg ugovora. Pošto server prilikom izmene u
 * potpunosti zamenjuje stavke ugovora onima poslatim sa klijenta, ovde se
 * unosi kompletan novi spisak stavki (a ne samo razlika u odnosu na
 * postojeće).
 * @author Jovan Radojičić
 */
public class IzmeniUgovorForma extends JDialog {

    private static final SimpleDateFormat FORMAT_DATUMA = new SimpleDateFormat("dd.MM.yyyy");

    private final Ugovor ugovor;
    private final ModelTabeleStavke modelStavki = new ModelTabeleStavke();
    private final JTable tabelaStavki = new JTable(modelStavki);

    private final JComboBox<Trener> comboTrener = new JComboBox<>();
    private final JComboBox<Igrac> comboIgrac = new JComboBox<>();
    private final JTextField txtDatumPotpisivanja = new JTextField(10);
    private final JComboBox<Oprema> comboOprema = new JComboBox<>();
    private final JTextField txtPlata = new JTextField(8);
    private final JTextField txtVazenjeDo = new JTextField(10);

    public IzmeniUgovorForma(Frame parent, Ugovor ugovor) {
        super(parent, "Izmena ugovora #" + ugovor.getIdUgovor(), true);
        this.ugovor = ugovor;
        txtDatumPotpisivanja.setText(FORMAT_DATUMA.format(ugovor.getDatumPotpisivanja()));

        add(napraviGlavniPanel(), BorderLayout.CENTER);

        popuniKomboBoksove();

        setSize(600, 500);
        setLocationRelativeTo(parent);
    }

    private JPanel napraviGlavniPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel info = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; info.add(new JLabel("Trener:"), c);
        c.gridx = 1; info.add(comboTrener, c);
        c.gridx = 2; info.add(new JLabel("Igrač:"), c);
        c.gridx = 3; info.add(comboIgrac, c);

        c.gridx = 0; c.gridy = 1; info.add(new JLabel("Datum potpisivanja (dd.MM.yyyy):"), c);
        c.gridx = 1; info.add(txtDatumPotpisivanja, c);

        c.gridx = 0; c.gridy = 2; info.add(new JLabel("Napomena:"), c);
        c.gridx = 1; c.gridwidth = 3;
        info.add(new JLabel("Unesite KOMPLETAN novi spisak stavki — zamenjuje postojeće."), c);

        JPanel novaStavka = new JPanel(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(4, 4, 4, 4);
        c2.fill = GridBagConstraints.HORIZONTAL;

        c2.gridx = 0; c2.gridy = 0; novaStavka.add(new JLabel("Plata:"), c2);
        c2.gridx = 1; novaStavka.add(txtPlata, c2);
        c2.gridx = 2; novaStavka.add(new JLabel("Oprema:"), c2);
        c2.gridx = 3; novaStavka.add(comboOprema, c2);

        c2.gridx = 0; c2.gridy = 1; novaStavka.add(new JLabel("Važenje do (dd.MM.yyyy):"), c2);
        c2.gridx = 1; novaStavka.add(txtVazenjeDo, c2);
        JButton btnDodajStavku = new JButton("Dodaj stavku");
        btnDodajStavku.addActionListener(e -> dodajStavku());
        c2.gridx = 2; c2.gridwidth = 2; novaStavka.add(btnDodajStavku, c2);

        JPanel gornjiDeo = new JPanel(new BorderLayout());
        gornjiDeo.add(info, BorderLayout.NORTH);
        gornjiDeo.add(novaStavka, BorderLayout.SOUTH);

        panel.add(gornjiDeo, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabelaStavki), BorderLayout.CENTER);

        JPanel dugmici = new JPanel();
        JButton btnUkloniStavku = new JButton("Ukloni selektovanu stavku");
        btnUkloniStavku.addActionListener(e -> ukloniStavku());
        JButton btnSacuvaj = new JButton("Sačuvaj izmene");
        btnSacuvaj.addActionListener(e -> sacuvajIzmene());
        dugmici.add(btnUkloniStavku);
        dugmici.add(btnSacuvaj);
        panel.add(dugmici, BorderLayout.SOUTH);

        return panel;
    }

    private void popuniKomboBoksove() {
        try {
            for (Trener trener : KontrolerKlijent.getInstance().vratiTrenere()) {
                comboTrener.addItem(trener);
            }
            comboTrener.setSelectedItem(ugovor.getTrener());
            for (Igrac igrac : KontrolerKlijent.getInstance().vratiIgrace()) {
                comboIgrac.addItem(igrac);
            }
            comboIgrac.setSelectedItem(ugovor.getIgrac());
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

    private void sacuvajIzmene() {
        if (modelStavki.getStavke().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ugovor mora imati bar jednu stavku.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Trener trener = (Trener) comboTrener.getSelectedItem();
        Igrac igrac = (Igrac) comboIgrac.getSelectedItem();
        if (trener == null || igrac == null) {
            JOptionPane.showMessageDialog(this, "Morate izabrati trenera i igrača.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            FORMAT_DATUMA.setLenient(false);
            Date datumPotpisivanja = FORMAT_DATUMA.parse(txtDatumPotpisivanja.getText().trim());

            ugovor.setTrener(trener);
            ugovor.setIgrac(igrac);
            ugovor.setDatumPotpisivanja(datumPotpisivanja);
            ugovor.setStavke(modelStavki.getStavke());

            KontrolerKlijent.getInstance().izmeniUgovor(ugovor);
            JOptionPane.showMessageDialog(this, "Ugovor je uspešno izmenjen.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Datum potpisivanja mora biti u formatu dd.MM.yyyy.", "Greška", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
