package pl.info.mojeakcje.maestroameryka;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.SpolkaAmeryka;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


@Service
public class CzytanieDanychJsoup {


//    public static void main(String[] args) throws IOException {

    public static List<SpolkaAmeryka> run(List<AmerykaSpolka> staraListaSpolek) throws IOException {

        List<SpolkaAmeryka> nowaListaSpolek = new ArrayList<>();

        Double course1M = 0.0;
        Double course3M = 0.0;
        Double course12M = 0.0;
        Double courseYTD = 0.0;
        Double courseCurrent = 0.0;

        String dayCourse1M = "";
        String dayCourse3M = "";
        String dayCourse12M = "";
        String dayCourseYTD = "";
        String dayCourseCurrent = "";

        // ustalanie parametrów dla URL
        LocalDate localDateStart = LocalDate.of(2020, 05, 02);
        LocalDate localDateNow = LocalDate.now();
        Period period2 = Period.between(localDateStart, localDateNow);
        System.out.println("Periot: " + period2.getDays());
        Long deltaTime = period2.getDays() * 86400L;

        Long startTime = 1588450505L;  //  02.05.2020 22:15
        Long rokTime = 31536000L + 5 * 86400L;  // roku + 5 dni

        LocalDate localDateFind = LocalDate.now();

        for (AmerykaSpolka amSp : staraListaSpolek) {

            // Tworzenie nowej spólki  - konstruktor (String ticker, String name, String market, String sector, String industry, String note)
            SpolkaAmeryka nowaSpolka = new SpolkaAmeryka(amSp.getTicker(), amSp.getName(), amSp.getMarket(), amSp.getSector(), amSp.getIndustry(), amSp.getNote());

            System.out.println("https://query1.finance.yahoo.com/v7/finance/download/" + amSp.getTicker() + "?period1=" + (startTime + deltaTime - rokTime) + "&period2=" + (startTime + deltaTime) + "&interval=1d&events=history");
            Connection connect = Jsoup.connect("https://query1.finance.yahoo.com/v7/finance/download/" + amSp.getTicker() + "?period1=" + (startTime + deltaTime - rokTime) + "&period2=" + (startTime + deltaTime) + "&interval=1d&events=history");
            Document document = connect.get();

            // Tworzenie listy dat
            List<String> listaDat = new ArrayList<>();
            if (document.body().getAllElements().toString().contains(localDateNow.toString())) {
                listaDat.add(localDateNow.toString());
            } else if (document.body().getAllElements().toString().contains(localDateNow.minusDays(1).toString())) {
                listaDat.add(localDateNow.minusDays(1).toString());
                localDateNow = localDateNow.minusDays(1);
            }
            if (!listaDat.isEmpty()) {
                System.out.println("ListaDat ma date current: " + listaDat);
                // ustalamy datę dla 12M
                if (document.body().getAllElements().toString().contains(localDateNow.minusYears(1).toString())) {
                    listaDat.add(localDateNow.minusYears(1).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusYears(1).minusDays(1).toString())) {
                    listaDat.add(localDateNow.minusYears(1).minusDays(1).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusYears(1).minusDays(2).toString())) {
                    listaDat.add(localDateNow.minusYears(1).minusDays(2).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusYears(1).minusDays(3).toString())) {
                    listaDat.add(localDateNow.minusYears(1).minusDays(3).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusYears(1).minusDays(4).toString())) {
                    listaDat.add(localDateNow.minusYears(1).minusDays(4).toString());
                } else listaDat.add("brak");
                System.out.println("ListaDat ma datę 12M: " + listaDat);
                // ustalamy datę dla YTD
                if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, 12, 31).toString())) {
                    listaDat.add(localDateFind.of(localDateNow.getYear() - 1, 12, 31).toString());
                } else if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, 12, 30).toString())) {
                    listaDat.add(localDateFind.of(localDateNow.getYear() - 1, 12, 30).toString());
                } else if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, 12, 29).toString())) {
                    listaDat.add(localDateFind.of(localDateNow.getYear() - 1, 12, 29).toString());
                } else if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, 12, 28).toString())) {
                    listaDat.add(localDateFind.of(localDateNow.getYear() - 1, 12, 28).toString());
                } else listaDat.add("brak");
                System.out.println("ListaDat ma datę YTD: " + listaDat);
                // ustalamy datę dla 3M
                if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(3).toString())) {
                    listaDat.add(localDateNow.minusMonths(3).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(3).minusDays(1).toString())) {
                    listaDat.add(localDateNow.minusMonths(3).minusDays(1).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(3).minusDays(2).toString())) {
                    listaDat.add(localDateNow.minusMonths(3).minusDays(2).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(3).minusDays(3).toString())) {
                    listaDat.add(localDateNow.minusMonths(3).minusDays(3).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(3).minusDays(4).toString())) {
                    listaDat.add(localDateNow.minusMonths(3).minusDays(4).toString());
                } else listaDat.add("brak");
                System.out.println("ListaDat ma datę 3M: " + listaDat);
                // ustalamy datę dla 1M
                if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(1).toString())) {
                    listaDat.add(localDateNow.minusMonths(1).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(1).minusDays(1).toString())) {
                    listaDat.add(localDateNow.minusMonths(1).minusDays(1).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(1).minusDays(2).toString())) {
                    listaDat.add(localDateNow.minusMonths(1).minusDays(2).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(1).minusDays(3).toString())) {
                    listaDat.add(localDateNow.minusMonths(1).minusDays(3).toString());
                } else if (document.body().getAllElements().toString().contains(localDateNow.minusMonths(1).minusDays(4).toString())) {
                    listaDat.add(localDateNow.minusMonths(1).minusDays(4).toString());
                } else listaDat.add("brak");
                System.out.println("ListaDat ma datę 1M: " + listaDat);
            }

            // wyszukiwanie wartości w pobranym pliku
            if (listaDat.size() > 0) {
                StringTokenizer st = new StringTokenizer(document.body().getAllElements().toString());
                String dane = "";
                String[] result;
                while (st.hasMoreTokens()) {
                    dane = st.nextToken();
//            System.out.println(dane);
                    if (dane.startsWith(listaDat.get(0))) {
                        result = dane.split(",");
                        courseCurrent = Double.parseDouble(result[4]);
                        nowaSpolka.setCourseCurrent(new DecimalFormat("# 0.000#").format(courseCurrent));
                        nowaSpolka.setDayCourseCurrent(result[0]);
//                        System.out.println("courseCurrent " + nowaSpolka.getDayCourseCurrent() + " - " + courseCurrent);
                        System.out.println("courseCurrent " + nowaSpolka.getDayCourseCurrent() + " - " + nowaSpolka.getCourseCurrent());
                    }
                    if (dane.startsWith(listaDat.get(1))) {
                        result = dane.split(",");
                        course12M = Double.parseDouble(result[4]);
                        nowaSpolka.setCourse12M(new DecimalFormat("# 0.000#").format(course12M));
                        nowaSpolka.setDay12M(result[0]);
//                        System.out.println("course12M " + nowaSpolka.getDay12M() + " - " + course12M);
                        System.out.println("course12M " + nowaSpolka.getDay12M() + " - " + nowaSpolka.getCourse12M());
                    }
                    if (dane.startsWith(listaDat.get(2))) {
                        result = dane.split(",");
                        courseYTD = Double.parseDouble(result[4]);
                        nowaSpolka.setCourseYTD(new DecimalFormat("# 0.000#").format(courseYTD));
                        nowaSpolka.setDayYTD(result[0]);
//                        System.out.println("courseYTD " + nowaSpolka.getDayYTD() + " - " + courseYTD);
                        System.out.println("courseYTD " + nowaSpolka.getDayYTD() + " - " + nowaSpolka.getCourseYTD());
                    }
                    if (dane.startsWith(listaDat.get(3))) {
                        result = dane.split(",");
                        course3M = Double.parseDouble(result[4]);
                        nowaSpolka.setCourse3M(new DecimalFormat("# 0.000#").format(course3M));
                        nowaSpolka.setDay3M(result[0]);
//                        System.out.println("course3M " + nowaSpolka.getDay3M() + " - " + course3M);
                        System.out.println("course3M " + nowaSpolka.getDay3M() + " - " + nowaSpolka.getCourse3M());
                    }
                    if (dane.startsWith(listaDat.get(4))) {
                        result = dane.split(",");
                        course1M = Double.parseDouble(result[4]);
                        nowaSpolka.setCourse1M(new DecimalFormat("# 0.000#").format(course1M));
                        nowaSpolka.setDay1M(result[0]);
//                        System.out.println("course1M " + nowaSpolka.getDay1M() + " - " + course1M);
                        System.out.println("course1M " + nowaSpolka.getDay1M() + " - " + nowaSpolka.getCourse1M());
                    }
                }
            } // warunek nie pustej listy dat

            // Tworzenie nowej spólki  - konstruktor (String ticker, String name, String market, String sector, String industry, String note)


            nowaListaSpolek.add(nowaSpolka);

        } // pętla po starej liście spółek i tworzenie nowej bazy
        return nowaListaSpolek; //cała metoda
    }


    //        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//            Metoda  automatycznego zapisu danych z HTTP, uruchamiana zawsze przy uruchomianiu aplikacji
    @EventListener(ApplicationReadyEvent.class)
    public void get() {

    }


}
