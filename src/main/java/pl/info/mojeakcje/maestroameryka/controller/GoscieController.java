package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_RED;
import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_RESET;

@Log4j2
@Controller
public class GoscieController {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/info/{infoKto}&{infoMiasto}&{infoKraj}")
    public void infoPage(@PathVariable String infoKto, @PathVariable String infoMiasto, @PathVariable String infoKraj, Model model) {
        log.info(ANSI_RED + "Jestem w info. " + ANSI_RESET);
//        return "amerykawidok";
    }
}
