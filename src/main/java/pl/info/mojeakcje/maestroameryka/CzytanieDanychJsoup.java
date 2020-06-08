package pl.info.mojeakcje.maestroameryka;

import lombok.extern.log4j.Log4j2;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CurrentUser;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

@Log4j2
@Service
public class CzytanieDanychJsoup {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    CurrentUser currentUser;
    QueryRepository queryRepository;
    AmSpRepository amSpRepository;

    public CzytanieDanychJsoup(CurrentUser currentUser, QueryRepository queryRepository, AmSpRepository amSpRepository) {
        this.currentUser = currentUser;
        this.queryRepository = queryRepository;
        this.amSpRepository = amSpRepository;
    }

    public void czytaj() throws InterruptedException {

        log.info(ANSI_YELLOW + "Zacząłem odczyt danych z URL !!!" + ANSI_RESET);
        log.info(ANSI_YELLOW + "A dokładnie: " + LocalTime.now() + ANSI_RESET);

        List<AmerykaSpolka> staraListaSpolek = (List<AmerykaSpolka>) amSpRepository.findAll();

//        List<AmerykaSpolka> staraListaSpolek = new ArrayList<>();
//        staraListaSpolek.add(amSpRepository.findById(1L).get());
//        staraListaSpolek.add(amSpRepository.findById(2L).get());
//        staraListaSpolek.add(amSpRepository.findById(3L).get());
//        staraListaSpolek.add(amSpRepository.findById(4L).get());
//        staraListaSpolek.add(amSpRepository.findById(5L).get());
//        staraListaSpolek.add(amSpRepository.findById(6L).get());
//        staraListaSpolek.add(amSpRepository.findById(7L).get());
//        staraListaSpolek.add(amSpRepository.findById(8L).get());
//        staraListaSpolek.add(amSpRepository.findById(9L).get());
//        staraListaSpolek.add(amSpRepository.findById(10L).get());

        Double course1M;
        Double course3M;
        Double course12M;
        Double courseYTD;
        Double courseCurrent;
        Double wynik;
        String stopa;

        // ustalanie parametrów dla URL
        String url = "https://finance.yahoo.com/quote/FOXA/history?p=FOXA";
        String endDate = "";
        Long startDate = 0L;
        RestTemplate restTemplate = new RestTemplate();
        String values = restTemplate.getForObject(url, String.class);
        String[] lines = values.split("<>");
        String[] linia;
        List<String> nowaL = new ArrayList<>();
        for (String st : lines) {
            if (st.contains("root.App.now")) {
                linia = st.split("root.App.now");
                for (String znak : linia) {
                    if (znak.startsWith(" = ")) {
                        endDate = znak.substring(3, znak.indexOf(";") - 3);
                    }
                }
            }
        }
        Long rokTime = 31536000L + 5 * 86400L;
        startDate = Long.parseLong(endDate) - rokTime;

        LocalDate localDateNow = LocalDate.now();
        LocalDate localDateFind = LocalDate.now();

        List<String> listaDat = new ArrayList<>();

        for (AmerykaSpolka amSp : staraListaSpolek) {

            course1M = null;
            course3M = null;
            course12M = null;
            courseYTD = null;
            courseCurrent = null;
            wynik = null;
            stopa = null;

            // Tworzenie nowej spólki  - konstruktor (String ticker, String name, String market, String sector, String industry, String note)
            // z danych z bazy, które nie ulegają zmianie.
            AmerykaSpolka nowaSpolka = new AmerykaSpolka(amSp.getTicker(), amSp.getName(), amSp.getMarket(), amSp.getSector(), amSp.getIndustry(), amSp.getNote(), amSp.getWebsite());
            nowaSpolka.setIdSpolka(amSp.getIdSpolka());
            nowaSpolka.setWidok(amSp.getWidok());
            nowaSpolka.setIdWszystkieDane(amSp.getIdWszystkieDane());

            Document document = connect("https://query1.finance.yahoo.com/v7/finance/download/" + amSp.getTicker() + "?period1=" + startDate + "&period2=" + endDate + "&interval=1d&events=history");

            // Tworzenie listy dat
            localDateNow = LocalDate.now();
            listaDat.clear();
            if (document.body().getAllElements().toString().contains(localDateNow.toString())) {
                listaDat.add(localDateNow.toString());
            } else if (document.body().getAllElements().toString().contains(localDateNow.minusDays(1).toString())) {
                listaDat.add(localDateNow.minusDays(1).toString());
                localDateNow = localDateNow.minusDays(1);
            }
            if (!listaDat.isEmpty()) {
//                System.out.println("ListaDat ma date current: " + listaDat);
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
//                System.out.println("ListaDat ma datę 12M: " + listaDat);
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
//                System.out.println("ListaDat ma datę YTD: " + listaDat);
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
//                System.out.println("ListaDat ma datę 3M: " + listaDat);
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
//                System.out.println("ListaDat ma datę 1M: " + listaDat);
            }

            log.info(ANSI_YELLOW + "Czytam spółkę: " + ANSI_RESET + amSp.getTicker() + ANSI_FIOLET + "       daty: " + listaDat + ANSI_RESET);
//            log.info("https://query1.finance.yahoo.com/v7/finance/download/" + amSp.getTicker() + "?period1=" + (startTime + deltaTime - rokTime) + "&period2=" + (startTime + deltaTime) + "&interval=1d&events=history");
            log.info("https://query1.finance.yahoo.com/v7/finance/download/" + amSp.getTicker() + "?period1=" + startDate + "&period2=" + endDate + "&interval=1d&events=history");

            // wyszukiwanie wartości kursów, po dacie w pobranym pliku
            if (listaDat.size() > 0) {
                StringTokenizer st = new StringTokenizer(document.body().getAllElements().toString());
                String dane = "";
                String[] result;
                while (st.hasMoreTokens()) {
                    dane = st.nextToken();
//            System.out.println(dane);
                    if (dane.startsWith(listaDat.get(0))) {
                        result = dane.split(",");
                        if ((result[4] != null) && (!result[4].equals("null"))) {
                            courseCurrent = Double.parseDouble(result[4]);
                            nowaSpolka.setCourseCurrent(new DecimalFormat("# 0.000").format(courseCurrent).replace(",", ".").trim());
                        }
                        nowaSpolka.setDayCourseCurrent(result[0]);
//                        System.out.println("courseCurrent " + nowaSpolka.getDayCourseCurrent() + " - " + nowaSpolka.getCourseCurrent());
                    }
                    if (dane.startsWith(listaDat.get(1))) {
                        result = dane.split(",");
                        if ((result[4] != null) && (!result[4].equals("null"))) {
                            course12M = Double.parseDouble(result[4]);
                            nowaSpolka.setCourse12M(new DecimalFormat("# 0.000").format(course12M).replace(",", ".").trim());
                        }
                        nowaSpolka.setDay12M(result[0]);
//                        System.out.println("course12M " + nowaSpolka.getDay12M() + " - " + nowaSpolka.getCourse12M());
                    }
                    if (dane.startsWith(listaDat.get(2))) {
                        result = dane.split(",");
                        if ((result[4] != null) && (!result[4].equals("null"))) {
                            courseYTD = Double.parseDouble(result[4]);
                            nowaSpolka.setCourseYTD(new DecimalFormat("# 0.000").format(courseYTD).replace(",", ".").trim());
                        }
                        nowaSpolka.setDayYTD(result[0]);
//                        System.out.println("courseYTD " + nowaSpolka.getDayYTD() + " - " + nowaSpolka.getCourseYTD());
                    }
                    if (dane.startsWith(listaDat.get(3))) {
                        result = dane.split(",");
                        if ((result[4] != null) && (!result[4].equals("null"))) {
                            course3M = Double.parseDouble(result[4]);
                            nowaSpolka.setCourse3M(new DecimalFormat("# 0.000").format(course3M).replace(",", ".").trim());
                        }
                        nowaSpolka.setDay3M(result[0]);
//                        System.out.println("course3M " + nowaSpolka.getDay3M() + " - " + nowaSpolka.getCourse3M());
                    }
                    if (dane.startsWith(listaDat.get(4))) {
                        result = dane.split(",");
                        if ((result[4] != null) && (!result[4].equals("null"))) {
                            course1M = Double.parseDouble(result[4]);
                            nowaSpolka.setCourse1M(new DecimalFormat("# 0.000").format(course1M).replace(",", ".").trim());
                        }
                        nowaSpolka.setDay1M(result[0]);
//                        System.out.println("course1M " + nowaSpolka.getDay1M() + " - " + nowaSpolka.getCourse1M());
                    }
                }

                // wyliczanie stóp zwrotu dla listy dat
                if ((courseCurrent != null) && (!courseCurrent.equals("null"))) {
                    if (course1M != null) {
                        wynik = ((courseCurrent / course1M) - 1) * 100;
                        stopa = new DecimalFormat("0.0").format(wynik).replace(",", ".");
                        nowaSpolka.setM1(stopa + "%");
//                        System.out.println("M1: " + nowaSpolka.getM1());
                    }
                    if (course3M != null) {
                        wynik = ((courseCurrent / course3M) - 1) * 100;
                        stopa = new DecimalFormat("0.0").format(wynik).replace(",", ".");
                        nowaSpolka.setM3(stopa + "%");
//                        System.out.println("M3: " + nowaSpolka.getM3());
                    }
                    if (course12M != null) {
                        wynik = ((courseCurrent / course12M) - 1) * 100;
                        stopa = new DecimalFormat("0.0").format(wynik).replace(",", ".");
                        nowaSpolka.setM12(stopa + "%");
//                        System.out.println("M12: " + nowaSpolka.getM12());
                    }
                    if (courseYTD != null) {
                        wynik = ((courseCurrent / courseYTD) - 1) * 100;
                        stopa = new DecimalFormat("0.0").format(wynik).replace(",", ".");
                        nowaSpolka.setyTD(stopa + "%");
//                        System.out.println("YTD: " + nowaSpolka.getyTD());
                    }
                }
                nowaSpolka = changeNull(nowaSpolka);
                amSpRepository.save(nowaSpolka);
            } // warunek nie pustej listy dat

            int millis = new Random().nextInt(400) + 600;
//            System.out.println("Czekam: " + millis + "ms");
            Thread.sleep(millis);

        } // pętla po starej liście spółek i tworzenie nowej bazy
    }

    private static AmerykaSpolka changeNull(AmerykaSpolka nowaSpolka) {
        if (nowaSpolka.getyTD() == null) nowaSpolka.setyTD("brak");
        if (nowaSpolka.getM1() == null) nowaSpolka.setM1("brak");
        if (nowaSpolka.getM3() == null) nowaSpolka.setM3("brak");
        if (nowaSpolka.getM12() == null) nowaSpolka.setM12("brak");
        if (nowaSpolka.getDay1M() == null) nowaSpolka.setDay1M("brak");
        if (nowaSpolka.getDay3M() == null) nowaSpolka.setDay3M("brak");
        if (nowaSpolka.getDay12M() == null) nowaSpolka.setDay12M("brak");
        if (nowaSpolka.getDayYTD() == null) nowaSpolka.setDayYTD("brak");
        if (nowaSpolka.getDayCourseCurrent() == null) nowaSpolka.setDayCourseCurrent("brak");
        if (nowaSpolka.getCourse3M() == null) nowaSpolka.setCourse3M("brak");
        if (nowaSpolka.getCourse1M() == null) nowaSpolka.setCourse1M("brak");
        if (nowaSpolka.getCourseYTD() == null) nowaSpolka.setCourseYTD("brak");
        if (nowaSpolka.getCourse12M() == null) nowaSpolka.setCourse12M("brak");
        if (nowaSpolka.getCourseCurrent() == null) nowaSpolka.setCourseCurrent("brak");
        return nowaSpolka;
    }


    private static Document connect(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .ignoreHttpErrors(true)
                    .get();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (HttpStatusException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }


    //        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//            Metoda  automatycznego zapisu danych z HTTP oraz czyszczenie danych AnonymousUsera, uruchamiana zawsze przy uruchomianiu aplikacji
    @EventListener(ApplicationReadyEvent.class)
    public void get() {

        LocalDateTime startCzytaj = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 22, 20);
        LocalDateTime teraz = LocalDateTime.now();
        Duration czasOczekiwania = Duration.between(teraz, startCzytaj);
        if (czasOczekiwania.getSeconds() < 0) czasOczekiwania = Duration.between(teraz.minusDays(1), startCzytaj);
        log.info(ANSI_FIOLET + "Czytanie danych zacznie się za: " + czasOczekiwania.getSeconds() + "s" + ANSI_RESET);
        TimerTask taskCzytaj = new TimerTask() {
            @Override
            public void run() {
                try {
                    czytaj();
                } catch (InterruptedException e) {
                    log.info(ANSI_RED + "Miałem problem z uruchomieniem metody czytaj()!" + ANSI_RESET);
                    e.printStackTrace();
                }
            }
        };
        Timer timerCzytaj = new Timer();
        timerCzytaj.schedule(taskCzytaj, czasOczekiwania.getSeconds() * 1000, 86400000);

        queryRepository.findAllWszystkieDane("anonymousUser");

        startCzytaj = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 05, 15);
        teraz = LocalDateTime.now();
        czasOczekiwania = Duration.between(teraz, startCzytaj);
        if (czasOczekiwania.getSeconds() < 0) czasOczekiwania = Duration.between(teraz.minusDays(1), startCzytaj);
        log.info(ANSI_FIOLET + "Czyszczenie listy gości zacznie się za: " + czasOczekiwania.getSeconds() + "s" + ANSI_RESET);
        TimerTask taskClearGoscie = new TimerTask() {
            @Override
            public void run() {
                queryRepository.clearGoscie(LocalDate.now().minusDays(3).toString());
            }
        };
        Timer timerClear = new Timer();
        timerClear.schedule(taskClearGoscie, czasOczekiwania.getSeconds() * 1000, 86400000);
    }

}

