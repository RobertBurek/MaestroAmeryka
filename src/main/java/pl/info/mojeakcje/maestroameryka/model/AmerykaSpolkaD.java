package pl.info.mojeakcje.maestroameryka.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Table(name = "ameryka_spolki")
public class AmerykaSpolkaD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private String name;
    private String market;
    private String sector;
    private String industry;
    private String note;
    private Double day1218;
    private Double day0119;
    private Double day0219;
    private Double day0319;
    private Double day0419;
    private Double day0519;
    private Double day0619;
    private Double day0719;
    private Double day0819;
    private Double day0919;
    private Double day1019;
    private Double day1119;
    private Double day1219;
    private Double day0120;
    private Double day0220;
    private Double day0320;
    private Double day0420;
    private Double day0520;
    private Double day0620;
    private Double day0720;

    public AmerykaSpolkaD() {
    }

    public AmerykaSpolkaD(String ticker, String name, String market, String sector, String industry) {
        this.ticker = ticker;
        this.name = name;
        this.market = market;
        this.sector = sector;
        this.industry = industry;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDay1218() {
        return day1218;
    }

    public void setDay1218(Double day1218) {
        this.day1218 = day1218;
    }

    public Double getDay0119() {
        return day0119;
    }

    public void setDay0119(Double day0119) {
        this.day0119 = day0119;
    }

    public Double getDay0219() {
        return day0219;
    }

    public void setDay0219(Double day0219) {
        this.day0219 = day0219;
    }

    public Double getDay0319() {
        return day0319;
    }

    public void setDay0319(Double day0319) {
        this.day0319 = day0319;
    }

    public Double getDay0419() {
        return day0419;
    }

    public void setDay0419(Double day0419) {
        this.day0419 = day0419;
    }

    public Double getDay0519() {
        return day0519;
    }

    public void setDay0519(Double day0519) {
        this.day0519 = day0519;
    }

    public Double getDay0619() {
        return day0619;
    }

    public void setDay0619(Double day0619) {
        this.day0619 = day0619;
    }

    public Double getDay0719() {
        return day0719;
    }

    public void setDay0719(Double day0719) {
        this.day0719 = day0719;
    }

    public Double getDay0819() {
        return day0819;
    }

    public void setDay0819(Double day0819) {
        this.day0819 = day0819;
    }

    public Double getDay0919() {
        return day0919;
    }

    public void setDay0919(Double day0919) {
        this.day0919 = day0919;
    }

    public Double getDay1019() {
        return day1019;
    }

    public void setDay1019(Double day1019) {
        this.day1019 = day1019;
    }

    public Double getDay1119() {
        return day1119;
    }

    public void setDay1119(Double day1119) {
        this.day1119 = day1119;
    }

    public Double getDay1219() {
        return day1219;
    }

    public void setDay1219(Double day1219) {
        this.day1219 = day1219;
    }

    public Double getDay0120() {
        return day0120;
    }

    public void setDay0120(Double day0120) {
        this.day0120 = day0120;
    }

    public Double getDay0220() {
        return day0220;
    }

    public void setDay0220(Double day0220) {
        this.day0220 = day0220;
    }

    public Double getDay0320() {
        return day0320;
    }

    public void setDay0320(Double day0320) {
        this.day0320 = day0320;
    }

    public Double getDay0420() {
        return day0420;
    }

    public void setDay0420(Double day0420) {
        this.day0420 = day0420;
    }

    public Double getDay0520() {
        return day0520;
    }

    public void setDay0520(Double day0520) {
        this.day0520 = day0520;
    }

    public Double getDay0620() {
        return day0620;
    }

    public void setDay0620(Double day0620) {
        this.day0620 = day0620;
    }

    public Double getDay0720() {
        return day0720;
    }

    public void setDay0720(Double day0720) {
        this.day0720 = day0720;
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
                ", day1218='" + day1218 + '\'' +
                ", day0119='" + day0119 + '\'' +
                ", day0219='" + day0219 + '\'' +
                ", day0319='" + day0319 + '\'' +
                ", day0419='" + day0419 + '\'' +
                ", day0519='" + day0519 + '\'' +
                ", day0619='" + day0619 + '\'' +
                ", day0719='" + day0719 + '\'' +
                ", day0819='" + day0819 + '\'' +
                ", day0919='" + day0919 + '\'' +
                ", day1019='" + day1019 + '\'' +
                ", day1119='" + day1119 + '\'' +
                ", day1219='" + day1219 + '\'' +
                ", day0120='" + day0120 + '\'' +
                ", day0220='" + day0220 + '\'' +
                ", day0320='" + day0320 + '\'' +
                ", day0420='" + day0420 + '\'' +
                ", day0520='" + day0520 + '\'' +
                ", day0620='" + day0620 + '\'' +
                ", day0720='" + day0720 + '\'' +
                '}';
    }
}
