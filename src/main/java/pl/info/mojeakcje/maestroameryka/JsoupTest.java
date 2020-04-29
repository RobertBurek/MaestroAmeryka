package pl.info.mojeakcje.maestroameryka;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTest {
    public static void main(String[] args) throws IOException {
        Connection connect = Jsoup.connect("https://query1.finance.yahoo.com/v7/finance/download/FOXA?period1=1556191608&period2=1587814008&interval=1d&events=history");
        Document document = connect.get();
        Elements allH1 = document.select("h1");
        for(Element elem: allH1) {
            System.out.println(elem.text());
        }
    }
}
