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
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaStrategia;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.AmSpStrategyRepository;

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
    AmSpStrategyRepository amSpStrategyRepository;
//    CreateStrategy createStrategy;

//    public AmSpController(CreateStrategy createStrategy) {
//        this.createStrategy = createStrategy;
//    }

    public AmSpController(AmSpRepository amSpRepository,
//                              CreateStrategy createStrategy,
                          AmSpStrategyRepository amSpStrategyRepository) {
        this.amSpStrategyRepository = amSpStrategyRepository;
        this.amSpRepository = amSpRepository;
//        this.createStrategy = createStrategy;
    }

//    public AmSpController(AmSpRepository amSpRepository) {
//        this.amSpRepository = amSpRepository;
//    }


    @GetMapping("/")
    public String getAll(Model model) {
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
        log.info("Szukać będziemy: " + ANSI_VIOLET + amerykaSpolkaFind.getTicker().toUpperCase() + ANSI_RESET);
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
//    @GetMapping("/amerykaspolka/del")
//    public String deleteAmerykaSpolka(@RequestParam Long index) {
//        deleteAmerykaSpolka = amSpRepository.findById(index);
//        log.info("Usuwanie danych: " + ANSI_RED + deleteAmerykaSpolka.getName() + " (" + deleteAmerykaSpolka.getTicker() + ")" + ANSI_RESET);
//        amSpRepository.deleteById(index);
//        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
//        return "redirect:/amerykaspolka/find#position";
//    }
//
    @GetMapping("/amerykaspolka/edit")
    public String editAmerykaSpolka(@RequestParam Long index, Model model) {
//        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        AmerykaSpolka modifiedAmerykaSpolka = amSpRepository.findById(index.longValue()).get();
//        List<AmerykaSpolka> newAmerykaSpolki =
//                amerykaSpolki.stream().filter(amerykaSpolka -> (amerykaSpolka.getTicker()).equals(modifiedAmerykaSpolka.getTicker())).collect(Collectors.toList());
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


//    @GetMapping("/strategie")
//    public String getAllStrategy(Model model) {
////        amerykaSpolkiStretegie = (List<AmerykaSpolkaStrategia>) amSpStrategyRepository.findAll();
////        amerykaSpolki.stream().map(amerykaSpolka -> Double.parseDouble(amerykaSpolka.getDay0119()))
//        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy ..." + ANSI_RESET);
//        model.addAttribute("industryList", industryList);
//        model.addAttribute("sectorList", sectorList);
//        model.addAttribute("amerykaSpolkiStrategie", amerykaSpolkiStretegie);
//        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
//        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
//        return "amerykastrategie";
//    }
//
//    @PutMapping("/strategie")
//    public void modificationList(@RequestBody Industry industry) {
////        int index=0;
//        log.info("Wysłałem : " + industry);
////        for (Industry ind :industryList) {
////            if (ind.getId()==industry.getId()) break;
////            else index++;
////        }
//        amerykaSpolkiStretegie = (List<AmerykaSpolkaStrategia>) amSpStrategyRepository.findAll();
//        amerykaSpolkiStretegie=amerykaSpolkiStretegie
//                .stream()
//                .filter(amerykaSpolkaStrategia -> amerykaSpolkaStrategia.getIndustry().equals(industry.getName()))
//                .collect(Collectors.toList());
////        return "redirect:/strategie";
//    }

}
