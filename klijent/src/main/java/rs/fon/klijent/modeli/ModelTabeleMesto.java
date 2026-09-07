package rs.fon.klijent.modeli;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.fon.domen.Mesto;

/**
 * Model tabele za prikaz liste mesta.
 * @author Jovan Radojičić
 */
public class ModelTabeleMesto extends AbstractTableModel {

    private final String[] kolone = {"ID", "Naziv", "Poštanski broj"};
    private List<Mesto> lista;

    public ModelTabeleMesto(List<Mesto> lista) {
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
        Mesto mesto = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return mesto.getIdMesta();
            case 1: return mesto.getNaziv();
            case 2: return mesto.getPostanskiBroj();
            default: return null;
        }
    }

    /**
     * Vraća mesto sa datog reda tabele.
     * @param red indeks reda
     * @return mesto na datom redu
     */
    public Mesto getMestoAt(int red) {
        return lista.get(red);
    }

    /**
     * Zamenjuje prikazanu listu i osvežava tabelu.
     * @param novaLista nova lista mesta
     */
    public void osveziPodatke(List<Mesto> novaLista) {
        this.lista = novaLista;
        fireTableDataChanged();
    }
}
