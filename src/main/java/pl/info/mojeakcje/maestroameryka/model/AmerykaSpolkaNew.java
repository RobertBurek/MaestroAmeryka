package pl.info.mojeakcje.maestroameryka.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AmerykaSpolkaNew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private String name;
    private String market;
    private String sector;
    private String industry;
    private String note;
    private String day12M;
    private String course12M;
    private String day1M;
    private String course1M;
    private String day3M;
    private String course3M;
    private String courseCurrent;
    private String dayCourseCurrent;
    private String dayYTD;
    private String courseYTD;
    private String yTD;
    private String m1;
    private String m3;
    private String m12;
    private String website;

    public AmerykaSpolkaNew() {
    }

    public AmerykaSpolkaNew(String ticker, String name, String market, String sector, String industry, String note) {
        this.ticker = ticker;
        this.name = name;
        this.market = market;
        this.sector = sector;
        this.industry = industry;
        this.note = note;
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

    public String getDay12M() {
        return day12M;
    }

    public void setDay12M(String day12M) {
        this.day12M = day12M;
    }

    public String getCourse12M() {
        return course12M;
    }

    public void setCourse12M(String course12M) {
        this.course12M = course12M;
    }

    public String getDay1M() {
        return day1M;
    }

    public void setDay1M(String day1M) {
        this.day1M = day1M;
    }

    public String getCourse1M() {
        return course1M;
    }

    public void setCourse1M(String course1M) {
        this.course1M = course1M;
    }

    public String getDay3M() {
        return day3M;
    }

    public void setDay3M(String day3M) {
        this.day3M = day3M;
    }

    public String getCourse3M() {
        return course3M;
    }

    public void setCourse3M(String course3M) {
        this.course3M = course3M;
    }

    public String getCourseCurrent() {
        return courseCurrent;
    }

    public void setCourseCurrent(String courseCurrent) {
        this.courseCurrent = courseCurrent;
    }

    public String getDayCourseCurrent() {
        return dayCourseCurrent;
    }

    public void setDayCourseCurrent(String dayCourseCurrent) {
        this.dayCourseCurrent = dayCourseCurrent;
    }

    public String getDayYTD() {
        return dayYTD;
    }

    public void setDayYTD(String dayYTD) {
        this.dayYTD = dayYTD;
    }

    public String getCourseYTD() {
        return courseYTD;
    }

    public void setCourseYTD(String courseYTD) {
        this.courseYTD = courseYTD;
    }

    public String getyTD() {
        return yTD;
    }

    public void setyTD(String yTD) {
        this.yTD = yTD;
    }

    public String getM1() {
        return m1;
    }

    public void setM1(String m1) {
        this.m1 = m1;
    }

    public String getM3() {
        return m3;
    }

    public void setM3(String m3) {
        this.m3 = m3;
    }

    public String getM12() {
        return m12;
    }

    public void setM12(String m12) {
        this.m12 = m12;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String webside) {
        this.website = webside;
    }

    @Override
    public String toString() {
        return "AmerykaSpolkaNew{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", market='" + market + '\'' +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                ", note='" + note + '\'' +
                ", day12M='" + day12M + '\'' +
                ", course12M='" + course12M + '\'' +
                ", day1M='" + day1M + '\'' +
                ", course1M='" + course1M + '\'' +
                ", day3M='" + day3M + '\'' +
                ", course3M='" + course3M + '\'' +
                ", courseCurrent='" + courseCurrent + '\'' +
                ", dayCourseCurrent='" + dayCourseCurrent + '\'' +
                ", dayYTD='" + dayYTD + '\'' +
                ", courseYTD='" + courseYTD + '\'' +
                ", yTD='" + yTD + '\'' +
                ", m1='" + m1 + '\'' +
                ", m3='" + m3 + '\'' +
                ", m12='" + m12 + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
