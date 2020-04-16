package pl.info.mojeakcje.maestroameryka.model;


import javax.persistence.*;

@Entity
//@Table(name = "ameryka_spolki")
public class AmerykaSpolka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private String name;
    private String market;
    private String sector;
    private String industry;
    private String note;
    private String day1218;
    private String day0119;
    private String day0219;
    private String day0319;
    private String day0419;
    private String day0519;
    private String day0619;
    private String day0719;
    private String day0819;
    private String day0919;
    private String day1019;
    private String day1119;
    private String day1219;
    private String day0120;
    private String day0220;
    private String day0320;
    private String day0420;
    private String day0520;
    private String day0620;
    private String day0720;
    private String day0820;
    private String day0920;
    private String day1020;
    private String day1120;
    private String day1220;
    private String yTD;
    private String m1TD;
    private String m2TD;

    public AmerykaSpolka() {
    }

    public AmerykaSpolka(String ticker, String name, String market, String sector, String industry) {
        this.ticker = ticker;
        this.name = name;
        this.market = market;
        this.sector = sector;
        this.industry = industry;
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

    public String getDay1218() {
        return day1218;
    }

    public void setDay1218(String day1218) {
        this.day1218 = day1218;
    }

    public String getDay0119() {
        return day0119;
    }

    public void setDay0119(String day0119) {
        this.day0119 = day0119;
    }

    public String getDay0219() {
        return day0219;
    }

    public void setDay0219(String day0219) {
        this.day0219 = day0219;
    }

    public String getDay0319() {
        return day0319;
    }

    public void setDay0319(String day0319) {
        this.day0319 = day0319;
    }

    public String getDay0419() {
        return day0419;
    }

    public void setDay0419(String day0419) {
        this.day0419 = day0419;
    }

    public String getDay0519() {
        return day0519;
    }

    public void setDay0519(String day0519) {
        this.day0519 = day0519;
    }

    public String getDay0619() {
        return day0619;
    }

    public void setDay0619(String day0619) {
        this.day0619 = day0619;
    }

    public String getDay0719() {
        return day0719;
    }

    public void setDay0719(String day0719) {
        this.day0719 = day0719;
    }

    public String getDay0819() {
        return day0819;
    }

    public void setDay0819(String day0819) {
        this.day0819 = day0819;
    }

    public String getDay0919() {
        return day0919;
    }

    public void setDay0919(String day0919) {
        this.day0919 = day0919;
    }

    public String getDay1019() {
        return day1019;
    }

    public void setDay1019(String day1019) {
        this.day1019 = day1019;
    }

    public String getDay1119() {
        return day1119;
    }

    public void setDay1119(String day1119) {
        this.day1119 = day1119;
    }

    public String getDay1219() {
        return day1219;
    }

    public void setDay1219(String day1219) {
        this.day1219 = day1219;
    }

    public String getDay0120() {
        return day0120;
    }

    public void setDay0120(String day0120) {
        this.day0120 = day0120;
    }

    public String getDay0220() {
        return day0220;
    }

    public void setDay0220(String day0220) {
        this.day0220 = day0220;
    }

    public String getDay0320() {
        return day0320;
    }

    public void setDay0320(String day0320) {
        this.day0320 = day0320;
    }

    public String getDay0420() {
        return day0420;
    }

    public void setDay0420(String day0420) {
        this.day0420 = day0420;
    }

    public String getDay0520() {
        return day0520;
    }

    public void setDay0520(String day0520) {
        this.day0520 = day0520;
    }

    public String getDay0620() {
        return day0620;
    }

    public void setDay0620(String day0620) {
        this.day0620 = day0620;
    }

    public String getDay0720() {
        return day0720;
    }

    public void setDay0720(String day0720) {
        this.day0720 = day0720;
    }

    public String getDay0820() {
        return day0820;
    }

    public void setDay0820(String day0820) {
        this.day0820 = day0820;
    }

    public String getDay0920() {
        return day0920;
    }

    public void setDay0920(String day0920) {
        this.day0920 = day0920;
    }

    public String getDay1020() {
        return day1020;
    }

    public void setDay1020(String day1020) {
        this.day1020 = day1020;
    }

    public String getDay1120() {
        return day1120;
    }

    public void setDay1120(String day1120) {
        this.day1120 = day1120;
    }

    public String getDay1220() {
        return day1220;
    }

    public void setDay1220(String day1220) {
        this.day1220 = day1220;
    }

    public String getyTD() {
        return yTD;
    }

    public void setyTD(String yTD) {
        this.yTD = yTD;
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
                ", day0319='" + day0319 + '\'' +
                ", day0320='" + day0320 + '\'' +
                ", day0420='" + day0420 + '\'' +
                '}';
    }
}
