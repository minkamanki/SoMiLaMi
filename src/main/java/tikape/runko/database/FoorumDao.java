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

        Aihealue o = new Aihealue(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Aihealue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue");

        ResultSet rs = stmt.executeQuery();
        List<Aihealue> aiheet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            aiheet.add(new Aihealue(id, nimi));
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
    
    public  List<Keskustelunavaus> findKeskustelut(Integer key)throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus WHERE aihe = " + key + ";");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelunavaus> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String lahettaja = rs.getString("lähettäjä");
            String otsikko = rs.getString("otsikko");
            String sisalto = rs.getString("sisältö");
            String aika = rs.getString("aika");
            Integer aihealue = rs.getInt("aihe");

            keskustelut.add(new Keskustelunavaus(id, lahettaja, otsikko, sisalto, aika, aihealue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }
    
        public  List<Vastaus> findVastaukset(Integer key)throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE viesti = " + key + ";");

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String lahettaja = rs.getString("lähettäjä");
            String sisalto = rs.getString("sisältö");
            String aika = rs.getString("aika");
            Integer viesti = rs.getInt("viesti");

            vastaukset.add(new Vastaus(id,viesti, lahettaja, sisalto, aika));

        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }

}
