package rs.fon.baza;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generička baza za repozitorijume nad bazom podataka. Konkretni
 * repozitorijumi implementiraju CRUD operacije preko {@link java.sql.PreparedStatement}
 * (parametrizovani upiti — bez konkatenacije korisničkih vrednosti u SQL) i
 * mapiranje reda rezultata u domenski objekat.
 *
 * @param <T> tip domenskog objekta kojim repozitorijum upravlja
 * @author Jovan Radojičić
 */
public abstract class Repository<T> {

    /**
     * Vraća trenutnu konekciju ka bazi.
     * @return konekcija ka bazi podataka
     * @throws SQLException ako konekcija ne može da se dobavi
     */
    protected Connection getConnection() throws SQLException {
        return Konekcija.getConnection();
    }

    /**
     * Vraća sve objekte iz odgovarajuće tabele.
     * @return lista svih objekata
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public abstract List<T> getAll() throws SQLException;

    /**
     * Dodaje novi objekat u bazu.
     * @param entitet objekat koji se dodaje
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public abstract void add(T entitet) throws SQLException;

    /**
     * Izmenjuje postojeći objekat u bazi.
     * @param entitet objekat sa izmenjenim vrednostima
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public abstract void edit(T entitet) throws SQLException;

    /**
     * Briše objekat iz baze.
     * @param entitet objekat koji se briše
     * @throws SQLException ako upit ka bazi ne uspe
     */
    public abstract void delete(T entitet) throws SQLException;

    /**
     * Mapira jedan red iz {@link ResultSet}-a u domenski objekat. Poziva se
     * dok je kursor rezultata već pozicioniran na validan red.
     * @param rs rezultat upita, pozicioniran na red koji se mapira
     * @return domenski objekat mapiran iz reda
     * @throws SQLException ako čitanje kolona ne uspe
     */
    protected abstract T mapRed(ResultSet rs) throws SQLException;

    /**
     * Mapira sve redove iz {@link ResultSet}-a u listu domenskih objekata.
     * @param rs rezultat upita
     * @return lista mapiranih objekata
     * @throws SQLException ako čitanje kolona ne uspe
     */
    protected List<T> mapListu(ResultSet rs) throws SQLException {
        List<T> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(mapRed(rs));
        }
        return lista;
    }

    /**
     * Vraća najveću vrednost primarnog ključa u tabeli (koristi se za ručno
     * generisanje ID-ja jer kolone nisu auto-increment). Naziv tabele i
     * kolone su uvek literali definisani u kodu repozitorijuma, nikad
     * korisnički unos, pa konkatenacija ovde ne predstavlja SQL injection rizik.
     * @param tabela naziv tabele
     * @param kolonaId naziv kolone primarnog ključa
     * @return najveći trenutni ID, ili 0 ako je tabela prazna
     * @throws SQLException ako upit ka bazi ne uspe
     */
    protected int vratiMaxId(String tabela, String kolonaId) throws SQLException {
        String upit = "SELECT MAX(" + kolonaId + ") FROM " + tabela;
        try (var st = getConnection().createStatement();
                var rs = st.executeQuery(upit)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
