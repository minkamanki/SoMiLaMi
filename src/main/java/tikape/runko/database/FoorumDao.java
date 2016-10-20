/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Aihealue;
import tikape.runko.domain.Keskustelunavaus;
import tikape.runko.domain.Vastaus;

public class FoorumDao implements Dao<Aihealue, Integer> {

    private Database database;

    public FoorumDao(Database database) {
        this.database = database;
    }

    public void addAihealue(Aihealue aihe) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Aihealue (nimi) VALUES (?)");
        stmt.setString(1, aihe.getNimi());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public void addKeskustelunavaus(Keskustelunavaus ka) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Keskustelunavaus (String lahettaja, String otsikko, String sisalto, Integer aihe, aihealueNimi) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, ka.getLahettaja());
        stmt.setString(1, ka.getOtsikko());
        stmt.setString(1, ka.getSisalto());
        stmt.setInt(1, ka.getAihe());
        stmt.setString(1, ka.getAihealueNimi());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Override
    public Aihealue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        Integer viesteja = rs.getInt("viesteja");
        String viimViestiAika = rs.getString("aika");

        Aihealue o = new Aihealue(id, nimi, viesteja, viimViestiAika);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Aihealue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT a.nimi, COUNT(ka.id) AS viesteja, a.id, ka.aika FROM Aihealue a LEFT JOIN Keskustelunavaus ka ON a.id = ka.aihe GROUP BY a.nimi;");

// INNER JOIN Vastaus v ON ka.id = v.viesti
//SELECT *, ka.id AS kaTunnus FROM Aihealue a INNER JOIN Keskustelunavaus ka ON a.id = ka.aihe
        ResultSet rs = stmt.executeQuery();
        List<Aihealue> aiheet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            Integer viesteja = rs.getInt("viesteja");
            String viimViestiAika = rs.getString("aika");

            aiheet.add(new Aihealue(id, nimi, viesteja, viimViestiAika));

        }

        rs.close();
        stmt.close();
        connection.close();

        return aiheet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    public List<Keskustelunavaus> findKeskustelut(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT ka.otsikko, ka.lähettäjä, ka.sisältö, ka.aika, ka.aihe, COUNT(v.id) AS vastauksiaKpl, ka.id, v.aika as viimVastaus, a.nimi FROM Keskustelunavaus ka LEFT JOIN Vastaus v ON ka.id = v.viesti LEFT JOIN Aihealue a ON a.id = ka.aihe WHERE ka.aihe = " + key + " GROUP BY ka.otsikko;");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelunavaus> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String lahettaja = rs.getString("lähettäjä");
            String otsikko = rs.getString("otsikko");
            String sisalto = rs.getString("sisältö");
            String aika = rs.getString("aika");
            Integer aihealue = rs.getInt("aihe");
            Integer vastauksiaKpl = rs.getInt("vastauksiaKpl");
            String viimVastaus = rs.getString("viimVastaus");
            String aihealueNimi = rs.getString("nimi");

            keskustelut.add(new Keskustelunavaus(id, lahettaja, otsikko, sisalto, aika, aihealue, vastauksiaKpl, viimVastaus, aihealueNimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        System.out.println("OMgLOL");

        return keskustelut;
    }

    public List<Vastaus> findVastaukset(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus INNER JOIN Keskustelunavaus ON Vastaus.viesti = Keskustelunavaus.id WHERE Vastaus.viesti = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String lahettaja = rs.getString("lähettäjä");
            String sisalto = rs.getString("sisältö");
            String aika = rs.getString("aika");
            Integer viesti = rs.getInt("viesti");
            String otsikko = rs.getString(8);
            String alkLah = rs.getString(9);

            vastaukset.add(new Vastaus(id, viesti, lahettaja, sisalto, aika, otsikko, alkLah));

        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }



}
