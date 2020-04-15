package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaStrategia;
import pl.info.mojeakcje.maestroameryka.model.Industry;
import pl.info.mojeakcje.maestroameryka.model.Sector;
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
public class AmSpStrategyController {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    AmSpRepository amSpRepository;
    AmSpStrategyRepository amSpStrategyRepository;

    public AmSpStrategyController(AmSpRepository amSpRepository,
                                  AmSpStrategyRepository amSpStrategyRepository) {
        this.amSpStrategyRepository = amSpStrategyRepository;
        this.amSpRepository = amSpRepository;
    }


    @GetMapping("/amerykastrategie")
    public String getAllStrategy(Model model) {
//        amerykaSpolkiStretegie = (List<AmerykaSpolkaStrategia>) amSpStrategyRepository.findAll();
//        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy ..." + ANSI_RESET);
        model.addAttribute("industryList", industryList);
        model.addAttribute("sectorList", sectorList);
        model.addAttribute("amerykaSpolkiStrategie", amerykaSpolkiStretegie);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykastrategie";
    }

    @GetMapping("/amerykastrategie/id")
//    public String getIndustryStrategy(@PathVariable Integer id, Model model) {
    public String getIndustryStrategy(@RequestParam Integer id, Model model) {
        Industry industryFind = industryList.get(id-1);
        amerykaSpolkiStretegie = (List<AmerykaSpolkaStrategia>) amSpStrategyRepository.findAll();
        amerykaSpolkiStretegie = amerykaSpolkiStretegie
                .stream()
                .filter(amerykaSpolkaStrategia -> amerykaSpolkaStrategia.getIndustry().equals(industryFind.getName()))
                .collect(Collectors.toList());
        log.info(ANSI_BLUE + "Spółki z branży: " + industryFind.getName() + ANSI_RESET);
        log.info(ANSI_BLUE + "Jest ich aż: " + amerykaSpolkiStretegie.size() + ANSI_RESET);
        log.info(ANSI_BLUE + "Są to: " + amerykaSpolkiStretegie + ANSI_RESET);
//        model.addAttribute("industryList", industryList);
//        model.addAttribute("sectorList", sectorList);
        model.addAttribute("amerykaSpolkiStrategie", amerykaSpolkiStretegie);
//        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
//        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
//        return "redirect:/amerykastrategie";
        return "amerykastrategie::#mojeZmiany";
    }

    @GetMapping("/amerykastrategie/sector/{id}")
    public String getSecctorStrategy(@PathVariable Integer id, Model model) {
//    public String getIndustryStrategy(@RequestParam Integer id, Model model) {
        Sector sectorFind = sectorList.get(id-1);
        amerykaSpolkiStretegie = (List<AmerykaSpolkaStrategia>) amSpStrategyRepository.findAll();
        amerykaSpolkiStretegie = amerykaSpolkiStretegie
                .stream()
                .filter(amerykaSpolkaStrategia -> amerykaSpolkaStrategia.getIndustry().equals(sectorFind.getName()))
                .collect(Collectors.toList());
        log.info(ANSI_BLUE + "Spółki z sektora: " + sectorFind.getName() + ANSI_RESET);
        log.info(ANSI_BLUE + "Jest ich aż: " + amerykaSpolkiStretegie.size() + ANSI_RESET);
        log.info(ANSI_BLUE + "Są to: " + amerykaSpolkiStretegie + ANSI_RESET);
//        model.addAttribute("industryList", industryList);
//        model.addAttribute("sectorList", sectorList);
        model.addAttribute("amerykaSpolkiStrategie", amerykaSpolkiStretegie);
//        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
//        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
//        return "redirect:/amerykastrategie";
        return "amerykastrategie::#mojeZmiany";
    }


//    @PutMapping
//    public String modificationList(@RequestBody Industry industry, Model model) {
////        int index=0;
//        log.info("Wysłałem : " + industry);
////        for (Industry ind :industryList) {
////            if (ind.getId()==industry.getId()) break;
////            else index++;
////        }
//        amerykaSpolkiStretegie = (List<AmerykaSpolkaStrategia>) amSpStrategyRepository.findAll();
//        amerykaSpolkiStretegie = amerykaSpolkiStretegie
//                .stream()
//                .filter(amerykaSpolkaStrategia -> amerykaSpolkaStrategia.getIndustry().equals(industry.getName()))
//                .collect(Collectors.toList());
////        return "redirect:/strategie";
//        model.addAttribute("industryList", industryList);
//        model.addAttribute("sectorList", sectorList);
//        model.addAttribute("amerykaSpolkiStrategie", amerykaSpolkiStretegie);
//        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
//        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
//        return "redirect:/amerykastrategie";
//    }

}
