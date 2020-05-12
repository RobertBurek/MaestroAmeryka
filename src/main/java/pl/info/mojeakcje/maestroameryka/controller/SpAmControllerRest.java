package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.info.mojeakcje.maestroameryka.CzytanieDanychJsoup;

import java.io.IOException;

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
    CzytanieDanychJsoup czytanieDanychJsoup;


    @GetMapping("/czytajdane") //czyta dane z URL
    public String createTableWithHttp() throws InterruptedException {
        czytanieDanychJsoup.czytaj();
        log.info(ANSI_BLUE + " Ręcznie uruchomiono czytanie danych!!!" + ANSI_RESET);
        return " Zrobiłem !!! Odczyt danych z pliku pobranego z HTTP !!!";
    }



}
