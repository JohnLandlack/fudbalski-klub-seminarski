package rs.fon.baza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import rs.fon.domen.Oprema;

/**
 * Repozitorijum za rad sa tabelom {@code oprema}.
 *
 * @author Jovan Radojičić
 */
public class OpremaRepository extends Repository<Oprema> {

    @Override
    public List<Oprema> getAll() throws SQLException {
        String upit = "SELECT idOpreme, tipDresa, vrsteDresa FROM oprema";
        try (PreparedStatement ps = getConnection().prepareStatement(upit);
                ResultSet rs = ps.executeQuery()) {
            return mapListu(rs);
        }
    }

    /**
     * Vraća opremu po ID-ju.
     * @param idOpreme identifikator opreme
     * @return pronađena oprema, ili null ako ne postoji
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public Oprema getById(int idOpreme) throws SQLException {
        String upit = "SELECT idOpreme, tipDresa, vrsteDresa FROM oprema WHERE idOpreme = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, idOpreme);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRed(rs) : null;
            }
        }
    }

    /**
     * Vraća sledeći slobodan ID za novu opremu (najveći trenutni ID uvećan za 1).
     * @return sledeći slobodan ID
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public int sledeciId() throws SQLException {
        return vratiMaxId("oprema", "idOpreme") + 1;
    }

    @Override
    public void add(Oprema oprema) throws SQLException {
        String upit = "INSERT INTO oprema (idOpreme, tipDresa, vrsteDresa) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, oprema.getIdOpreme());
            ps.setString(2, oprema.getTipDresa());
            ps.setString(3, oprema.getVrsteDresa());
            ps.executeUpdate();
        }
    }

    @Override
    public void edit(Oprema oprema) throws SQLException {
        String upit = "UPDATE oprema SET tipDresa = ?, vrsteDresa = ? WHERE idOpreme = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setString(1, oprema.getTipDresa());
            ps.setString(2, oprema.getVrsteDresa());
            ps.setInt(3, oprema.getIdOpreme());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Oprema oprema) throws SQLException {
        String upit = "DELETE FROM oprema WHERE idOpreme = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, oprema.getIdOpreme());
            ps.executeUpdate();
        }
    }

    @Override
    protected Oprema mapRed(ResultSet rs) throws SQLException {
        return new Oprema(rs.getInt("idOpreme"), rs.getString("tipDresa"), rs.getString("vrsteDresa"));
    }
}
