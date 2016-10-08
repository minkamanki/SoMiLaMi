package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        System.out.println("ALUEET" + '\n');

        HashMap<Aihealue, String> aihenimet = new HashMap<>();

//        List<Aihealue> aiheet = foorumDao.findAll();
//        ArrayList<String> aiheNimet = new ArrayList<>();
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

                        while (loytyiko == false) {
                            for (Keskustelunavaus k : keskustelut) {
                                if (syote.equals(k.getOtsikko())) {
                                    System.out.println(k.getSisalto());
                                    loytyiko = true;
                                    
                                    System.out.println("Tähän tulee vastauksia");
                                    syote = scanner.nextLine();
                                }
                            }
                            if (loytyiko = false) {
                                System.out.println("Computer says no. Valitse keskustelu: ");
                                syote = scanner.nextLine();
                            }
                        }
                    }
                }
            }
            System.out.println("Computer says no. Valitse aihealue: ");
            syote = scanner.nextLine();
        }

        get(
                "/", (req, res) -> {
                    HashMap map = new HashMap<>();
                    map.put("aiheet", foorumDao.findAll());

                    return new ModelAndView(map, "index");
                },
                new ThymeleafTemplateEngine()
        );

//        get("/aiheet", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("aiheet", foorumDao.findAll());
//
//            return new ModelAndView(map, "aiheet");
//        }, new ThymeleafTemplateEngine());
        get(
                "/aihe/:id", (req, res) -> {
                    HashMap map = new HashMap<>();
                    map.put("keskustelut", foorumDao.findKeskustelut(Integer.parseInt(req.params("id"))));

                    return new ModelAndView(map, "keskustelut");
                },
                new ThymeleafTemplateEngine()
        );
//        
//        get("/keskustelu/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("vastaukset", foorumDao.findVastaukset(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "keskustelu");
//        }, new ThymeleafTemplateEngine());
    }
}
