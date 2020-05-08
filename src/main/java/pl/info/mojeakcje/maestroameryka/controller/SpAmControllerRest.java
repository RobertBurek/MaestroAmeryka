package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.info.mojeakcje.maestroameryka.CzytanieDanychJsoup;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.SpolkaAmeryka;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.SpAmRepository;
import pl.info.mojeakcje.maestroameryka.service.DBfromCSV;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_BLUE;
import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_RESET;

/**
 * Created by Robert Burek
 */

@Log4j2
@RestController
public class SpAmControllerRest {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    SpAmRepository spAmRepository;

    @Autowired
    AmSpRepository amSpRepository;

    @Autowired
    CzytanieDanychJsoup czytanieDanychJsoup;

    @Autowired
    DBfromCSV dBfromCSV;

    @Value("${data.folder.csv}")
    String myPath;




    @GetMapping("/dane")
    public Iterable<SpolkaAmeryka> getData() {
        log.info(ANSI_BLUE + "Wypisałem wszystkie dane z bazy MaestroAmeryka z tabeli: spolka_ameryka" + ANSI_RESET);
        return spAmRepository.findAll();
    }

    @GetMapping("/czytaj") //na podstawie tabeli ameryka_spolka tworzy tabelę strategie
    public String createTableWithHttp() throws IOException {
        List<String> listaTicker = new ArrayList<>();
        ((List<AmerykaSpolka>)amSpRepository.findAll()).stream().map(amerykaSpolka -> listaTicker.add(amerykaSpolka.getTicker())).collect(Collectors.toList());
        List<AmerykaSpolka> staraListaSpolek = new ArrayList<>();
                staraListaSpolek.add(amSpRepository.findById(1L).get());
                staraListaSpolek.add(amSpRepository.findById(2L).get());
//                staraListaSpolek.add(amSpRepository.findById(3L).get());
//                staraListaSpolek.add(amSpRepository.findById(4L).get());
//                staraListaSpolek.add(amSpRepository.findById(5L).get());
//                staraListaSpolek.add(amSpRepository.findById(6L).get());
//                staraListaSpolek.add(amSpRepository.findById(7L).get());
//                staraListaSpolek.add(amSpRepository.findById(8L).get());
//                staraListaSpolek.add(amSpRepository.findById(9L).get());
//                staraListaSpolek.add(amSpRepository.findById(10L).get());
//        czytanieDanychJsoup.run(listaTicker);
        List<SpolkaAmeryka> nowaListaSpolek = czytanieDanychJsoup.run(staraListaSpolek);
        log.info(ANSI_BLUE + " Zrobiłem !!! Odczyt danych z pliku pobranego z HTTP !!!");
//        return "Tabela uzupełniona";
        return nowaListaSpolek.toString();
    }


    @GetMapping("/dane/{ticker}")
    public SpolkaAmeryka getSpolkaTicker(@PathVariable String ticker) {
        log.info(ANSI_BLUE + "Dane z bazy MaestroAmeryka z tabeli: spolka_ameryka, spolka: " + ticker.toUpperCase() + ANSI_RESET);
        return ((List<SpolkaAmeryka>) spAmRepository.findAll())
                .stream()
                .filter(spolkaAmeryka -> spolkaAmeryka.getTicker().equals(ticker.toUpperCase()))
                .findAny().orElse(new SpolkaAmeryka());
    }



}
