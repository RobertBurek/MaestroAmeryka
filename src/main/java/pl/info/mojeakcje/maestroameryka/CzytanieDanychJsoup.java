package pl.info.mojeakcje.maestroameryka;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.info.mojeakcje.maestroameryka.model.SpolkaAmeryka;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


@Service
public class CzytanieDanychJsoup {


//    public static void main(String[] args) throws IOException {

    public static List<SpolkaAmeryka> run(List<String> listaTicker) throws IOException {
        Date dateTeraz = new Date();
//        LocalDate localDateStart = LocalDate.of(2020, 05, 07);
        LocalDate localDateStart = LocalDate.of(2020, 05, 02);
        LocalDate localDateNow = LocalDate.now();
        Period period2 = Period.between(localDateStart, localDateNow);
        System.out.println("Periot: " + period2.getDays());
        Long deltaTime = period2.getDays() * 86400L;

//        Long startTime = 1588882505L;  //  07.05.2020 22:15
        Long startTime = 1588450505L;  //  02.05.2020 22:15
        Long rokTime = 31536000L + 5 * 86400L;  // roku + 5 dni

        LocalDate localDateYear = LocalDate.now();
        LocalDate localDate1Month = LocalDate.now();
        LocalDate localDate2Month = LocalDate.now();

        System.out.println("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=" + (startTime + deltaTime - rokTime) + "&period2=" + (startTime + deltaTime) + "&interval=1d&events=history");
        Connection connect = Jsoup.connect("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=" + (startTime + deltaTime - rokTime) + "&period2=" + (startTime + deltaTime) + "&interval=1d&events=history");

        Document document = connect.get();
        StringTokenizer st = new StringTokenizer(document.body().getAllElements().toString());
        String dane = "";
        String[] result;
        while (st.hasMoreTokens()) {
            dane = st.nextToken();
//            System.out.println(dane);
            if (dane.startsWith(localDateNow.toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
                System.out.println(result[0] +" - "+result[4]);
            }
            if (dane.startsWith(localDateYear.of(localDateNow.getYear() - 1, localDateNow.getMonth(), localDateNow.getDayOfMonth()).toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
                System.out.println(result[0] +" - "+result[4]);
            }
            if (dane.startsWith(localDateYear.of(localDateNow.getYear() - 1, 12, 31).toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
                System.out.println(result[0] +" - "+result[4]);
            }
//            if (dane.startsWith(localDateNow.minusDays(30).toString())) {
            if (dane.startsWith(localDateYear.of(localDateNow.getYear(), localDateNow.getMonth().minus(3), localDateNow.getDayOfMonth()).toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
                System.out.println(result[0] +" - "+result[4]);
            }
//            if (dane.startsWith(localDateNow.minusDays(365).toString())) {
            if (dane.startsWith(localDateYear.of(localDateNow.getYear(), localDateNow.getMonth().minus(1), localDateNow.getDayOfMonth()).toString())) {
//            if (dane.startsWith("2019-04-24")) {
                result = dane.split(",");
                System.out.println(result[0] +" - "+result[4]);
            }

        }
        return null;
    }
}
