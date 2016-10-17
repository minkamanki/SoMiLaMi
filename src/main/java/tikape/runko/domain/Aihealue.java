package tikape.runko.domain;

public class Aihealue {

    private Integer id;
    private String nimi;
    private String viimViestiAika;
    private Integer viesteja;

    public Aihealue(Integer id, String nimi, Integer viesteja, String viimViestiAika) {
        this.id = id;
        this.nimi = nimi;
        this.viesteja = viesteja;
        this.viimViestiAika = viimViestiAika;
    }

    public Aihealue(String nimi, String viimViestiAika, Integer viesteja) {
        this.nimi = nimi;
        this.viimViestiAika = viimViestiAika;
        this.viesteja = viesteja;
    }
    

    public void setViimViestiAika(String viimViestiAika) {
        this.viimViestiAika = viimViestiAika;
    }

    public String getViimViestiAika() {
        return viimViestiAika;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Integer getViesteja() {
        return viesteja;
    }

    public void setViesteja(Integer viesteja) {
        this.viesteja = viesteja;
    }

}
