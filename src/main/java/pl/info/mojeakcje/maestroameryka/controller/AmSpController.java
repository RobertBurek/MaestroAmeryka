package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.ShowSpolka;
import pl.info.mojeakcje.maestroameryka.model.WszystkieDane;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CurrentUser;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;
import pl.info.mojeakcje.maestroameryka.repository.WszysDaneRepository;

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

    CurrentUser currentUser;
    AmSpRepository amSpRepository;
    QueryRepository queryRepository;
    ShowSpolka showSpolka;
    WszysDaneRepository wszysDaneRepository;

    public AmSpController(CurrentUser currentUser, AmSpRepository amSpRepository, QueryRepository queryRepository, ShowSpolka showSpolka, WszysDaneRepository wszysDaneRepository) {
        this.currentUser = currentUser;
        this.amSpRepository = amSpRepository;
        this.queryRepository = queryRepository;
        this.showSpolka = showSpolka;
        this.wszysDaneRepository = wszysDaneRepository;
    }

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
    public String login() {
        log.info(ANSI_RED + "Proces logowania!!!" + ANSI_RESET);
        return "loginMaestro";
    }

    @GetMapping("/logout")
    public String logout() {
        log.info(ANSI_RED + "Wylogowano: " + currentUserName() + ANSI_RESET);
        return "redirect:/amerykaspolka";
    }


    @GetMapping("/")
    public String getAllView(Model model) {
        queryRepository.findAllWszystkieDane(currentUserName());
        if (!showSpolka.show) queryRepository.showWD();
        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy, endpoint (/), użytkownik: " + currentUserName() + ANSI_RESET);
        model.addAttribute("showAll", showSpolka.getShow());
        model.addAttribute("currentUserName", currentUserName());
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykawidok";
    }


    @GetMapping("/amerykaspolka")
    public String getStart(Model model) {
        queryRepository.findAllWszystkieDane(currentUserName());
        if (!showSpolka.show) queryRepository.showWD();
        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy, endpoint (/amerykaspolka), użytkownik: " + currentUserName() + ANSI_RESET);
        model.addAttribute("showAll", showSpolka.getShow());
        model.addAttribute("currentUserName", currentUserName());
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


    @GetMapping("/amerykaspolka/find")
    public String getAllFind(Model model) {
        model.addAttribute("showAll", showSpolka.getShow());
        model.addAttribute("currentUserName", currentUserName());
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykawidok";
    }

    @PostMapping("/amerykaspolka")
    public String postFindAmerykaSpolka(@ModelAttribute AmerykaSpolka amerykaSpolkaFind) {
        log.info("Szukać będziemy: " + ANSI_FIOLET + amerykaSpolkaFind.getTicker().toUpperCase() + ANSI_RESET);
        amerykaSpolki = queryRepository.findAllWszystkieDane(currentUserName());
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


    @GetMapping("/amerykaspolka/show")
    public String showAmerykaSpolka(@RequestParam Long index, Model model) {
        AmerykaSpolka showAmerykaSpolka = amerykaSpolki.stream().filter(amerykaSpolka -> amerykaSpolka.getIdSpolka().equals(index)).findFirst().get();
        WszystkieDane wszystkieDane = wszysDaneRepository.findById(showAmerykaSpolka.getIdWszystkieDane()).get();
        if (wszystkieDane.getWidoczny()) wszystkieDane.setWidoczny(false);
        else wszystkieDane.setWidoczny(true);
        wszysDaneRepository.save(wszystkieDane);
        log.info("Spółka usunięta z listy widoku: " + ANSI_RED + showAmerykaSpolka.getName() + " (" + showAmerykaSpolka.getTicker() + ")" + ANSI_RESET);
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "redirect:/amerykaspolka";
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
        WszystkieDane wszystkieDane = wszysDaneRepository.findById(modifiedAmerykaSpolka.getIdWszystkieDane()).get();
        wszystkieDane.setNotatka(modifiedAmerykaSpolka.getNote());
        wszystkieDane.setWidoczny(modifiedAmerykaSpolka.getWidok());
        wszysDaneRepository.save(wszystkieDane);
        amerykaSpolki = queryRepository.findAllWszystkieDane(currentUserName());
        log.info("Zapisano nowe dane dla spólki: " + ANSI_YELLOW + modifiedAmerykaSpolka.getName() + " (" + modifiedAmerykaSpolka.getTicker() + ")  - " + currentUserName() + ANSI_RESET);
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


    private String currentUserName() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
