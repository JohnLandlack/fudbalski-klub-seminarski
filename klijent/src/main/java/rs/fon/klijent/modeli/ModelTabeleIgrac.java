package rs.fon.klijent.modeli;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.fon.domen.Igrac;

/**
 * Model tabele za prikaz liste igrača.
 * @author Jovan Radojičić
 */
public class ModelTabeleIgrac extends AbstractTableModel {

    private final String[] kolone = {"ID", "Ime", "Prezime", "Telefon", "Pozicija", "Mesto"};
    private List<Igrac> lista;

    public ModelTabeleIgrac(List<Igrac> lista) {
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
        Igrac igrac = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return igrac.getIdIgrac();
            case 1: return igrac.getIme();
            case 2: return igrac.getPrezime();
            case 3: return igrac.getTelefon();
            case 4: return igrac.getPozicija();
            case 5: return igrac.getMesto().getNaziv();
            default: return null;
        }
    }

    /**
     * Vraća igrača sa datog reda tabele.
     * @param red indeks reda
     * @return igrač na datom redu
     */
    public Igrac getIgracAt(int red) {
        return lista.get(red);
    }

    /**
     * Zamenjuje prikazanu listu i osvežava tabelu.
     * @param novaLista nova lista igrača
     */
    public void osveziPodatke(List<Igrac> novaLista) {
        this.lista = novaLista;
        fireTableDataChanged();
    }

    /**
     * Filtrira originalnu listu po imenu ili prezimenu (bez razlike malih
     * i velikih slova) i osvežava tabelu.
     * @param originalnaLista puna lista igrača nad kojom se filtrira
     * @param tekst tekst pretrage; prazan tekst vraća punu listu
     */
    public void osveziSaFilterom(List<Igrac> originalnaLista, String tekst) {
        if (tekst == null || tekst.isBlank()) {
            osveziPodatke(originalnaLista);
            return;
        }
        String pretraga = tekst.trim().toLowerCase();
        List<Igrac> filtrirani = new ArrayList<>();
        for (Igrac igrac : originalnaLista) {
            if (igrac.getIme().toLowerCase().contains(pretraga) || igrac.getPrezime().toLowerCase().contains(pretraga)) {
                filtrirani.add(igrac);
            }
        }
        osveziPodatke(filtrirani);
    }
}
