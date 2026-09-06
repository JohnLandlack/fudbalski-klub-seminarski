package rs.fon.baza;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import rs.fon.domen.Igrac;
import rs.fon.domen.Mesto;
import rs.fon.domen.Trener;
import rs.fon.domen.Ugovor;

/**
 * Repozitorijum za rad sa tabelom {@code ugovor}. Pridružuje tabele
 * {@code trener}, {@code igrac} i {@code mesto} da bi svaki ugovor imao
 * potpuno učitane objekate trenera i igrača. Stavke ugovora se ne učitavaju
 * ovde (prazna lista) — za njih se koristi {@link StavkaUgovoraRepository}.
 * * @author Jovan Radojičić
 */
public class UgovorRepository extends Repository<Ugovor> {

    private static final String UPIT_SA_JOINOVIMA =
            "SELECT u.idUgovor, u.datumPotpisivanja, "
            + "t.idTrener, t.ime AS t_ime, t.prezime AS t_prezime, "
            + "t.korisnickoIme AS t_korisnickoIme, t.sifra AS t_sifra, "
            + "i.idIgrac, i.ime AS i_ime, i.prezime AS i_prezime, i.telefon AS i_telefon, i.pozicija AS i_pozicija, "
            + "m.idMesta, m.naziv, m.postanskiBroj "
            + "FROM ugovor u "
            + "JOIN trener t ON u.idTrener = t.idTrener "
            + "JOIN igrac i ON u.idIgrac = i.idIgrac "
            + "JOIN mesto m ON i.idMesta = m.idMesta";

    @Override
    public List<Ugovor> getAll() throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(UPIT_SA_JOINOVIMA);
                ResultSet rs = ps.executeQuery()) {
            return mapListu(rs);
        }
    }

    /**
     * Vraća ugovor po ID-ju.
     * @param idUgovor identifikator ugovora
     * @return pronađeni ugovor, ili null ako ne postoji
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public Ugovor getById(int idUgovor) throws SQLException {
        String upit = UPIT_SA_JOINOVIMA + " WHERE u.idUgovor = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, idUgovor);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRed(rs) : null;
            }
        }
    }

    @Override
    public void add(Ugovor ugovor) throws SQLException {
        String upit = "INSERT INTO ugovor (idUgovor, datumPotpisivanja, idTrener, idIgrac) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, ugovor.getIdUgovor());
            ps.setDate(2, new Date(ugovor.getDatumPotpisivanja().getTime()));
            ps.setInt(3, ugovor.getTrener().getIdTrener());
            ps.setInt(4, ugovor.getIgrac().getIdIgrac());
            ps.executeUpdate();
        }
    }

    @Override
    public void edit(Ugovor ugovor) throws SQLException {
        String upit = "UPDATE ugovor SET datumPotpisivanja = ?, idTrener = ?, idIgrac = ? WHERE idUgovor = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setDate(1, new Date(ugovor.getDatumPotpisivanja().getTime()));
            ps.setInt(2, ugovor.getTrener().getIdTrener());
            ps.setInt(3, ugovor.getIgrac().getIdIgrac());
            ps.setInt(4, ugovor.getIdUgovor());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Ugovor ugovor) throws SQLException {
        String upit = "DELETE FROM ugovor WHERE idUgovor = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(upit)) {
            ps.setInt(1, ugovor.getIdUgovor());
            ps.executeUpdate();
        }
    }

    @Override
    protected Ugovor mapRed(ResultSet rs) throws SQLException {
        Trener trener = new Trener(rs.getInt("idTrener"), rs.getString("t_ime"), rs.getString("t_prezime"),
                rs.getString("t_korisnickoIme"), rs.getString("t_sifra"));
        Mesto mesto = new Mesto(rs.getInt("idMesta"), rs.getString("naziv"), rs.getString("postanskiBroj"));
        Igrac igrac = new Igrac(rs.getInt("idIgrac"), rs.getString("i_ime"), rs.getString("i_prezime"),
                rs.getString("i_telefon"), rs.getString("i_pozicija"), mesto);
        return new Ugovor(rs.getInt("idUgovor"), rs.getDate("datumPotpisivanja"), trener, igrac);
    }
}
