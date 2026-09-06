package rs.fon.baza;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import rs.fon.domen.Oprema;
import rs.fon.domen.StavkaUgovora;
import rs.fon.domen.Ugovor;

/**
 * Repozitorijum za rad sa tabelom {@code stavkaugovora} (kompozitni ključ:
 * ugovor + redni broj). Pridružuje tabelu {@code oprema} da bi svaka stavka
 * imala potpuno učitan objekat opreme. Ugovor kome stavka pripada se ovde
 * učitava samo sa postavljenim ID-jem (bez trenera/igrača) — za pun objekat
 * ugovora koristiti {@link UgovorRepository#getById(int)}.
 * * @author Jovan Radojičić
 */
public class StavkaUgovoraRepository extends Repository<StavkaUgovora> {

    private static final String UPIT_SA_OPREMOM =
            "SELECT su.idUgovor, su.rbStavkaUgovora, su.vazenjeUgovora, su.plata, "
            + "o.idOpreme, o.tipDresa, o.vrsteDresa "
            + "FROM stavkaugovora su "
            + "JOIN oprema o ON su.idOpreme = o.idOpreme";

    @Override
    public List<StavkaUgovora> getAll() throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(UPIT_SA_OPREMOM);
                ResultSet rs = ps.executeQuery()) {
            return mapListu(rs);
        }
    }

    /**
     * Vraća sve stavke jednog ugovora.
     * @param idUgovor identifikator ugovora
     * @return lista stavki tog ugovora
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public List<StavkaUgovora> getByUgovor(int idUgovor) throws SQLException {
        String upit = UPIT_SA_OPREMOM + " WHERE su.idUgovor = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, idUgovor);
            try (ResultSet rs = ps.executeQuery()) {
                return mapListu(rs);
            }
        }
    }

    @Override
    public void add(StavkaUgovora stavka) throws SQLException {
        String upit = "INSERT INTO stavkaugovora (idUgovor, rbStavkaUgovora, vazenjeUgovora, plata, idOpreme) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, stavka.getUgovor().getIdUgovor());
            ps.setInt(2, stavka.getRbStavkaUgovora());
            ps.setDate(3, new Date(stavka.getVazenjeUgovora().getTime()));
            ps.setInt(4, stavka.getPlata());
            ps.setInt(5, stavka.getOprema().getIdOpreme());
            ps.executeUpdate();
        }
    }

    @Override
    public void edit(StavkaUgovora stavka) throws SQLException {
        String upit = "UPDATE stavkaugovora SET vazenjeUgovora = ?, plata = ?, idOpreme = ? WHERE idUgovor = ? AND rbStavkaUgovora = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setDate(1, new Date(stavka.getVazenjeUgovora().getTime()));
            ps.setInt(2, stavka.getPlata());
            ps.setInt(3, stavka.getOprema().getIdOpreme());
            ps.setInt(4, stavka.getUgovor().getIdUgovor());
            ps.setInt(5, stavka.getRbStavkaUgovora());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(StavkaUgovora stavka) throws SQLException {
        String upit = "DELETE FROM stavkaugovora WHERE idUgovor = ? AND rbStavkaUgovora = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, stavka.getUgovor().getIdUgovor());
            ps.setInt(2, stavka.getRbStavkaUgovora());
            ps.executeUpdate();
        }
    }

    @Override
    protected StavkaUgovora mapRed(ResultSet rs) throws SQLException {
        Ugovor ugovorStub = new Ugovor();
        ugovorStub.setIdUgovor(rs.getInt("idUgovor"));
        Oprema oprema = new Oprema(rs.getInt("idOpreme"), rs.getString("tipDresa"), rs.getString("vrsteDresa"));
        return new StavkaUgovora(ugovorStub, rs.getInt("rbStavkaUgovora"), rs.getDate("vazenjeUgovora"),
                rs.getInt("plata"), oprema);
    }
}
