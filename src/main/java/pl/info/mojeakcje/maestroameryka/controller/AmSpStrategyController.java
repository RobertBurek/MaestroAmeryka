package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.info.mojeakcje.maestroameryka.model.*;
import pl.info.mojeakcje.maestroameryka.model.modeleStrategii.Szukana;
import pl.info.mojeakcje.maestroameryka.model.modeleStrategii.SzukanyModel;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;

import java.util.ArrayList;
import java.util.Comparator;
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
    private static List<String> filtrowane = new ArrayList<>();
    private static Integer wynikWyszukiwania = 0;
    private static String filtry = "";
    private static String defaultSort = "NameRosnaco";

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    AmSpRepository amSpRepository;

    public AmSpStrategyController(AmSpRepository amSpRepository) {
        this.amSpRepository = amSpRepository;
    }


    @GetMapping("/amerykastrategie")
    public String getAllStrategy(Model model) {
        szukane.clear();
        amerykaSpolki.clear();
        filtrowane.clear();
        defaultSort = "NameRosnaco";
        model.addAttribute("industryList", industryList);
        model.addAttribute("sectorList", sectorList);
        model.addAttribute("marketList", marketList);
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaNew", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaFind", new AmerykaSpolka());
        model.addAttribute("amerykaSpolkaModified", new AmerykaSpolka());
        model.addAttribute("wynikWyszukiwania", amerykaSpolki.size());
        model.addAttribute("filtry", filtry);
        return "amerykastrategie";
    }

//    @GetMapping("/amerykastrategie/id")
//    public String getIndustryStrategy(@RequestParam Integer id, Model model) {
//        Industry industryFind2 = industryList.get(id - 1);
//        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
//        amerykaSpolki = amerykaSpolki
//                .stream()
//                .filter(amerykaSpolka -> amerykaSpolka.getIndustry().equals(industryFind2.getName()))
//                .collect(Collectors.toList());
//        log.info(ANSI_BLUE + "Spółki z branży: " + industryFind2.getName() + ANSI_RESET);
//        log.info(ANSI_BLUE + "Jest ich aż: " + amerykaSpolki.size() + ANSI_RESET);
//        model.addAttribute("amerykaSpolki", amerykaSpolki);
//        return "amerykastrategie::#mojeZmiany";
//    }

//    @GetMapping("/amerykastrategie/sector/{id}")
//    public String getSectorStrategy(@PathVariable Integer id, Model model) {
//        Sector sectorFind2 = sectorList.get(id - 1);
//        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
//        amerykaSpolki = amerykaSpolki
//                .stream()
//                .filter(amerykaSpolkaStrategia -> amerykaSpolkaStrategia.getSector().equals(sectorFind2.getName()))
//                .collect(Collectors.toList());
//        log.info(ANSI_YELLOW + "Spółki z sektora: " + sectorFind2.getName() + ", jest ich aż: " + amerykaSpolki.size() + ANSI_RESET);
//        model.addAttribute("amerykaSpolkiStrategie", amerykaSpolki);
//        return "amerykastrategie::#mojeZmiany";
//    }

    @GetMapping("/amerykastrategie/find/{id}&{name}")
    public String getSectorStrategy(@PathVariable Integer id, @PathVariable String name, Model model) {
        if (id == -3) {

        }
        dodajDoWyszukiwania(id, name);
        amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        amerykaSpolki = amerykaSpolki
                .stream()
                .filter(szukanaPredicate())
                .collect(Collectors.toList());
        log.info(ANSI_RED + "Ilość pokazanych spółek: " + amerykaSpolki.size() + ANSI_RESET);
        if (szukane.size() == 0)
            amerykaSpolki = (List<AmerykaSpolka>) amSpRepository.findAll();
        filtry = "";
        if (filtrowane.size() > 0) {
            for (String filtr : filtrowane) {
                amerykaSpolki = amerykaSpolki
                        .stream()
                        .filter(wyszukajPredicate(filtr))
                        .collect(Collectors.toList());
                log.info(ANSI_FIOLET + "Ilość po filtrowaniu: " + amerykaSpolki.size() + ANSI_RESET);
                filtry += filtr + ",  ";
            }
        }
        if (id == -2) {
            log.info(ANSI_BLUE + "Posortowałem: " + name + ANSI_RESET);
            defaultSort=name;
        }
        amerykaSpolki = amerykaSpolki
                .stream()
                .sorted(sortowanieComparator(defaultSort))
                .collect(Collectors.toList());
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaModified", new AmerykaSpolka());
        model.addAttribute("wynikWyszukiwania", amerykaSpolki.size());
        model.addAttribute("filtry", filtry);
        return "amerykastrategie::#mojeZmiany";
    }


    @GetMapping("/amerykastrategie/note/{id}&{note}")
    public String chengeNote(@PathVariable Long id, @PathVariable String note, Model model) {
        String wierszTabeli = "amerykastrategie::#wierszTabeli"+id;
        log.info("amerykastrategie::#wierszTabeli"+id);
        log.info("notatka: "+note);
        AmerykaSpolka amerykaSpolka = amSpRepository.findById(id.longValue()).get();
        amerykaSpolka.setNote(note.replaceAll("QTTTQ"," "));
        amSpRepository.save(amerykaSpolka);
        log.info(""+amerykaSpolka);
        model.addAttribute("amerykaSpolka", amerykaSpolka);
//        model.addAttribute("amerykaSpolki", amerykaSpolki);
//        model.addAttribute("amerykaSpolkaModified", new AmerykaSpolka());
//        model.addAttribute("wynikWyszukiwania", amerykaSpolki.size());
//        model.addAttribute("filtry", filtry);
        return wierszTabeli;
    }


    @NotNull
    private Comparator<AmerykaSpolka> sortowanieComparator(String sort) {
        return new Comparator<AmerykaSpolka>() {
            @Override
            public int compare(AmerykaSpolka o1, AmerykaSpolka o2) {
                Double b1 = 0.0;
                Double b2 = 0.0;
                switch (sort) {
                    case "NameRosnaco": {
                        return o1.getName().compareTo(o2.getName());
                    }
                    case "NameMalejaco": {
                        return o2.getName().compareTo(o1.getName());
                    }
                    case "SectorRosnaco": {
                        return o1.getSector().compareTo(o2.getSector());
                    }
                    case "SectorMalejaco": {
                        return o2.getSector().compareTo(o1.getSector());
                    }
                    case "YTDRosnaco": {
                        if (!o1.getyTD().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getyTD().trim().replace("%", ""));
                        if (!o2.getyTD().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getyTD().trim().replace("%", ""));
                        return Double.compare(b1, b2);
                    }
                    case "YTDMalejaco": {
                        if (!o1.getyTD().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getyTD().trim().replace("%", ""));
                        if (!o2.getyTD().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getyTD().trim().replace("%", ""));
                        return Double.compare(b2, b1);
                    }
                    case "1MTDRosnaco": {
                        if (!o1.getCourse1M().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getCourse1M().trim().replace("%", ""));
                        if (!o2.getCourse1M().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getCourse1M().trim().replace("%", ""));
                        return Double.compare(b1, b2);
                    }
                    case "1MTDMalejaco": {
                        if (!o1.getCourse1M().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getCourse1M().trim().replace("%", ""));
                        if (!o2.getCourse1M().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getCourse1M().trim().replace("%", ""));
                        return Double.compare(b2, b1);
                    }
                    case "2MTDRosnaco": {
                        if (!o1.getCourse3M().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getCourse3M().trim().replace("%", ""));
                        if (!o2.getCourse3M().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getCourse3M().trim().replace("%", ""));
                        return Double.compare(b1, b2);
                    }
                    case "2MTDMalejaco": {
                        if (!o1.getCourse3M().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getCourse3M().trim().replace("%", ""));
                        if (!o2.getCourse3M().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getCourse3M().trim().replace("%", ""));
                        return Double.compare(b2, b1);
                    }
                }
                return 0;
            }
        };
    }

    @NotNull
    private Predicate<AmerykaSpolka> wyszukajPredicate(String val) {
        return new Predicate<AmerykaSpolka>() {
            @Override
            public boolean test(AmerykaSpolka amerykaSpolka) {
                boolean flaga = false;
                switch (val) {
                    case "dodatniaYTD": {
                        if (!amerykaSpolka.getyTD().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getyTD().trim().replace("%", "")) >= 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "ujemnaYTD": {
                        if (!amerykaSpolka.getyTD().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getyTD().trim().replace("%", "")) < 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "dodatnia1MTD": {
                        if (!amerykaSpolka.getCourse1M().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getCourse1M().trim().replace("%", "")) >= 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "ujemna1MTD": {
                        if (!amerykaSpolka.getCourse1M().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getCourse1M().trim().replace("%", "")) < 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "dodatnia2MTD": {
                        if (!amerykaSpolka.getCourse3M().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getCourse3M().trim().replace("%", "")) >= 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "ujemna2MTD": {
                        if (!amerykaSpolka.getCourse3M().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getCourse3M().trim().replace("%", "")) < 0)
                                flaga = true;
                        }
                        break;
                    }
                }
                return flaga;
            }
        };
    }

    @NotNull
    private Predicate<AmerykaSpolka> szukanaPredicate() {
        return new Predicate<AmerykaSpolka>() {
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
        };
    }

    private void dodajDoWyszukiwania(@PathVariable Integer id, @PathVariable String name) {
        if (id >= 0) {
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
            log.info(ANSI_GREEN + "Dodano/Usunięto do pokazania: " + name + " / " + szukana.getSzukanaWartosc() + ANSI_RESET);
            log.info(ANSI_GREEN_ + "Ilość warunków: " + szukane.size() + ANSI_RESET);
            log.info(ANSI_GREEN + "Lista warunków: " + szukane + ANSI_RESET);
        }
        if (id == -1) {
            if (!filtrowane.remove(name)) filtrowane.add(name);
            log.info(ANSI_YELLOW + "Dodano/Usunięto filtr: " + name + ANSI_RESET);
            log.info(ANSI_YELLOW + "Ilość filtrów: " + szukane.size() + ANSI_RESET);
            log.info(ANSI_YELLOW + "Lista filtrów: " + filtrowane + ANSI_RESET);
        }
    }

}
