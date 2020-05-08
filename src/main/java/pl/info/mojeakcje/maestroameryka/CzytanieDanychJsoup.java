package pl.info.mojeakcje.maestroameryka;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeFilter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.info.mojeakcje.maestroameryka.model.SpolkaAmeryka;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class CzytanieDanychJsoup {


//    public static void main(String[] args) throws IOException {

    public static List<SpolkaAmeryka> run(List<String> listaTicker) throws IOException {

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

        LocalDate localDateStart = LocalDate.of(2020, 05, 02);
        LocalDate localDateNow = LocalDate.now();
        Period period2 = Period.between(localDateStart, localDateNow);
        System.out.println("Periot: " + period2.getDays());
        Long deltaTime = period2.getDays() * 86400L;

        Long startTime = 1588450505L;  //  02.05.2020 22:15
        Long rokTime = 31536000L + 5 * 86400L;  // roku + 5 dni

        LocalDate localDateFind = LocalDate.now();

        System.out.println("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=" + (startTime + deltaTime - rokTime) + "&period2=" + (startTime + deltaTime) + "&interval=1d&events=history");
        Connection connect = Jsoup.connect("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=" + (startTime + deltaTime - rokTime) + "&period2=" + (startTime + deltaTime) + "&interval=1d&events=history");

        Document document = connect.get();
        List<String> listaDat = new ArrayList<>();
        if (document.body().getAllElements().toString().contains(localDateNow.toString())) {
            listaDat.add(localDateNow.toString());
        } else if (document.body().getAllElements().toString().contains(localDateNow.minusDays(1).toString())){
            listaDat.add(localDateNow.minusDays(1).toString());
            localDateNow = localDateNow.minusDays(1);
//            System.out.println("Teraz localDateNow: "+localDateNow);
        }

        if (!listaDat.isEmpty()){
            System.out.println("ListaDat nie jest pusta: "+listaDat);
            if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth()).toString())) {
                listaDat.add(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth()).toString());
            } else if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth()-1).toString())) {
                listaDat.add(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth()-1).toString());
            } else if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth()-2).toString())) {
                listaDat.add(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth() - 2).toString());
            } else if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth()-3).toString())) {
                listaDat.add(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth() - 3).toString());
            } else if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth()-4).toString())) {
                listaDat.add(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth() - 4).toString());
            }
            System.out.println("ListaDat ma datę 12M: "+listaDat);
            if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, 12, 31).toString())) {
                listaDat.add(localDateFind.of(localDateNow.getYear() - 1, 12, 31).toString());
            } else if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, 12, 30).toString())) {
                listaDat.add(localDateFind.of(localDateNow.getYear() - 1, 12, 30).toString());
            } else if (document.body().getAllElements().toString().contains(localDateFind.of(localDateNow.getYear() - 1, 12, 29).toString())) {
                listaDat.add(localDateFind.of(localDateNow.getYear() - 1, 12, 29).toString());
            }
            System.out.println("ListaDat ma datę YTD: "+listaDat);

        }


        StringTokenizer st = new StringTokenizer(document.body().getAllElements().toString());
        String dane = "";
        String[] result;
        while (st.hasMoreTokens()) {
            dane = st.nextToken();
            System.out.println(dane);
            if (dane.startsWith(localDateNow.toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
//                System.out.println(result[0] + " - " + result[4]);
                courseCurrent = Double.parseDouble(result[4]);
                dayCourseCurrent =result[0];
                System.out.println(dayCourseCurrent + " - " + courseCurrent);
            }
            if (dane.startsWith(localDateFind.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth()).toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
//                System.out.println(result[0] + " - " + result[4]);
                course12M = Double.parseDouble(result[4]);
                dayCourse12M =result[0];
                System.out.println(dayCourse12M + " - " + course12M);
            }
            if (dane.startsWith(localDateFind.of(localDateNow.getYear() - 1, 12, 31).toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
//                System.out.println(result[0] + " - " + result[4]);
                courseYTD = Double.parseDouble(result[4]);
                dayCourseYTD =result[0];
                System.out.println(dayCourseYTD + " - " + courseYTD);
            }
//            if (dane.startsWith(localDateNow.minusDays(30).toString())) {
            if (dane.startsWith(localDateFind.of(localDateNow.getYear(), localDateNow.getMonth().minus(3), localDateNow.getDayOfMonth()).toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
//                System.out.println(result[0] + " - " + result[4]);
                course3M = Double.parseDouble(result[4]);
                dayCourse3M =result[0];
                System.out.println(dayCourse3M + " - " + course3M);
            }
//            if (dane.startsWith(localDateNow.minusDays(365).toString())) {
            if (dane.startsWith(localDateFind.of(localDateNow.getYear(), localDateNow.getMonth().minus(1), localDateNow.getDayOfMonth()).toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
//                System.out.println(result[0] + " - " + result[4]);
                course1M = Double.parseDouble(result[4]);
                dayCourse1M =result[0];
                System.out.println(dayCourse1M + " - " + course1M);
            }

        }
        return null;
    }

    //        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//            Metoda  automatycznego zapisu danych z HTTP, uruchamiana zawsze przy uruchomianiu aplikacji
    @EventListener(ApplicationReadyEvent.class)
    public void get() {

    }


}
