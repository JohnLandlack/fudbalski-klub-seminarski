package rs.fon.baza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import rs.fon.domen.Licenca;

/**
 * Repozitorijum za rad sa tabelom {@code licenca}.
 * * @author Jovan Radojičić
 */
public class LicencaRepository extends Repository<Licenca> {

    @Override
    public List<Licenca> getAll() throws SQLException {
        String upit = "SELECT idLicence, tipLicence, nivoLicence FROM licenca";
        try (PreparedStatement ps = getConnection().prepareStatement(upit);
                ResultSet rs = ps.executeQuery()) {
            return mapListu(rs);
        }
    }

    /**
     * Vraća licencu po ID-ju.
     * @param idLicence identifikator licence
     * @return pronađena licenca, ili null ako ne postoji
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public Licenca getById(int idLicence) throws SQLException {
        String upit = "SELECT idLicence, tipLicence, nivoLicence FROM licenca WHERE idLicence = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, idLicence);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRed(rs) : null;
            }
        }
    }

    @Override
    public void add(Licenca licenca) throws SQLException {
        String upit = "INSERT INTO licenca (idLicence, tipLicence, nivoLicence) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, licenca.getIdLicence());
            ps.setString(2, licenca.getTipLicence());
            ps.setString(3, licenca.getNivoLicence());
            ps.executeUpdate();
        }
    }

    @Override
    public void edit(Licenca licenca) throws SQLException {
        String upit = "UPDATE licenca SET tipLicence = ?, nivoLicence = ? WHERE idLicence = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setString(1, licenca.getTipLicence());
            ps.setString(2, licenca.getNivoLicence());
            ps.setInt(3, licenca.getIdLicence());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Licenca licenca) throws SQLException {
        String upit = "DELETE FROM licenca WHERE idLicence = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, licenca.getIdLicence());
            ps.executeUpdate();
        }
    }

    @Override
    protected Licenca mapRed(ResultSet rs) throws SQLException {
        return new Licenca(rs.getInt("idLicence"), rs.getString("tipLicence"), rs.getString("nivoLicence"));
    }
}
