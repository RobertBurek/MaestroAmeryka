package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.ShowSpolka;
import pl.info.mojeakcje.maestroameryka.model.WszystkieDane;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CurrentUser;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;
import pl.info.mojeakcje.maestroameryka.repository.WszysDaneRepository;
import pl.info.mojeakcje.maestroameryka.service.ClearAnonymous;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

/**
 * Created by Robert Burek
 */

@Log4j2
@Controller
public class AmSpController {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    CurrentUser currentUser;
    AmSpRepository amSpRepository;
    QueryRepository queryRepository;
    ShowSpolka showSpolka;
    WszysDaneRepository wszysDaneRepository;
    ClearAnonymous clearAnonymous;

    public AmSpController(CurrentUser currentUser, AmSpRepository amSpRepository, QueryRepository queryRepository, ShowSpolka showSpolka, WszysDaneRepository wszysDaneRepository, ClearAnonymous clearAnonymous) {
        this.currentUser = currentUser;
        this.amSpRepository = amSpRepository;
        this.queryRepository = queryRepository;
        this.showSpolka = showSpolka;
        this.wszysDaneRepository = wszysDaneRepository;
        this.clearAnonymous = clearAnonymous;
    }


    @GetMapping("/")
    public String getAllView(Model model) {
        queryRepository.findAllWszystkieDane(currentUser.currentUserName());
        if (!showSpolka.show) queryRepository.showWD();
        amerykaSpolki = amerykaSpolki.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy, endpoint (/), użytkownik: " + currentUser.currentUserName() + ANSI_RESET);
        model.addAttribute("showAll", showSpolka.getShow());
        model.addAttribute("currentUserName", currentUser.currentUserName());
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykawidok";
    }


    @GetMapping("/amerykaspolka")
    public String getStart(Model model) {
        clearAnonymous.clearShowAnonymousUser(0L);
        queryRepository.findAllWszystkieDane(currentUser.currentUserName());
        if (!showSpolka.show) queryRepository.showWD();
        amerykaSpolki = amerykaSpolki.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy, endpoint (/amerykaspolka), użytkownik: " + currentUser.currentUserName() + ANSI_RESET);
        model.addAttribute("showAll", showSpolka.getShow());
        model.addAttribute("currentUserName", currentUser.currentUserName());
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykawidok";
    }


    @GetMapping("/showMineOrAll")
    public String setShow() {
        if (showSpolka.getShow()) showSpolka.setShow(false);
        else showSpolka.setShow(true);
        return "redirect:/";
    }


    @GetMapping("/amerykaspolka/show/{id}&{widok}")
    public String chengeShow(@PathVariable Long id, @PathVariable Boolean widok, Model model) {
        if ((currentUser.currentUserName().equals("anonymousUser")) && (!isDelay))
            clearAnonymous.clearShowAnonymousUser(20L);
        String position = "amerykawidok::#position" + id;
        AmerykaSpolka amerykaSpolka = amerykaSpolki.stream().filter(amSp -> amSp.getIdSpolka().equals(id)).findFirst().get();
        WszystkieDane wszystkieDane = wszysDaneRepository.findById(amerykaSpolka.getIdWszystkieDane()).get();
        if (widok) {
            wszystkieDane.setWidoczny(false);
            amerykaSpolka.setWidok(false);
        } else {
            wszystkieDane.setWidoczny(true);
            amerykaSpolka.setWidok(true);
        }
        wszysDaneRepository.save(wszystkieDane);
        log.info("Zmieniono widoczność spółki: " + amerykaSpolka.getName() + " na: " + wszystkieDane.getWidoczny() + "  - " + currentUser.currentUserName());
        model.addAttribute("amerykaSpolka", amerykaSpolka);
        return position;
    }


    @GetMapping("/amerykaspolka/find")
    public String getAllFind(Model model) {
        model.addAttribute("showAll", showSpolka.getShow());
        model.addAttribute("currentUserName", currentUser.currentUserName());
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykawidok";
    }


    @PostMapping("/amerykaspolka/find")
    public String postFindAmerykaSpolka(@ModelAttribute AmerykaSpolka amerykaSpolkaFind) {
        log.info("Szukać będziemy: " + ANSI_FIOLET + amerykaSpolkaFind.getTicker().toUpperCase() + ANSI_RESET+" , w grupie: "+amerykaSpolkaFind.getIdWszystkieDane());
        amerykaSpolki = queryRepository.findAllWszystkieDane(currentUser.currentUserName());
        if (!amerykaSpolkaFind.getTicker().equals("")) {
            amerykaSpolki = amerykaSpolki.stream()
//                    .filter(amerykaSpolka -> (amerykaSpolka.getTicker()).contains(amerykaSpolkaFind.getTicker().toUpperCase()))
//                    .filter(amerykaSpolka -> (amerykaSpolka.getName().toUpperCase()).contains(amerykaSpolkaFind.getTicker().toUpperCase()))
                    .filter(new Predicate<AmerykaSpolka>() {
                        @Override
                        public boolean test(AmerykaSpolka amerykaSpolka) {
                            if (amerykaSpolkaFind.getIdWszystkieDane()==1)
                                return (amerykaSpolka.getName().toUpperCase()).contains(amerykaSpolkaFind.getTicker().toUpperCase());
                            if (amerykaSpolkaFind.getIdWszystkieDane()==2)
                                return (amerykaSpolka.getTicker()).contains(amerykaSpolkaFind.getTicker().toUpperCase());
                            if (amerykaSpolkaFind.getIdWszystkieDane()==4)
                                return (amerykaSpolka.getIndustry().toUpperCase()).contains(amerykaSpolkaFind.getTicker().toUpperCase());
                            if (amerykaSpolkaFind.getIdWszystkieDane()==3)
                                return (amerykaSpolka.getSector().toUpperCase()).contains(amerykaSpolkaFind.getTicker().toUpperCase());
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
        }
        log.info("Lista znalezionych spółek: " + ANSI_YELLOW + amerykaSpolki.stream()
                .map(amerykaSpolka -> amerykaSpolka.getTicker() + ", ")
                .collect(Collectors.joining()) + ANSI_RESET);
        return "redirect:/amerykaspolka/find";
    }


    @GetMapping("/amerykaspolka/edit")
    public String editAmerykaSpolka(@RequestParam Long index, Model model) {
        AmerykaSpolka editAmerykaSpolka = amerykaSpolki.stream().filter(amerykaSpolka -> amerykaSpolka.getIdSpolka().equals(index)).findFirst().get();
        log.info("Spółka do modyfikacji: " + ANSI_RED + editAmerykaSpolka.getName() + " (" + editAmerykaSpolka.getTicker() + ")" + ANSI_RESET);
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaModified", editAmerykaSpolka);
        return "amerykaspolkaedit";
    }


    @PostMapping("/amerykaspolka/save/edit")
    public String saveNameDayEdit(@ModelAttribute AmerykaSpolka modifiedAmerykaSpolka) {
        if ((currentUser.currentUserName().equals("anonymousUser")) && (!isDelay))
            clearAnonymous.clearShowAnonymousUser(20L);
        WszystkieDane wszystkieDane = wszysDaneRepository.findById(modifiedAmerykaSpolka.getIdWszystkieDane()).get();
        wszystkieDane.setNotatka(modifiedAmerykaSpolka.getNote());
        wszystkieDane.setWidoczny(modifiedAmerykaSpolka.getWidok());
        wszysDaneRepository.save(wszystkieDane);
        amerykaSpolki = queryRepository.findAllWszystkieDane(currentUser.currentUserName());
        log.info("Zapisano nowe dane dla spólki: " + ANSI_YELLOW + modifiedAmerykaSpolka.getName() + " (" + modifiedAmerykaSpolka.getTicker() + ")  - " + currentUser.currentUserName() + ANSI_RESET);
        return "redirect:/amerykaspolka/edit?index=" + modifiedAmerykaSpolka.getIdSpolka();
    }


    @GetMapping("/testSpolka")
    public String getAll(Model model) {
//        long startTime = System.nanoTime();
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        log.info(ANSI_BLUE + "Odczyt samych spółek z tabeli ameryka_spolka." + ANSI_RESET);
        model.addAttribute("currentUser", currentUser.getName());
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
//        long executionTime = System.nanoTime() - startTime;
//        log.info("Zajęło mi to: " + (executionTime / 1000000000) + "s");
        return "amerykawidok";
    }

}
