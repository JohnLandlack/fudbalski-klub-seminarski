package rs.fon.baza;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import rs.fon.domen.Licenca;
import rs.fon.domen.Trener;
import rs.fon.domen.TrenerLicenca;

/**
 * Repozitorijum za rad sa tabelom {@code trener_licenca} (kompozitni ključ:
 * trener + licenca). Pridružuje tabele {@code trener} i {@code licenca} da bi
 * svaki zapis imao potpuno učitane objekte.
 *
 * @author Jovan Radojičić
 */
public class TrenerLicencaRepository extends Repository<TrenerLicenca> {

    private static final String UPIT_SA_JOINOVIMA =
            "SELECT tl.idTrener, tl.idLicence, tl.datumIzdavanja, tl.datumIsteka, "
            + "t.ime AS t_ime, t.prezime AS t_prezime, t.korisnickoIme AS t_korisnickoIme, t.sifra AS t_sifra, "
            + "l.tipLicence, l.nivoLicence "
            + "FROM trener_licenca tl "
            + "JOIN trener t ON tl.idTrener = t.idTrener "
            + "JOIN licenca l ON tl.idLicence = l.idLicence";

    @Override
    public List<TrenerLicenca> getAll() throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(UPIT_SA_JOINOVIMA);
                ResultSet rs = ps.executeQuery()) {
            return mapListu(rs);
        }
    }

    /**
     * Vraća vezu trenera i licence po kompozitnom ključu.
     * @param idTrener identifikator trenera
     * @param idLicence identifikator licence
     * @return pronađena veza, ili null ako ne postoji
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public TrenerLicenca getById(int idTrener, int idLicence) throws SQLException {
        String upit = UPIT_SA_JOINOVIMA + " WHERE tl.idTrener = ? AND tl.idLicence = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, idTrener);
            ps.setInt(2, idLicence);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRed(rs) : null;
            }
        }
    }

    @Override
    public void add(TrenerLicenca trenerLicenca) throws SQLException {
        String upit = "INSERT INTO trener_licenca (idTrener, idLicence, datumIzdavanja, datumIsteka) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, trenerLicenca.getTrener().getIdTrener());
            ps.setInt(2, trenerLicenca.getLicenca().getIdLicence());
            ps.setDate(3, new Date(trenerLicenca.getDatumIzdavanja().getTime()));
            ps.setDate(4, new Date(trenerLicenca.getDatumIsteka().getTime()));
            ps.executeUpdate();
        }
    }

    @Override
    public void edit(TrenerLicenca trenerLicenca) throws SQLException {
        String upit = "UPDATE trener_licenca SET datumIzdavanja = ?, datumIsteka = ? WHERE idTrener = ? AND idLicence = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setDate(1, new Date(trenerLicenca.getDatumIzdavanja().getTime()));
            ps.setDate(2, new Date(trenerLicenca.getDatumIsteka().getTime()));
            ps.setInt(3, trenerLicenca.getTrener().getIdTrener());
            ps.setInt(4, trenerLicenca.getLicenca().getIdLicence());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(TrenerLicenca trenerLicenca) throws SQLException {
        String upit = "DELETE FROM trener_licenca WHERE idTrener = ? AND idLicence = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, trenerLicenca.getTrener().getIdTrener());
            ps.setInt(2, trenerLicenca.getLicenca().getIdLicence());
            ps.executeUpdate();
        }
    }

    @Override
    protected TrenerLicenca mapRed(ResultSet rs) throws SQLException {
        Trener trener = new Trener(rs.getInt("idTrener"), rs.getString("t_ime"), rs.getString("t_prezime"),
                rs.getString("t_korisnickoIme"), rs.getString("t_sifra"));
        Licenca licenca = new Licenca(rs.getInt("idLicence"), rs.getString("tipLicence"), rs.getString("nivoLicence"));
        return new TrenerLicenca(trener, licenca, rs.getDate("datumIzdavanja"), rs.getDate("datumIsteka"));
    }
}
