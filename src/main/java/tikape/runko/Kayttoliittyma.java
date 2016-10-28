/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko;

import java.sql.SQLException;

import java.util.List;
import java.util.Scanner;
import tikape.runko.database.FoorumDao;
import tikape.runko.domain.Aihealue;
import tikape.runko.domain.Keskustelunavaus;
import tikape.runko.domain.Vastaus;

/**
 *
 * @author tminka
 */
public class Kayttoliittyma {

    private FoorumDao foorumDao;
    private List<Aihealue> aiheet;
    private List<Keskustelunavaus> viestit;
    private int key;

    public Kayttoliittyma(FoorumDao foorumDao) {
        this.foorumDao = foorumDao;
    }

    public void tekstikayttoliityma() throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Haluatko mennä tekstikäyttöliittymään? kirjoita 'k' jos kyllä");

        String syote = scanner.nextLine();

        while (syote.equals("k")) {
            etsiAiheet();

            System.out.println("Lisätäänkö aihe? 'k', jos lisätään");
            String vastaus = scanner.nextLine();

            if (vastaus.equals("k")) {
                System.out.println("Anna uusi aihe:");
                String aihe = scanner.nextLine();
                lisaaAihe(aihe);
            } else {
                System.out.println("Mihin aihealueeseen siirrytään? kirjota aiheen nimi");
                String aihe = scanner.nextLine();
                if (loytyykoAihe(aihe)) {
                    etsiViestit();

                    System.out.println("Lisätäänkö uusi viesti? 'k', jos lisätään");
                    vastaus = scanner.nextLine();
                    if (vastaus.equals("k")) {
                        System.out.println("Anna otsikko:");
                        String otsikko = scanner.nextLine();
                        System.out.println("Anna nimesi:");
                        String lahettaja = scanner.nextLine();
                        System.out.println("Anna viestin sisältö:");
                        String sisalto = scanner.nextLine();
                        lisaaViesti(lahettaja, otsikko, sisalto);
                    } else {
                        System.out.println("Mihin keskusteluun siirrytään? kirjoita keskustelun otsikko");
                        String keskustelu = scanner.nextLine();
                        if (loytyykoKeskustelu(keskustelu)) {
                            etsiVastaukset();
                            System.out.println("Lisää vastaus? 'k'");
                            vastaus = scanner.nextLine();
                            if (vastaus.equals("k")) {
                                System.out.println("Anna nimesi:");
                                String nimi = scanner.nextLine();
                                System.out.println("Anna vastauksesi:");
                                String viesti = scanner.nextLine();
                                lisaaVastaus(nimi, viesti);
                            }

                        } else {
                            System.out.println("Ei löytynyt tämän nimistä kekustelua.");
                        }
                    }

                } else {
                    System.out.println("Ei löytynyt tämän nimistä aihetta.");
                }

            }

            System.out.println("Kirjoita 'k', jos haluat jatkaa tekstikäyttöliittymässä.");
            syote = scanner.nextLine();
        }

    }

    public void lisaaAihe(String aihe) throws SQLException {
        foorumDao.addAihealue(aihe);
    }

    public void etsiAiheet() throws SQLException {
        System.out.println("ALUEET" + '\n');
        aiheet = foorumDao.findAll();
        for (Aihealue aihe : aiheet) {
            System.out.println(aihe.getNimi());
        }

    }

    public void lisaaViesti(String lahettaja, String otsikko, String sisalto) throws SQLException {
        foorumDao.addKeskustelunavaus(lahettaja, otsikko, sisalto, key);
    }

    public void etsiViestit() throws SQLException {
        System.out.println("Viestit:");
        viestit = foorumDao.findKeskustelut(key);

        for (Keskustelunavaus viesti : viestit) {
            System.out.println(viesti.getOtsikko());
//            System.out.println("Viesti: " + viesti.getSisalto()+ '\n');
        }
    }

    public void lisaaVastaus(String lahettaja, String sisalto) throws SQLException {
        foorumDao.addVastaus(lahettaja, sisalto, key);
    }

    public void etsiVastaukset() throws SQLException {
        System.out.println();
        System.out.println("Vastukset:");
        List<Vastaus> vastaukset = foorumDao.findVastaukset(key);
        for (Vastaus vastaus : vastaukset) {
            System.out.println(vastaus.getSisalto() + " t. " + vastaus.getLahettaja());
        }
    }

    public boolean loytyykoAihe(String etsittava) {
        for (Aihealue aihe : aiheet) {
            if (aihe.getNimi().equals(etsittava)) {
                key = aihe.getId();
                return true;
            }
        }
        return false;
    }

    private boolean loytyykoKeskustelu(String keskustelu) {
        for (Keskustelunavaus ka : viestit) {
            if (ka.getOtsikko().equals(keskustelu)) {
                key = ka.getId();
                System.out.println("Viesti:");
                System.out.println(ka.getOtsikko());
                System.out.println(ka.getSisalto() + " t. " + ka.getLahettaja());
                return true;
            }
        }
        return false;
    }
}
