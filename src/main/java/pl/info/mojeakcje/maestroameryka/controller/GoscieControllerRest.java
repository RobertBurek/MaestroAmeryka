package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_RED;
import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_RESET;

@Log4j2
@RestController
public class GoscieControllerRest {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @PutMapping("info/{infoKto}&{infoMiasto}&{infoKraj}")
    public void infoPage(@PathVariable String infoKto, @PathVariable String infoMiasto, @PathVariable String infoKraj) {
        log.info(ANSI_RED + "infoKto: " + infoKto + ANSI_RESET);
        log.info(ANSI_RED + "infoMiasto: " + infoMiasto + ANSI_RESET);
        log.info(ANSI_RED + "infoKraj: " + infoKraj + ANSI_RESET);
    }
}
