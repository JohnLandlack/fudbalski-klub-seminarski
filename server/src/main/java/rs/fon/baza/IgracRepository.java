package rs.fon.baza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;

/**
 * Repozitorijum za rad sa tabelom {@code igrac}. Pridružuje tabelu
 * {@code mesto} da bi svaki igrač imao potpuno učitan objekat mesta.
 * * @author Jovan Radojičić
 */
public class IgracRepository extends Repository<Igrac> {

    private static final String UPIT_SA_MESTOM =
            "SELECT i.idIgrac, i.ime, i.prezime, i.telefon, i.pozicija, "
            + "m.idMesta, m.naziv, m.postanskiBroj "
            + "FROM igrac i JOIN mesto m ON i.idMesta = m.idMesta";

    @Override
    public List<Igrac> getAll() throws SQLException {
        String upit = UPIT_SA_MESTOM;
        try (PreparedStatement ps = getConnection().prepareStatement(upit);
                ResultSet rs = ps.executeQuery()) {
            return mapListu(rs);
        }
    }

    /**
     * Vraća igrača po ID-ju.
     * @param idIgrac identifikator igrača
     * @return pronađeni igrač, ili null ako ne postoji
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public Igrac getById(int idIgrac) throws SQLException {
        String upit = UPIT_SA_MESTOM + " WHERE i.idIgrac = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, idIgrac);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRed(rs) : null;
            }
        }
    }

    @Override
    public void add(Igrac igrac) throws SQLException {
        String upit = "INSERT INTO igrac (idIgrac, ime, prezime, telefon, pozicija, idMesta) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, igrac.getIdIgrac());
            ps.setString(2, igrac.getIme());
            ps.setString(3, igrac.getPrezime());
            ps.setString(4, igrac.getTelefon());
            ps.setString(5, igrac.getPozicija());
            ps.setInt(6, igrac.getMesto().getIdMesta());
            ps.executeUpdate();
        }
    }

    @Override
    public void edit(Igrac igrac) throws SQLException {
        String upit = "UPDATE igrac SET ime = ?, prezime = ?, telefon = ?, pozicija = ?, idMesta = ? WHERE idIgrac = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setString(1, igrac.getIme());
            ps.setString(2, igrac.getPrezime());
            ps.setString(3, igrac.getTelefon());
            ps.setString(4, igrac.getPozicija());
            ps.setInt(5, igrac.getMesto().getIdMesta());
            ps.setInt(6, igrac.getIdIgrac());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Igrac igrac) throws SQLException {
        String upit = "DELETE FROM igrac WHERE idIgrac = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, igrac.getIdIgrac());
            ps.executeUpdate();
        }
    }

    @Override
    protected Igrac mapRed(ResultSet rs) throws SQLException {
        Mesto mesto = new Mesto(rs.getInt("idMesta"), rs.getString("naziv"), rs.getString("postanskiBroj"));
        return new Igrac(rs.getInt("idIgrac"), rs.getString("ime"), rs.getString("prezime"),
                rs.getString("telefon"), rs.getString("pozicija"), mesto);
    }
}
