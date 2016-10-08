package tikape.runko;

import java.util.HashMap;
import java.util.Scanner;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.FoorumDao;

public class Main {

    /*mainin suorittamalla pääsee selaimella "osoitteessa" http://localhost:4567/ olevaan SoMiLaMi foorumiin*/
    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:SoMiLaMi.db");
//        database.init();

        FoorumDao foorumDao = new FoorumDao(database);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Anna syöte: ");
        String syote = scanner.nextLine();

        while (!"".equals(syote)) {
            System.out.println("sjkasd");
            /*
            
            tekstikäyttöliitymä tänne
        
        
             */
            System.out.println("Anna syöte: ");
            syote = scanner.nextLine();
        }

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
            map.put("keskustelut", foorumDao.findKeskustelut(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());
//        
//        get("/keskustelu/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("vastaukset", foorumDao.findVastaukset(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "keskustelu");
//        }, new ThymeleafTemplateEngine());
    }
}
