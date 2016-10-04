package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.FoorumDao;
import tikape.runko.database.KeskustelutDao;

public class Main {
    /*mainin suorittamalla pääsee selaimella "osoitteessa" http://localhost:4567/ olevaan SoMiLaMi foorumiin
    jossa sivulla http://localhost:4567/aiheet/ on listattuna aiheet. Jokaisella aiheelle on myös oma sivus http://localhost:4567/aiheet/"aiheen id".
    */

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:SoMiLaMi.db");
//        database.init();

        FoorumDao foorumDao = new FoorumDao(database);
        KeskustelutDao kDao = new KeskustelutDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

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
    }
}
