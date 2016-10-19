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
    private String aika;
    private Integer aihe;
    private String viimVastaus;
    private Integer vastauksiaKpl;
    private String aihealueNimi;

    public Keskustelunavaus(String lahettaja, String otsikko, String sisalto, Integer aihe, String aihealueNimi) {
        this.lahettaja = lahettaja;
        this.otsikko = otsikko;
        this.sisalto = sisalto;
        this.aihe = aihe;
        this.aihealueNimi = aihealueNimi;
    }

    public Keskustelunavaus(Integer id, String lahettaja, String otsikko, String sisalto, String aika, Integer aihe, Integer vastauksiaKpl, String viimVastaus, String aihealueNimi) {
        this.id = id;
        this.lahettaja = lahettaja;
        this.aika = aika;
        this.otsikko = otsikko;
        this.sisalto = sisalto;
        this.aihe = aihe;
        this.vastauksiaKpl = vastauksiaKpl;
        this.viimVastaus = viimVastaus;
        this.aihealueNimi = aihealueNimi;
    }

    public void setAihealueNimi(String aihealueNimi) {
        this.aihealueNimi = aihealueNimi;
    }

    public String getAihealueNimi() {
        return aihealueNimi;
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

    public String getAika() {
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

    public void setAika(String aika) {
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

    public Integer getVastauksiaKpl() {
        return vastauksiaKpl;
    }

    public String getViimVastaus() {
        return viimVastaus;
    }

    public void setVastauksiaKpl(Integer vastauksiaKpl) {
        this.vastauksiaKpl = vastauksiaKpl;
    }

    public void setViimVastaus(String viimVastaus) {
        this.viimVastaus = viimVastaus;
    }

}
