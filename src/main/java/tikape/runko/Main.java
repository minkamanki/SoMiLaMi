package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.FoorumDao;

public class Main {
    /*mainin suorittamalla pääsee selaimella "osoitteessa" http://localhost:4567/ olevaan SoMiLaMi foorumiin
    jossa sivulla http://localhost:4567/aiheet/ on listattuna aiheet. Jokaisella aiheelle on myös oma sivus http://localhost:4567/aiheet/"aiheen id".
    http://localhost:4567/keskustelu/"keskustelunavauksen id" osoitteesta löytyy vastaukset kesksutelunavaukseen.
    */

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:SoMiLaMi.db");
//        database.init();

        FoorumDao foorumDao = new FoorumDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/aiheet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aiheet", foorumDao.findAll());

            return new ModelAndView(map, "aiheet");
        }, new ThymeleafTemplateEngine());

        get("/aiheet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelut", foorumDao.findKeskustelut(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());
        
        get("/keskustelu/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("vastaukset", foorumDao.findVastaukset(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());
    }
}
