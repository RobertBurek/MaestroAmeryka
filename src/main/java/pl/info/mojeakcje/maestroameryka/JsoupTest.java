package pl.info.mojeakcje.maestroameryka;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.StringTokenizer;

public class JsoupTest {
    public static void main(String[] args) throws IOException {
//    public void mojeDane() throws IOException {
        Date date = new Date(1587040275487L);
        LocalDate localDate = LocalDate.now();
        Long dateNow = date.getTime();
        System.out.println(date);
        System.out.println(dateNow);
        System.out.println(localDate.toString());
        Connection connect = Jsoup.connect("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=" + (dateNow-259200) + "&period2=" + dateNow + "&interval=1d&events=history");
//        Connection connec2 = Jsoup.connect("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=1556576877&period2=1588199277&interval=1d&events=history");
//        Connection connec3 = Jsoup.connect("https://query1.finance.yahoo" +
//                ".com/v7/finance/download/FOXA?period1=1556612951&period2=1588235351&interval=1d&events=history");
//        https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=1556613197&period2=1588235597&interval=1d&events=history
//        1588235597
//        1588235351  - 29.04.2020
//        1588199277  - 29.04.2020
//        1587814008  - 24.04.2020
//        LocaleData localeData;
//        localeData.getCurrencyNames();
        Document document = connect.get();
        StringTokenizer st = new StringTokenizer(document.body().getAllElements().toString());
        String dane = "";
        String[] result;
        while (st.hasMoreTokens()) {
            dane = st.nextToken();
            if (dane.startsWith(localDate.toString())) {
                result = dane.split(",");
                System.out.println(result[5]);
            }
        }
        //        Elements allH1 = document.select("h1");
//        for(Element elem: allH1) {
//            System.out.println(elem.text());
//        }
//        System.out.println(document.body());
    }

}
