package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

/**
 * Created by Robert Burek
 */

@Log4j2
@Controller
public class AmSpController {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    AmSpRepository amSpRepository;

    public AmSpController(AmSpRepository amSpRepository) {
        this.amSpRepository = amSpRepository;
    }

//    public AmSpController(AmSpRepository amSpRepository) {
//        this.amSpRepository = amSpRepository;
//    }

//    @GetMapping("/loginOpenID")
//    public String getLoginOpenID() {
//        return "redirect:/amerykawidok";
//    }

//    @GetMapping("/loginMaestro/error")
//    public String loginError(@RequestParam String error, Model model) {
//        log.info("Jestem w loginMaestro.html: " + ANSI_RED + error + ANSI_RESET);
//        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
//        return "loginMaestro";
//    }

    @GetMapping("/loginMaestro")
    public String login(Model model) {
        log.info(ANSI_RED + "Formularz logowania, loginMaestro.html" + ANSI_RESET);
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "loginMaestro";
    }

    @GetMapping("/")
    public String getAll(Model model) {
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy ..." + ANSI_RESET);
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykawidok";
    }

    @GetMapping("/amerykaspolka")
    public String getStart(Model model) {
//        amerykaSpolkiStretegie = (List<AmerykaSpolkaStrategia>) amSpStrategyRepository.findAll();
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
//        amerykaSpolki.stream().map(amerykaSpolka -> Double.parseDouble(amerykaSpolka.getDay0119()))
        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy ..." + ANSI_RESET);
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykawidok";
    }

    //
    @GetMapping("/amerykaspolka/find")
    public String getAllFind(Model model) {
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykawidok";
    }

    @PostMapping("/amerykaspolka")
    public String postFindAmerykaSpolka(@ModelAttribute AmerykaSpolka amerykaSpolkaFind) {
        log.info("Szukać będziemy: " + ANSI_FIOLET + amerykaSpolkaFind.getTicker().toUpperCase() + ANSI_RESET);
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        if (!amerykaSpolkaFind.getTicker().equals("")) {
            amerykaSpolki = amerykaSpolki.stream()
                    .filter(amerykaSpolka -> (amerykaSpolka.getTicker()).contains(amerykaSpolkaFind.getTicker().toUpperCase()))
                    .collect(Collectors.toList());
        }
        log.info("Lista znalezionych spółek: " + ANSI_YELLOW + amerykaSpolki.stream()
                .map(amerykaSpolka -> amerykaSpolka.getTicker() + ", ")
                .collect(Collectors.joining()) + ANSI_RESET);
        return "redirect:/amerykaspolka/find";
    }

    //
//    @PostMapping("/amerykaspolka/save")
//    public String saveAmerykaSpolka(@ModelAttribute AmerykaSpolka amerykaspolkaNew) {
//        amSpRepository.save(amerykaspolkaNew);
//        log.info("Nowe dane spółki:  " + ANSI_RED + modifiedAmerykaSpolka.getName() + " (" + modifiedAmerykaSpolka.getTicker() + ")" + ANSI_RESET);
//        log.info(amerykaspolkaNew.toString());
//        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
//        return "redirect:/amerykaspolka/find#position";
//    }
//
    @GetMapping("/amerykaspolka/del")
    public String delAmerykaSpolka(@RequestParam Long index, Model model) {
        AmerykaSpolka deleteAmerykaSpolka = amSpRepository.findById(index.longValue()).get();
        log.info("Spółka do usunięcia: " + ANSI_RED + deleteAmerykaSpolka.getName() + " (" + deleteAmerykaSpolka.getTicker() + ")" + ANSI_RESET);
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkadelete", deleteAmerykaSpolka);
        return "amerykaspolkadelete";
    }

    @PostMapping("/amerykaspolka/delete")
    public String deleteAmerykaSpolka(@ModelAttribute AmerykaSpolka deleteAmerykaSpolka) {
        log.info("Usunięto spółkę: " + ANSI_RED + deleteAmerykaSpolka.getName() + " (" + deleteAmerykaSpolka.getTicker() + ")" + ANSI_RESET);
        amSpRepository.deleteById(deleteAmerykaSpolka.getId());
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        return "redirect:/amerykaspolka";
    }

    @GetMapping("/amerykaspolka/edit")
    public String editAmerykaSpolka(@RequestParam Long index, Model model) {
        AmerykaSpolka modifiedAmerykaSpolka = amSpRepository.findById(index.longValue()).get();
        log.info("Spółka do modyfikacji: " + ANSI_RED + modifiedAmerykaSpolka.getName() + " (" + modifiedAmerykaSpolka.getTicker() + ")" + ANSI_RESET);
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaModified", modifiedAmerykaSpolka);
        return "amerykaspolkaedit";
    }

    @PostMapping("/amerykaspolka/edit")
    public String findAmerykaSpolka(@ModelAttribute AmerykaSpolka amerykaSpolkaFind, Model model) {
        log.info("Szukać będziemy: " + amerykaSpolkaFind.getTicker());
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        List<AmerykaSpolka> newAmerykaSpolki;
        if (!amerykaSpolkaFind.getTicker().equals("")) {
            newAmerykaSpolki = amerykaSpolki.stream()
                    .filter(amerykaSpolka -> (amerykaSpolka.getTicker()).contains(amerykaSpolkaFind.getTicker().toUpperCase()))
                    .collect(Collectors.toList());
        } else {
            newAmerykaSpolki = amerykaSpolki;
        }
        log.info("Lista znalezionych spółek: " + newAmerykaSpolki.toString());
        model.addAttribute("amerykaSpolki", newAmerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", amerykaSpolkaFind);
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykaspolkaedit";
    }

    @PostMapping("/amerykaspolka/save/edit")
    public String saveNameDayEdit(@ModelAttribute AmerykaSpolka modifiedAmerykaSpolka) {
        amSpRepository.save(modifiedAmerykaSpolka);
        log.info("Zapisano nowe dane dla spólki: " + ANSI_YELLOW + modifiedAmerykaSpolka.getName() + " (" + modifiedAmerykaSpolka.getTicker() + ")" + ANSI_RESET);
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        return "redirect:/amerykaspolka/edit?index=" + modifiedAmerykaSpolka.getId();
    }

}
