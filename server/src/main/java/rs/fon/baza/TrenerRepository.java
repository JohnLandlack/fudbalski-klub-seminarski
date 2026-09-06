package rs.fon.baza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import rs.fon.domen.Trener;

/**
 * Repozitorijum za rad sa tabelom {@code trener}.
 * * @author Jovan Radojičić
 */
public class TrenerRepository extends Repository<Trener> {

    @Override
    public List<Trener> getAll() throws SQLException {
        String upit = "SELECT idTrener, ime, prezime, korisnickoIme, sifra FROM trener";
        try (PreparedStatement ps = getConnection().prepareStatement(upit);
                ResultSet rs = ps.executeQuery()) {
            return mapListu(rs);
        }
    }

    /**
     * Vraća trenera po ID-ju.
     * @param idTrener identifikator trenera
     * @return pronađeni trener, ili null ako ne postoji
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public Trener getById(int idTrener) throws SQLException {
        String upit = "SELECT idTrener, ime, prezime, korisnickoIme, sifra FROM trener WHERE idTrener = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, idTrener);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRed(rs) : null;
            }
        }
    }

    @Override
    public void add(Trener trener) throws SQLException {
        String upit = "INSERT INTO trener (idTrener, ime, prezime, korisnickoIme, sifra) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, trener.getIdTrener());
            ps.setString(2, trener.getIme());
            ps.setString(3, trener.getPrezime());
            ps.setString(4, trener.getKorisnickoIme());
            ps.setString(5, trener.getSifra());
            ps.executeUpdate();
        }
    }

    @Override
    public void edit(Trener trener) throws SQLException {
        String upit = "UPDATE trener SET ime = ?, prezime = ?, korisnickoIme = ?, sifra = ? WHERE idTrener = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setString(1, trener.getIme());
            ps.setString(2, trener.getPrezime());
            ps.setString(3, trener.getKorisnickoIme());
            ps.setString(4, trener.getSifra());
            ps.setInt(5, trener.getIdTrener());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Trener trener) throws SQLException {
        String upit = "DELETE FROM trener WHERE idTrener = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, trener.getIdTrener());
            ps.executeUpdate();
        }
    }

    @Override
    protected Trener mapRed(ResultSet rs) throws SQLException {
        return new Trener(rs.getInt("idTrener"), rs.getString("ime"), rs.getString("prezime"),
                rs.getString("korisnickoIme"), rs.getString("sifra"));
    }
}
