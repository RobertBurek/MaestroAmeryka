package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.info.mojeakcje.maestroameryka.service.DBfromCSV;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

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
    DBfromCSV dBfromCSV;

//    @Value("${data.folder.csv}")
    @Value("${data.folder.csv}")
    String myPath;


    @GetMapping("/odczytdanychzplikucsv/{nameFile}") //ceny miesięczne w formacie string
    public String setDataToBase(@PathVariable String nameFile) {
        log.info("NameFile " + ANSI_BLUE + nameFile + ANSI_RESET);
        nameFile = "AmerykaSpolki.csv";
//        Iterable<AmerykaSpolka> amerykaSp = dBfromCSV.readFromFile(myPath + nameFile);
//        amerykaSp.forEach(amerykaSpolka -> {
//            log.info(amerykaSpolka.getTicker() + " -  " + ANSI_BLUE + amerykaSpolka.getName() + ANSI_RESET);
//            amSpRepository.save(amerykaSpolka);
//        });
        return "Dane wczytane z kliku " + myPath + nameFile;
    }

    @GetMapping("/danezbazy")
    public Iterable<AmerykaSpolka> getDatabase() {
        log.info(ANSI_BLUE + "Wypisałem wszystkie dane z bazy MaestroAmeryka z tabeli: ameryka_spolka" + ANSI_RESET);
        return amSpRepository.findAll();
    }

//    @GetMapping("/tworzenietabelistrategia") //na podstawie tabeli ameryka_spolka tworzy tabelę strategie
//    public String createTableStrategy() {
//        amerykaSpolkiStretegie = ((List<AmerykaSpolka>) amSpRepository.findAll())
//                .stream()
//                .map(amerykaSpolka -> getAmerykaSpolkaStrategia(amerykaSpolka)
//                ).collect(Collectors.toList());
//        amSpStrategyRepository.saveAll(amerykaSpolkiStretegie);
//        log.info(ANSI_BLUE + " Zrobiłem !!! Mapowanie tabeli notowań na tabelę Strategie !!!");
//        return "Tabela Strategia zrobiona !!!";
//    }


//    @GetMapping("/")
//    public Iterable<AmerykaSpolka> getAll() {
//        log.info(ANSI_BLUE + "Wypisałem wszystkie dane z bazy MaestroAmeryka z tabeli: ameryka_spolka" + ANSI_RESET);
//        return amSpRepository.findAll();
//    }

    @GetMapping("/danezbazy/{ticker}")
    public AmerykaSpolka getSpolkaTicker(@PathVariable String ticker) {
        log.info(ANSI_BLUE + "Dane z bazy MaestroAmeryka z tabeli: ameryka_spolka, spolka: " + ticker.toUpperCase() + ANSI_RESET);
        return ((List<AmerykaSpolka>) amSpRepository.findAll())
                .stream()
                .filter(amerykaSpolka -> amerykaSpolka.getTicker().equals(ticker.toUpperCase()))
                .findAny().orElse(new AmerykaSpolka());
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("plik") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                UUID uuid = UUID.randomUUID();
                String filename = "/uploads/upload_"+uuid.toString();
                byte[] bytes = file.getBytes();
                File fsFile = new File(filename);
                fsFile.createNewFile();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fsFile));
                stream.write(bytes);
                stream.close();
                log.info("File {} has been successfully uploaded as {}", new Object[] {file.getOriginalFilename(), filename});
            } catch (Exception e) {
                log.error("File has not been uploaded", e);
            }
        } else {
            log.error("Uploaded file is empty");
        }
        return "redirect:/";
    }

}
