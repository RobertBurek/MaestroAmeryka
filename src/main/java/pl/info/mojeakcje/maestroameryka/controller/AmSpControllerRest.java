package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.info.mojeakcje.maestroameryka.DBfromCSV;
import pl.info.mojeakcje.maestroameryka.DBfromCSV_D;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaD;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaStrategia;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepositoryD;
import pl.info.mojeakcje.maestroameryka.repository.AmSpStrategyRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

/**
 * Created by Robert Burek
 */

@Log4j2
@RestController
public class AmSpControllerRest {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    AmSpRepository amSpRepository;
    @Autowired
    AmSpRepositoryD amSpRepositoryD;
    @Autowired
    AmSpStrategyRepository amSpStrategyRepository;
    @Autowired
    DBfromCSV dBfromCSV;
    @Autowired
    DBfromCSV_D dBfromCSVD;

    @Value("${data.folder.csv}")
    String myPath;


    @GetMapping("/odczytdanychzplikucsv/{nameFile}") //ceny miesięczne w formacie string
    public String setDataToBase(@PathVariable String nameFile) {
        log.info("NameFile " + ANSI_BLUE + nameFile + ANSI_RESET);
//        nameFile = "AmerykaSpolki.csv";
        Iterable<AmerykaSpolka> amerykaSp = dBfromCSV.readFromFile(myPath + nameFile);
        amerykaSp.forEach(amerykaSpolka -> {
            log.info(amerykaSpolka.getTicker() + " -  " + ANSI_BLUE + amerykaSpolka.getName() + ANSI_RESET);
            amSpRepository.save(amerykaSpolka);
        });
        return "Dane wczytane z kliku " + myPath + nameFile;
    }

    @GetMapping("/danezbazy")
    public Iterable<AmerykaSpolka> getDatabase() {
        log.info(ANSI_BLUE + "Wypisałem wszystkie dane z bazy MaestroAmeryka z tabeli: ameryka_spolka" + ANSI_RESET);
        return amSpRepository.findAll();
    }

    @GetMapping("/odczytdanychzcsv/{nameFile}") //ceny miesięczne w formacie double
    public String setDataToBaseD(@PathVariable String nameFile) {
        log.info("NameFile " + ANSI_BLUE + nameFile + ANSI_RESET);
        nameFile = "AmerykaSpolkiD.csv";
        Iterable<AmerykaSpolkaD> amerykaSpD = dBfromCSVD.readFromFile(myPath + nameFile);
        amerykaSpD.forEach(amerykaSpolkaD -> {
            log.info(amerykaSpolkaD.getTicker() + " -  " + ANSI_BLUE + amerykaSpolkaD.getName() + ANSI_RESET);
            amSpRepositoryD.save(amerykaSpolkaD);
        });
        return "Dane wczytane z kliku " + myPath + nameFile;
    }

    @GetMapping("/tworzenietabelistrategia") //na podstawie tabeli ameryka_spolka tworzy tabelę strategie
    public String createTableStrategy() {
        amerykaSpolkiStretegie = ((List<AmerykaSpolka>) amSpRepository.findAll())
                .stream()
                .map(amerykaSpolka -> getAmerykaSpolkaStrategia(amerykaSpolka)
                ).collect(Collectors.toList());
        amSpStrategyRepository.saveAll(amerykaSpolkiStretegie);
        log.info(ANSI_BLUE + " Zrobiłem !!! Mapowanie tabeli notowań na tabelę Strategie !!!");
        return "Tabela Strategia zrobiona !!!";
    }




//    @GetMapping("/")
//    public Iterable<AmerykaSpolka> getAll() {
//        log.info(ANSI_BLUE + "Wypisałem wszystkie dane z bazy MaestroAmeryka z tabeli: ameryka_spolka" + ANSI_RESET);
//        return amSpRepository.findAll();
//    }

    @GetMapping("/danezbazy/{ticker}")
    public AmerykaSpolka getSpolkaTicker(@PathVariable String ticker) {
        log.info(ANSI_BLUE + "Dane z bazy MaestroAmeryka z tabeli: ameryka_spolka, spolka: " + ticker.toUpperCase() + ANSI_RESET);
        return ((List<AmerykaSpolka>)amSpRepository.findAll())
                .stream()
                .filter(amerykaSpolka -> amerykaSpolka.getTicker().equals(ticker.toUpperCase()))
                .findAny().orElse(new AmerykaSpolka());
    }

    private AmerykaSpolkaStrategia getAmerykaSpolkaStrategia(AmerykaSpolka amerykaSpolka) {
        AmerykaSpolkaStrategia amerykaSpolkaStrategiaNew = new AmerykaSpolkaStrategia(amerykaSpolka.getTicker(), amerykaSpolka.getName(), amerykaSpolka.getMarket(), amerykaSpolka.getSector(), amerykaSpolka.getIndustry());
        amerykaSpolkaStrategiaNew.setLastCourse(amerykaSpolka.getDay0320());
        Double endYTD = 0.0;
        Double startYTD = 0.0;
        Double start1MTD = 0.0;
        Double start2MTD = 0.0;
        if (!amerykaSpolka.getDay0320().replaceAll(" ", "").equals("null"))
            endYTD = Double.parseDouble(amerykaSpolka.getDay0320().replaceAll(" ", ""));
        if (!amerykaSpolka.getDay0319().replaceAll(" ", "").equals("null"))
            startYTD = Double.parseDouble(amerykaSpolka.getDay0319().replaceAll(" ", ""));
        if (!amerykaSpolka.getDay0220().replaceAll(" ", "").equals("null"))
            start1MTD = Double.parseDouble(amerykaSpolka.getDay0220().replaceAll(" ", ""));
        if (!amerykaSpolka.getDay0120().replaceAll(" ", "").equals("null"))
            start2MTD = Double.parseDouble(amerykaSpolka.getDay0120().replaceAll(" ", ""));
        Double yTD = 0.0;
        Double m1TD = 0.0;
        Double m2TD = 0.0;
        if (startYTD != 0.0) {
            yTD = (endYTD - startYTD) * 100 / startYTD;
            amerykaSpolkaStrategiaNew.setyTD(String.format("%.1f", yTD));
        } else amerykaSpolkaStrategiaNew.setyTD("brak");
        if (start1MTD != 0.0) {
            m1TD = (endYTD - start1MTD) * 100 / start1MTD;
            amerykaSpolkaStrategiaNew.setM1TD(String.format("%.1f", m1TD));
        } else amerykaSpolkaStrategiaNew.setM1TD("brak");
        if (start2MTD != 0.0) {
            m2TD = (endYTD - start2MTD) * 100 / start2MTD;
            amerykaSpolkaStrategiaNew.setM2TD(String.format("%.1f", m2TD));
        } else amerykaSpolkaStrategiaNew.setM2TD("brak");
        log.info(ANSI_BLUE + amerykaSpolkaStrategiaNew.getTicker() + ANSI_YELLOW + "  " + amerykaSpolkaStrategiaNew.getyTD()
                + "  " + amerykaSpolkaStrategiaNew.getM1TD() + " " + amerykaSpolkaStrategiaNew.getM2TD() + ANSI_RESET);
        return amerykaSpolkaStrategiaNew;
    }

}
