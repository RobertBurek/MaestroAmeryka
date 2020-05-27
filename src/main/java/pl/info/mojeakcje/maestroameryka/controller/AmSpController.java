package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CurrentUser;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;

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

    public AmSpController(CurrentUser currentUser, AmSpRepository amSpRepository, QueryRepository queryRepository) {
        this.currentUser = currentUser;
        this.amSpRepository = amSpRepository;
        this.queryRepository = queryRepository;
    }

    //    public AmSpController(AmSpRepository amSpRepository, QueryRepository queryRepository) {
//        this.amSpRepository = amSpRepository;
//        this.queryRepository = queryRepository;
//    }

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
    public String getAllView(Model model) {
        queryRepository.findAllWszystkieDane(currentUser.getName());
//        System.out.println("amerykawidok, currentUser: " + currentUser.getName());
//        amerykaSpolki = wszystkieDaneList.stream().map(wszystkieDane -> wszystkieDane.getAmerykaSpolka()).collect(Collectors.toList());
//        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy, endpoint (/), użytkownik: " + currentUser.getName() + ANSI_RESET);
        model.addAttribute("currentUser", currentUser.getName());
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykawidok";
    }


    @GetMapping("/amerykaspolka")
    public String getStart(Model model) {
//        amerykaSpolkiStretegie = (List<AmerykaSpolkaStrategia>) amSpStrategyRepository.findAll();
//        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
//        amerykaSpolki.stream().map(amerykaSpolka -> Double.parseDouble(amerykaSpolka.getDay0119()))
        queryRepository.findAllWszystkieDane(currentUser.getName());
        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy, endpoint (/amerykaspolka), użytkownik: " + currentUser.getName() + ANSI_RESET);
        model.addAttribute("currentUser", currentUser.getName());
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
//        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        amerykaSpolki = queryRepository.findAllWszystkieDane(currentUser.getName());
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
        amSpRepository.deleteById(deleteAmerykaSpolka.getIdSpolka());
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        return "redirect:/amerykaspolka";
    }

    @GetMapping("/amerykaspolka/edit")
    public String editAmerykaSpolka(@RequestParam Long index, Model model) {
//        AmerykaSpolka modifiedAmerykaSpolka = amSpRepository.findById(index.longValue()).get();
        AmerykaSpolka modifiedAmerykaSpolka = amerykaSpolki.stream().filter(amerykaSpolka -> amerykaSpolka.getIdSpolka()==((int)(long)(index))).findFirst().get();
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

//    private void findAllWszystkieDane() {
//        List<Object> wszystkieDaneList = queryRepository.getDaneWithView(currentUser.getName());
//        Iterator itr = wszystkieDaneList.iterator();
//        while (itr.hasNext()) {
//            Object[] obj = (Object[]) itr.next();
//            AmerykaSpolka amerykaSpolka = new AmerykaSpolka(String.valueOf(obj[29]), String.valueOf(obj[26]), String.valueOf(obj[25]), String.valueOf(obj[28]), String.valueOf(obj[21]), String.valueOf(obj[1]), String.valueOf(obj[31]));
//            amerykaSpolka.setIdSpolka(Long.parseLong(String.valueOf(obj[10])));
//            amerykaSpolka.setIdWszystkieDane(Long.parseLong(String.valueOf(obj[0])));
//            amerykaSpolka.setWidok(Boolean.valueOf(String.valueOf(obj[2])));
//            amerykaSpolka.setyTD(String.valueOf(obj[30]));
//            amerykaSpolka.setM1(String.valueOf(obj[22]));
//            amerykaSpolka.setM3(String.valueOf(obj[24]));
//            amerykaSpolka.setM12(String.valueOf(obj[23]));
//            amerykaSpolka.setDay1M(String.valueOf(obj[17]));
//            amerykaSpolka.setDay3M(String.valueOf(obj[18]));
//            amerykaSpolka.setDay12M(String.valueOf(obj[16]));
//            amerykaSpolka.setDayYTD(String.valueOf(obj[20]));
//            amerykaSpolka.setDayCourseCurrent(String.valueOf(obj[19]));
//            amerykaSpolka.setCourse3M(String.valueOf(obj[13]));
//            amerykaSpolka.setCourse1M(String.valueOf(obj[12]));
//            amerykaSpolka.setCourseYTD(String.valueOf(obj[15]));
//            amerykaSpolka.setCourse12M(String.valueOf(obj[11]));
//            amerykaSpolka.setCourseCurrent(String.valueOf(obj[14]));
//            amerykaSpolki.add(amerykaSpolka);
//        }
//    }

}
