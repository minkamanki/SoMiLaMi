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
        Database database = new Database("jdbc:sqlite:SoMiLaMi.db");
//        database.init();

        FoorumDao foorumDao = new FoorumDao(database);

        Scanner scanner = new Scanner(System.in);

        //Tekstikäyttöliittymä alkaa. Paina ENTER, jos haluat käynnistää web-sovelluksen.
        System.out.println("ALUEET" + '\n');

        HashMap<Aihealue, String> aihenimet = new HashMap<>();

        for (Aihealue a : foorumDao.findAll()) {
            System.out.println(a.getNimi() + '\n');
            aihenimet.put(a, a.getNimi());
        }

        System.out.println("Valitse aihealue: ");
        String syote = scanner.nextLine();

        while (!"".equals(syote)) {

            if (aihenimet.containsValue(syote)) {

                for (Aihealue a : aihenimet.keySet()) {

                    if (a.getNimi().equals(syote)) {

                        List<Keskustelunavaus> keskustelut = foorumDao.findKeskustelut(a.getId());

                        System.out.println("KESKUSTELUT" + '\n');

                        for (Keskustelunavaus k : keskustelut) {
                            System.out.println(k.getOtsikko() + '\n');
                        }

                        System.out.println("Valitse keskustelu: ");
                        syote = scanner.nextLine();

                        boolean loytyiko = false;

//                        while (loytyiko == false) {
//                            for (Keskustelunavaus k : keskustelut) {
//                                if (syote.equals(k.getOtsikko())) {
//                                    System.out.println(k.getSisalto());
//                                    loytyiko = true;
//
//                                    System.out.println("Tähän tulee vastauksia");
//                                    syote = scanner.nextLine();
//                                }
//                            }
//                            if (loytyiko = false) {
//                                System.out.println("Computer says no. Valitse keskustelu: ");
//                                syote = scanner.nextLine();
//                            }
//                        }
                    }
                }
            }
            System.out.println("Computer says no. Valitse aihealue: ");
            syote = scanner.nextLine();
        }
        //Tekstikäyttöliittymä loppuu.

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

            return new ModelAndView(map, "keskustelut");
        },
                new ThymeleafTemplateEngine()
        );

        get("/keskustelu/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("vastaukset", foorumDao.findVastaukset(Integer.parseInt(req.params("id"))));
  
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
