package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.info.mojeakcje.maestroameryka.model.modelGoscie.Goscie;
import pl.info.mojeakcje.maestroameryka.repository.GoscieRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

@Log4j2
@RestController
public class GoscieControllerRest {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    GoscieRepository goscieRepository;

    public GoscieControllerRest(GoscieRepository goscieRepository) {
        this.goscieRepository = goscieRepository;
    }

    @PutMapping("info/{infoKto}&{infoMiasto}&{infoKraj}")
    public void infoPage(@PathVariable String infoKto, @PathVariable String infoMiasto, @PathVariable String infoKraj) {
        log.info(ANSI_YELLOW + "" + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + ", " + infoKto + ", " + infoMiasto + ", " + infoKraj + ANSI_RESET);
        goscieRepository.save(new Goscie(LocalDate.now(), LocalTime.now(), infoKto, infoMiasto, infoKraj));
    }
}
