package rs.fon.klijent.modeli;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.fon.domen.Oprema;

/**
 * Model tabele za prikaz liste opreme.
 * @author Jovan Radojičić
 */
public class ModelTabeleOprema extends AbstractTableModel {

    private final String[] kolone = {"ID", "Tip dresa", "Vrsta dresa"};
    private List<Oprema> lista;

    public ModelTabeleOprema(List<Oprema> lista) {
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
        Oprema oprema = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return oprema.getIdOpreme();
            case 1: return oprema.getTipDresa();
            case 2: return oprema.getVrsteDresa();
            default: return null;
        }
    }

    /**
     * Vraća opremu sa datog reda tabele.
     * @param red indeks reda
     * @return oprema na datom redu
     */
    public Oprema getOpremaAt(int red) {
        return lista.get(red);
    }

    /**
     * Zamenjuje prikazanu listu i osvežava tabelu.
     * @param novaLista nova lista opreme
     */
    public void osveziPodatke(List<Oprema> novaLista) {
        this.lista = novaLista;
        fireTableDataChanged();
    }
}
