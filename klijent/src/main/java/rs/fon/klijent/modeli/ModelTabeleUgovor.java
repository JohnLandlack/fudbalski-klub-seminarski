package rs.fon.klijent.modeli;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.fon.domen.Ugovor;

/**
 * Model tabele za prikaz liste ugovora.
 * @author Jovan Radojičić
 */
public class ModelTabeleUgovor extends AbstractTableModel {

    private static final SimpleDateFormat FORMAT_DATUMA = new SimpleDateFormat("dd.MM.yyyy");

    private final String[] kolone = {"ID", "Datum potpisivanja", "Trener", "Igrač"};
    private List<Ugovor> lista;

    public ModelTabeleUgovor(List<Ugovor> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
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
        Ugovor ugovor = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return ugovor.getIdUgovor();
            case 1: return FORMAT_DATUMA.format(ugovor.getDatumPotpisivanja());
            case 2: return ugovor.getTrener().toString();
            case 3: return ugovor.getIgrac().toString();
            default: return null;
        }
    }

    /**
     * Vraća ugovor sa datog reda tabele.
     * @param red indeks reda
     * @return ugovor na datom redu
     */
    public Ugovor getUgovorAt(int red) {
        return lista.get(red);
    }

    /**
     * Zamenjuje prikazanu listu i osvežava tabelu.
     * @param novaLista nova lista ugovora
     */
    public void osveziPodatke(List<Ugovor> novaLista) {
        this.lista = novaLista;
        fireTableDataChanged();
    }
}
