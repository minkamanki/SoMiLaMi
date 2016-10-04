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
public class Keskustelunavaus {

    private Integer id;
    private String lahettaja;
    private String otsikko;
    private String sisalto;
    private Timestamp aika;
    private Integer aihe;

    public Keskustelunavaus(Integer id, String lahettaja, String otsikko, String sisalto, Timestamp aika, Integer aihe) {
        this.id = id;
        this.lahettaja = lahettaja;
        this.aika = aika;
        this.otsikko = otsikko;
        this.sisalto = sisalto;
        this.aihe = aihe;
    }

    public Integer getId() {
        return id;
    }

    public String getSisalto() {
        return sisalto;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public Timestamp getAika() {
        return aika;
    }

    public Integer getAihe() {
        return aihe;
    }

    public String getLahettaja() {
        return lahettaja;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }

    public void setLahettaja(String lahettaja) {
        this.lahettaja = lahettaja;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setAihe(Integer aihe) {
        this.aihe = aihe;
    }

}
