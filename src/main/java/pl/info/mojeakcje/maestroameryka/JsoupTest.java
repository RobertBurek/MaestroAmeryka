package pl.info.mojeakcje.maestroameryka;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.util.resources.LocaleData;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.StringTokenizer;

public class JsoupTest {
    public static void main(String[] args) throws IOException {
        Connection connect = Jsoup.connect("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=1556191608&period2=1587814008&interval=1d&events=history");
        Connection connec2 = Jsoup.connect("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=1556576877&period2=1588199277&interval=1d&events=history");
        Connection connec2 = Jsoup.connect("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=1556612951&period2=1588235351&interval=1d&events=history");
//        https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=1556613197&period2=1588235597&interval=1d&events=history
//        1588235597
//        1588235351  - 29.04.2020
//        1588199277  - 29.04.2020
//        1587814008  - 24.04.2020
//        LocaleData localeData;
//        localeData.getCurrencyNames();
        Document document = connect.get();
        StringTokenizer st = new StringTokenizer (document.body().getAllElements().toString());
        String dane = "";
        String[] result;
        while (st.hasMoreTokens ()) {
            dane = st.nextToken ();
            if (dane.startsWith("2020-04-24")) {
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
