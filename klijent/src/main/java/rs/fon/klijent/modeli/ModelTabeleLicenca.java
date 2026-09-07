package rs.fon.klijent.modeli;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.fon.domen.Licenca;

/**
 * Model tabele za prikaz liste licenci.
 * @author Jovan Radojičić
 */
public class ModelTabeleLicenca extends AbstractTableModel {

    private final String[] kolone = {"ID", "Tip", "Nivo"};
    private List<Licenca> lista;

    public ModelTabeleLicenca(List<Licenca> lista) {
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
        Licenca licenca = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return licenca.getIdLicence();
            case 1: return licenca.getTipLicence();
            case 2: return licenca.getNivoLicence();
            default: return null;
        }
    }

    /**
     * Vraća licencu sa datog reda tabele.
     * @param red indeks reda
     * @return licenca na datom redu
     */
    public Licenca getLicencaAt(int red) {
        return lista.get(red);
    }

    /**
     * Zamenjuje prikazanu listu i osvežava tabelu.
     * @param novaLista nova lista licenci
     */
    public void osveziPodatke(List<Licenca> novaLista) {
        this.lista = novaLista;
        fireTableDataChanged();
    }
}
