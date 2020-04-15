package pl.info.mojeakcje.maestroameryka.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AmerykaSpolkaStrategia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private String name;
    private String market;
    private String sector;
    private String industry;
    private String note;
    private String lastCourse;
    private String m1TD;
    private String m2TD;
    private String yTD;


    public AmerykaSpolkaStrategia() {
    }

    public AmerykaSpolkaStrategia(String ticker, String name, String market, String sector, String industry) {
        this.ticker = ticker;
        this.name = name;
        this.market = market;
        this.sector = sector;
        this.industry = industry;
    }

    public String getLastCourse() {
        return lastCourse;
    }

    public void setLastCourse(String lastCourse) {
        this.lastCourse = lastCourse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getM1TD() {
        return m1TD;
    }

    public void setM1TD(String m1TD) {
        this.m1TD = m1TD;
    }

    public String getM2TD() {
        return m2TD;
    }

    public void setM2TD(String m2TD) {
        this.m2TD = m2TD;
    }

    public String getyTD() {
        return yTD;
    }

    public void setyTD(String yTD) {
        this.yTD = yTD;
    }

    @Override
    public String toString() {
        return "AmerykaSpolka{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", market='" + market + '\'' +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                ", note='" + note + '\'' +
                ", 1MTD='" + m1TD + '\'' +
                ", 2MTD='" + m2TD + '\'' +
                ", YTD='" + yTD + '\'' +
                '}';
    }
}
