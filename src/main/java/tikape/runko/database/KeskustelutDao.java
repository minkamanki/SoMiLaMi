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
import tikape.runko.domain.Keskustelunavaus;

/**
 *
 * @author tminka
 */
public class KeskustelutDao implements Dao<Keskustelunavaus, Integer> {

    private Database database;

    public KeskustelutDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelunavaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String lahettaja = rs.getString("lähettäjä");
        String otsikko = rs.getString("otsikko");
        String sisalto = rs.getString("sisältö");
        Timestamp aika = rs.getTimestamp("aika");
        Integer aihealue = rs.getInt("aihe");

        Keskustelunavaus o = new Keskustelunavaus(id, lahettaja, otsikko, sisalto, aika, aihealue);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    public List<Keskustelunavaus> findAllAiheesta(Integer key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus WHERE aihe = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Keskustelunavaus> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String lahettaja = rs.getString("lähettäjä");
            String otsikko = rs.getString("otsikko");
            String sisalto = rs.getString("sisältö");
            Timestamp aika = rs.getTimestamp("aika");
            Integer aihealue = rs.getInt("aihe");

            keskustelut.add(new Keskustelunavaus(id, lahettaja, otsikko, sisalto, aika, aihealue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public List<Keskustelunavaus> findAll() throws SQLException {
    
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelunavaus");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelunavaus> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String lahettaja = rs.getString("lähettäjä");
            String otsikko = rs.getString("otsikko");
            String sisalto = rs.getString("sisältö");
            Timestamp aika = rs.getTimestamp("aika");
            Integer aihealue = rs.getInt("aihe");

            keskustelut.add(new Keskustelunavaus(id, lahettaja, otsikko, sisalto, aika, aihealue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;   
    }

}


