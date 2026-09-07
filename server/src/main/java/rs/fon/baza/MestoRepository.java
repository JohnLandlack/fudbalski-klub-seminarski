package rs.fon.baza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import rs.fon.domen.Mesto;

/**
 * Repozitorijum za rad sa tabelom {@code mesto}.
 *
 * @author Jovan Radojičić
 */
public class MestoRepository extends Repository<Mesto> {

    @Override
    public List<Mesto> getAll() throws SQLException {
        String upit = "SELECT idMesta, naziv, postanskiBroj FROM mesto";
        try (PreparedStatement ps = getConnection().prepareStatement(upit);
                ResultSet rs = ps.executeQuery()) {
            return mapListu(rs);
        }
    }

    /**
     * Vraća mesto po ID-ju.
     * @param idMesta identifikator mesta
     * @return pronađeno mesto, ili null ako ne postoji
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public Mesto getById(int idMesta) throws SQLException {
        String upit = "SELECT idMesta, naziv, postanskiBroj FROM mesto WHERE idMesta = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, idMesta);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRed(rs) : null;
            }
        }
    }

    /**
     * Vraća sledeći slobodan ID za novo mesto (najveći trenutni ID uvećan za 1).
     * @return sledeći slobodan ID
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public int sledeciId() throws SQLException {
        return vratiMaxId("mesto", "idMesta") + 1;
    }

    @Override
    public void add(Mesto mesto) throws SQLException {
        String upit = "INSERT INTO mesto (idMesta, naziv, postanskiBroj) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, mesto.getIdMesta());
            ps.setString(2, mesto.getNaziv());
            ps.setString(3, mesto.getPostanskiBroj());
            ps.executeUpdate();
        }
    }

    @Override
    public void edit(Mesto mesto) throws SQLException {
        String upit = "UPDATE mesto SET naziv = ?, postanskiBroj = ? WHERE idMesta = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setString(1, mesto.getNaziv());
            ps.setString(2, mesto.getPostanskiBroj());
            ps.setInt(3, mesto.getIdMesta());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Mesto mesto) throws SQLException {
        String upit = "DELETE FROM mesto WHERE idMesta = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, mesto.getIdMesta());
            ps.executeUpdate();
        }
    }

    @Override
    protected Mesto mapRed(ResultSet rs) throws SQLException {
        return new Mesto(rs.getInt("idMesta"), rs.getString("naziv"), rs.getString("postanskiBroj"));
    }
}
