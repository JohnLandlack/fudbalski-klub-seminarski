package rs.fon.klijent.modeli;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.fon.domen.StavkaUgovora;

/**
 * Model tabele za prikaz i uređivanje stavki ugovora u formi za unos/izmenu
 * ugovora. Redni broj stavke se automatski dodeljuje pri dodavanju.
 * @author Jovan Radojičić
 */
public class ModelTabeleStavke extends AbstractTableModel {

    private static final SimpleDateFormat FORMAT_DATUMA = new SimpleDateFormat("dd.MM.yyyy");

    private final String[] kolone = {"RB", "Važenje do", "Plata", "Oprema"};
    private List<StavkaUgovora> stavke = new ArrayList<>();

    @Override
    public int getRowCount() {
        return stavke.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaUgovora stavka = stavke.get(rowIndex);
        switch (columnIndex) {
            case 0: return stavka.getRbStavkaUgovora();
            case 1: return FORMAT_DATUMA.format(stavka.getVazenjeUgovora());
            case 2: return stavka.getPlata();
            case 3: return stavka.getOprema().toString();
            default: return null;
        }
    }

    /**
     * Dodaje stavku i dodeljuje joj sledeći slobodan redni broj.
     * @param stavka stavka koja se dodaje
     */
    public void dodajStavku(StavkaUgovora stavka) {
        int maxRb = 0;
        for (StavkaUgovora postojeca : stavke) {
            maxRb = Math.max(maxRb, postojeca.getRbStavkaUgovora());
        }
        stavka.setRbStavkaUgovora(maxRb + 1);
        stavke.add(stavka);
        fireTableDataChanged();
    }

    /**
     * Uklanja stavku sa datog reda.
     * @param red indeks reda koji se uklanja
     */
    public void ukloniStavku(int red) {
        stavke.remove(red);
        fireTableDataChanged();
    }

    /**
     * Vraća trenutnu listu stavki.
     * @return lista stavki
     */
    public List<StavkaUgovora> getStavke() {
        return stavke;
    }

    /**
     * Zamenjuje prikazanu listu stavki i osvežava tabelu.
     * @param noveStavke nova lista stavki
     */
    public void osveziPodatke(List<StavkaUgovora> noveStavke) {
        this.stavke = noveStavke;
        fireTableDataChanged();
    }
}
