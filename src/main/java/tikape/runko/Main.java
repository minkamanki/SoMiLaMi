package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.FoorumDao;
import tikape.runko.domain.Aihealue;
import tikape.runko.domain.Keskustelunavaus;

public class Main {

    /*mainin suorittamalla pääsee selaimella "osoitteessa" http://localhost:4567/ olevaan SoMiLaMi foorumiin*/
    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:SoMiLaMi.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        } 
        
        Database database = new Database(jdbcOsoite);
//        database.init();

        FoorumDao foorumDao = new FoorumDao(database);

        //Tekstikäyttöliittymä loppuu.
        Kayttoliittyma kl = new Kayttoliittyma(foorumDao);
        kl.tekstikayttoliityma();
        
        
        
        post("/", (req, res) -> {
            String otsikko = req.queryParams("nimi").trim();

            if (!otsikko.isEmpty()) {
                foorumDao.addAihealue(otsikko);
            }

            res.redirect("/");
            return "";
        });

        post("/aihe/:id", (req, res) -> {
            String lahettaja = req.queryParams("nimimerkki").trim();
            String otsikko = req.queryParams("nimi").trim();
            String sisalto = req.queryParams("sisalto").trim();

            int aId = Integer.parseInt(req.params("id"));

            if (!lahettaja.isEmpty() && !otsikko.isEmpty() && !sisalto.isEmpty()) {
                foorumDao.addKeskustelunavaus(lahettaja, otsikko, sisalto, aId);
            }

            res.redirect("/aihe/" + aId);
            return "";
        });
//jdfh
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aiheet", foorumDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

//        get("/aiheet", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("aiheet", foorumDao.findAll());
//
//            return new ModelAndView(map, "aiheet");
//        }, new ThymeleafTemplateEngine());
        get("/aihe/:id", (req, res) -> {
            HashMap map = new HashMap<>();

//                    if (foorumDao.findKeskustelut(Integer.parseInt(req.params("id"))).isEmpty()) {
//
//                    } else {
            map.put("keskustelut", foorumDao.findKeskustelut(Integer.parseInt(req.params("id"))));
            map.put("aihe", foorumDao.findAiheNimi(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelut");
        },
                new ThymeleafTemplateEngine()
        );

        get("/keskustelu/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("vastaukset", foorumDao.findVastaukset(Integer.parseInt(req.params("id"))));
            Keskustelunavaus a = foorumDao.findKeskustelu(Integer.parseInt(req.params("id")));
            map.put("sisalto", a.getSisalto());
            map.put("otsikko", a.getOtsikko());
            map.put("aiheid", a.getAihe());
            map.put("lahettaja", a.getLahettaja());
  
            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());

        post("/keskustelu/:id", (req, res) -> {
            String lahettaja = req.queryParams("nimimerkki").trim();
            String sisalto = req.queryParams("sisalto").trim();

            int aId = Integer.parseInt(req.params("id"));

            if (!lahettaja.isEmpty() && !sisalto.isEmpty()) {
                foorumDao.addVastaus(lahettaja, sisalto, aId);
            }

            res.redirect("/keskustelu/" + aId);
            return "";
        });

    }
}
