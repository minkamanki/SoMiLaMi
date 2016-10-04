/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.Timestamp;

/**
 *
 * @author tminka
 */
public class Vastaus {

    private Integer id;
    private Keskustelunavaus viesti;
    private String lahetaja;
    private String sisalto;
    private Timestamp aika;

    public Vastaus(Integer id, Keskustelunavaus viesti, String lahetaja, String sisalto, Timestamp aika) {
        this.id = id;
        this.viesti = viesti;
        this.lahetaja = lahetaja;
        this.sisalto = sisalto;
        this.aika = aika;
    }

    public Timestamp getAika() {
        return aika;
    }

    public String getLahetaja() {
        return lahetaja;
    }

    public String getSisalto() {
        return sisalto;
    }

    public Keskustelunavaus getViesti() {
        return viesti;
    }

    public Integer getId() {
        return id;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }

    public void setLahetaja(String lahetaja) {
        this.lahetaja = lahetaja;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setViesti(Keskustelunavaus viesti) {
        this.viesti = viesti;
    }

}
