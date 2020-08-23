package pl.info.mojeakcje.maestroameryka.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pl.info.mojeakcje.maestroameryka.CzytanieDanychJsoup;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.Customer;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;
import pl.info.mojeakcje.maestroameryka.service.DBfromCSV;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_BLUE;
import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_RESET;

/**
 * Created by Robert Burek
 */

@RestController
public class AmSpControllerRest {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    AmSpRepository amSpRepository;

    @Autowired
    CzytanieDanychJsoup czytanieDanychJsoup;

    @Autowired
    DBfromCSV dBfromCSV;

    @Autowired
    QueryRepository queryRepository;

    @Autowired
    CustoRepository custoRepository;

    //    @Value("${data.folder.csv}")
    @Value("${data.folder.csv}")
    String myPath;


    @GetMapping("/odczytdanychzplikucsv/{nameFile}") //ceny miesięczne w formacie string
    public String setDataToBase(@PathVariable String nameFile) {
        log.info("NameFile " + ANSI_BLUE + nameFile + ANSI_RESET);
        nameFile = "AmerykaSpolki.csv";
        return "Dane wczytane z kliku " + myPath + nameFile;
    }

    @GetMapping("/danezbazy")
    public Iterable<AmerykaSpolka> getDatabase() {
        log.info(ANSI_BLUE + "Wypisałem wszystkie dane z bazy MaestroAmeryka z tabeli: ameryka_spolka" + ANSI_RESET);
        return amSpRepository.findAll();
    }


    @GetMapping("/danezbazy/{ticker}")
    public AmerykaSpolka getSpolkaTicker(@PathVariable String ticker) {
        log.info(ANSI_BLUE + "Dane z bazy MaestroAmeryka z tabeli: ameryka_spolka, spolka: " + ticker.toUpperCase() + ANSI_RESET);
        return ((List<AmerykaSpolka>) amSpRepository.findAll())
                .stream()
                .filter(amerykaSpolka -> amerykaSpolka.getTicker().equals(ticker.toUpperCase()))
                .findAny().orElse(new AmerykaSpolka());
    }

    @RequestMapping("/usunSpolke/{id}&{ticker}")
    public AmerykaSpolka deleteSpolkaInDB(@PathVariable Long id, @PathVariable String ticker) {
        AmerykaSpolka amerykaSpolka = amSpRepository.findById(id).get();
        if (amerykaSpolka.getTicker().equals(ticker.toUpperCase())) {
            log.info("To właściwa spółka: " + ticker.toUpperCase() + " do usunięcia!!! ");
            queryRepository.del_ameryka_spolka(id);
            List<Customer> Customers = (List<Customer>) custoRepository.findAll();
            for (Customer customer : Customers) {
                if (!customer.getNickCustomer().equals("")) {
                    queryRepository.delView(customer.getNickCustomer());
                    queryRepository.setView(customer.getNickCustomer(), customer.getId());
                    log.info("Zmieniono widok dla użytkownika: " + customer.getNickCustomer());
                }
            }
        } else
            log.info("Nie ta spółka !!!!  Id nie zgadza się z ticker. " + amerykaSpolka.getTicker() + " != " + ticker.toUpperCase());
        return amerykaSpolka;
    }


    @RequestMapping("/naprawWidoki")
    public String naprawWidoki() {
        List<Customer> Customers = (List<Customer>) custoRepository.findAll();
        for (Customer customer : Customers) {
            if (!customer.getNickCustomer().equals("")) {
                try {
                    queryRepository.delView(customer.getNickCustomer());
                } catch (Exception ex){
                    log.info("Nie usunąłem widoku " + customer.getNickCustomer());
                }
                queryRepository.setView(customer.getNickCustomer(), customer.getId());
                log.info("Zmieniono widok dla użytkownika: " + customer.getNickCustomer());
            }
        }
        return "Naprawiłem widoki użytkowników!!!";
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("plik") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                UUID uuid = UUID.randomUUID();
                String filename = "/uploads/upload_" + uuid.toString();
                byte[] bytes = file.getBytes();
                File fsFile = new File(filename);
                fsFile.createNewFile();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fsFile));
                stream.write(bytes);
                stream.close();
                log.info("File {} has been successfully uploaded as {}", new Object[]{file.getOriginalFilename(), filename});
            } catch (Exception e) {
                log.error("File has not been uploaded", e);
            }
        } else {
            log.error("Uploaded file is empty");
        }
        return "redirect:/";
    }


    @GetMapping("/napraw") //na podstawie tabeli ameryka_spolka tworzy tabelę strategie
    public String napraw() {
        List<AmerykaSpolka> zbior = (List<AmerykaSpolka>) amSpRepository.findAll();
        zbior.stream().forEach(new Consumer<AmerykaSpolka>() {
            @Override
            public void accept(AmerykaSpolka amerykaSpolka) {
                amerykaSpolka.setCourse3M(amerykaSpolka.getCourse3M().replace(",", "."));
                amerykaSpolka.setCourse1M(amerykaSpolka.getCourse1M().replace(",", "."));
                amerykaSpolka.setCourse12M(amerykaSpolka.getCourse12M().replace(",", "."));
                amerykaSpolka.setCourseYTD(amerykaSpolka.getCourseYTD().replace(",", "."));
                amerykaSpolka.setCourseCurrent(amerykaSpolka.getCourseCurrent().replace(",", "."));
                amerykaSpolka.setM3(amerykaSpolka.getM3().replace(",", "."));
                amerykaSpolka.setM1(amerykaSpolka.getM1().replace(",", "."));
                amerykaSpolka.setM12(amerykaSpolka.getM12().replace(",", "."));
                amerykaSpolka.setyTD(amerykaSpolka.getyTD().replace(",", "."));
//                amSpRepository.save(amerykaSpolka);    zakomentowane na wszelki wypadek
            }
        });
        log.info(ANSI_BLUE + "Naprawiłem!!!" + ANSI_RESET);
        return " Zrobiłem !!! Odczyt danych z pliku pobranego z HTTP !!!";
    }


    @GetMapping("/czytajDane") //czyta dane z URLi
    public String createTableWithHttp() throws InterruptedException {
        log.info(ANSI_BLUE + " Ręcznie uruchomiono czytanie danych!!!" + ANSI_RESET);
        czytanieDanychJsoup.czytaj();
        return " Zrobiłem !!! Odczyt danych z pliku pobranego z HTTP !!!";
    }


    @GetMapping("/czytajWebsite")
    public String czytajWebsite() throws InterruptedException {
        AmerykaSpolka amerykaSpolka;
        for (long i = 1; i < amSpRepository.count() + 1; i++) {
            amerykaSpolka = amSpRepository.findById(i).get();
            String url = "https://finance.yahoo.com/quote/" + amerykaSpolka.getTicker() + "/profile?p=" + amerykaSpolka.getTicker();
            RestTemplate restTemplate = new RestTemplate();
            String values = restTemplate.getForObject(url, String.class);
            String odpowiedniaLinia = values.substring(values.lastIndexOf("website") + 10, values.lastIndexOf("website") + 200);
            String webSite = "";
            if (odpowiedniaLinia.startsWith("http")) {
                webSite = odpowiedniaLinia.substring(0, odpowiedniaLinia.indexOf("maxAge") - 3).replace("u002F", "").replace("\\\\", "//");
            } else {
                webSite = "#brak";
            }
            amerykaSpolka.setWebsite(webSite);
            System.out.println(amerykaSpolka.getIdSpolka() + "  " + webSite);
//            amSpRepository.save(amerykaSpolka);    zakomentowane na wszelki wypadek
            int millis = new Random().nextInt(200);
            Thread.sleep(millis);
        }
        return "Zrobine!!!";
    }

}
