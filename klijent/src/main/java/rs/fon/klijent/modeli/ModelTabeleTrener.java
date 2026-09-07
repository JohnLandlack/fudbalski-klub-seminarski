package rs.fon.klijent.modeli;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.fon.domen.Trener;

/**
 * Model tabele za prikaz liste trenera.
 * @author Jovan Radojičić
 */
public class ModelTabeleTrener extends AbstractTableModel {

    private final String[] kolone = {"ID", "Ime", "Prezime", "Korisničko ime"};
    private List<Trener> lista;

    public ModelTabeleTrener(List<Trener> lista) {
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
        Trener trener = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return trener.getIdTrener();
            case 1: return trener.getIme();
            case 2: return trener.getPrezime();
            case 3: return trener.getKorisnickoIme();
            default: return null;
        }
    }

    /**
     * Vraća trenera sa datog reda tabele.
     * @param red indeks reda
     * @return trener na datom redu
     */
    public Trener getTrenerAt(int red) {
        return lista.get(red);
    }

    /**
     * Zamenjuje prikazanu listu i osvežava tabelu.
     * @param novaLista nova lista trenera
     */
    public void osveziPodatke(List<Trener> novaLista) {
        this.lista = novaLista;
        fireTableDataChanged();
    }
}
