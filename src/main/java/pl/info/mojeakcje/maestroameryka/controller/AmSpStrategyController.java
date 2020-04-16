package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.info.mojeakcje.maestroameryka.model.*;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

/**
 * Created by Robert Burek
 */

@Log4j2
@Controller
public class AmSpStrategyController {

    private static List<Szukana> szukane = new ArrayList<>();

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    AmSpRepository amSpRepository;

    public AmSpStrategyController(AmSpRepository amSpRepository) {
        this.amSpRepository = amSpRepository;
    }


    @GetMapping("/amerykastrategie")
    public String getAllStrategy(Model model) {
//        amerykaSpolkiStretegie = (List<AmerykaSpolkaStrategia>) amSpStrategyRepository.findAll();
//        log.info(ANSI_BLUE + "Odczyt wszystkich danych z bazy ..." + ANSI_RESET);
        model.addAttribute("industryList", industryList);
        model.addAttribute("sectorList", sectorList);
        model.addAttribute("marketList", marketList);
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        return "amerykastrategie";
    }

    @GetMapping("/amerykastrategie/id")
    public String getIndustryStrategy(@RequestParam Integer id, Model model) {
        Industry industryFind2 = industryList.get(id - 1);
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        amerykaSpolki = amerykaSpolki
                .stream()
                .filter(amerykaSpolka -> amerykaSpolka.getIndustry().equals(industryFind2.getName()))
                .collect(Collectors.toList());
        log.info(ANSI_BLUE + "Spółki z branży: " + industryFind2.getName() + ANSI_RESET);
        log.info(ANSI_BLUE + "Jest ich aż: " + amerykaSpolki.size() + ANSI_RESET);
        model.addAttribute("amerykaSpolki", amerykaSpolki);

        return "amerykastrategie::#mojeZmiany";
    }

    @GetMapping("/amerykastrategie/sector/{id}")
    public String getSectorStrategy(@PathVariable Integer id, Model model) {
        Sector sectorFind2 = sectorList.get(id - 1);
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        amerykaSpolki = amerykaSpolki
                .stream()
                .filter(amerykaSpolkaStrategia -> amerykaSpolkaStrategia.getSector().equals(sectorFind2.getName()))
                .collect(Collectors.toList());
        log.info(ANSI_YELLOW + "Spółki z sektora: " + sectorFind2.getName() + ", jest ich aż: " + amerykaSpolki.size() + ANSI_RESET);
        model.addAttribute("amerykaSpolkiStrategie", amerykaSpolki);
        return "amerykastrategie::#mojeZmiany";
    }

    @GetMapping("/amerykastrategie/find/{id}&{name}")
    public String getSectorStrategy(@PathVariable Integer id, @PathVariable String name, Model model) {
        dodajDoWyszukiwania(id, name);
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        amerykaSpolki = amerykaSpolki
                .stream()
                .filter(new Predicate<AmerykaSpolka>() {
                    @Override
                    public boolean test(AmerykaSpolka amerykaSpolka) {
                        boolean flaga = false;
                        for (Szukana szukana : szukane) {
                            switch (szukana.getRodzajSzukanej()) {
                                case "market": {
                                    if (amerykaSpolka.getMarket().trim().equals(szukana.getSzukanaWartosc()))
                                        flaga = true;
                                    break;
                                }
                                case "sector": {
                                    if (amerykaSpolka.getSector().trim().equals(szukana.getSzukanaWartosc()))
                                        flaga = true;
                                    break;
                                }
                                case "industry": {
                                    if (amerykaSpolka.getIndustry().trim().equals(szukana.getSzukanaWartosc()))
                                        flaga = true;
                                    break;
                                }
                            }
                        }
                        return flaga;
                    }
                })
                .collect(Collectors.toList());
        log.info(ANSI_RED + "Ilość zmalezionych: " + amerykaSpolki.size() + ANSI_RESET);
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        return "amerykastrategie::#mojeZmiany";
    }

    private void dodajDoWyszukiwania(@PathVariable Integer id, @PathVariable String name) {
        SzukanyModel modelSzukana = new SzukanyModel();
        if (name.equals("market")) modelSzukana = marketList.get(id - 1);
        if (name.equals("sector")) modelSzukana = sectorList.get(id - 1);
        if (name.equals("industry")) modelSzukana = industryList.get(id - 1);
        Szukana szukana = new Szukana(name, modelSzukana.getName());
        Szukana szukanaYes;
        if (szukane.stream().filter(szu -> szu.getSzukanaWartosc().equals(szukana.getSzukanaWartosc())).findFirst().isPresent()) {
            szukanaYes = szukane.stream().filter(szu -> szu.getSzukanaWartosc().equals(szukana.getSzukanaWartosc())).findFirst().get();
            szukane.remove(szukanaYes);
        } else szukane.add(szukana);
        log.info("Dodano / usunięto do wyszukiwania: " + name + " / " + szukana.getSzukanaWartosc());
        log.info("Ilość szukanych parametrów: " + szukane.size());
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
