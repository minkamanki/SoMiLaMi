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
    private Integer viesti;
    private String lahettaja;
    private String sisalto;
    private String aika;

    public Vastaus(Integer id, Integer viesti, String lahetaja, String sisalto, String aika) {
        this.id = id;
        this.viesti = viesti;
        this.lahettaja = lahetaja;
        this.sisalto = sisalto;
        this.aika = aika;

    }
    

    public String getAika() {
        return aika;
    }

    public String getLahettaja() {
        return lahettaja;
    }

    public String getSisalto() {
        return sisalto;
    }

    public Integer getViesti() {
        return viesti;
    }

    public Integer getId() {
        return id;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public void setLahettaja(String lahetaja) {
        this.lahettaja = lahetaja;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setViesti(Integer viesti) {
        this.viesti = viesti;
    }
//sfa
}
