package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.ShowSpolka;
import pl.info.mojeakcje.maestroameryka.model.WszystkieDane;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CurrentUser;
import pl.info.mojeakcje.maestroameryka.model.modeleStrategii.Szukana;
import pl.info.mojeakcje.maestroameryka.model.modeleStrategii.SzukanyModel;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;
import pl.info.mojeakcje.maestroameryka.repository.WszysDaneRepository;

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
    private static String filtry = "";
    private static String defaultSort = "NameRosnaco";
    private static List<AmerykaSpolka> amerykaSpolkiFound = new ArrayList<>();

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    WszysDaneRepository wszysDaneRepository;
    QueryRepository queryRepository;
    CurrentUser currentUser;
    ShowSpolka showSpolka;

    public AmSpStrategyController(WszysDaneRepository wszysDaneRepository, QueryRepository queryRepository, CurrentUser currentUser, ShowSpolka showSpolka) {
        this.wszysDaneRepository = wszysDaneRepository;
        this.queryRepository = queryRepository;
        this.currentUser = currentUser;
        this.showSpolka = showSpolka;
    }

    @GetMapping("/amerykastrategie")
    public String getAllStrategy(Model model) {
        szukane.clear();
        amerykaSpolki.clear();
        amerykaSpolkiFound.clear();
        filtrowane.clear();
        defaultSort = "NameRosnaco";
        model.addAttribute("showAll", showSpolka.getShow());
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

    @GetMapping("/amerykastrategie/result")
    public String resultFindStrategy(Model model) {
        szukane.clear();
        filtrowane.clear();
        defaultSort = "NameRosnaco";
        model.addAttribute("showAll", showSpolka.getShow());
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

    @PostMapping("/amerykastrategie/find")
    public String strategiaFindAmerykaSpolka(@ModelAttribute AmerykaSpolka amerykaSpolkaFind) {
        log.info("Szukać będziemy: " + ANSI_FIOLET + amerykaSpolkaFind.getTicker().toUpperCase() + ANSI_RESET + " , w grupie: " + amerykaSpolkaFind.getIdWszystkieDane());
        amerykaSpolki = queryRepository.findAllWszystkieDane(currentUser.currentUserName());
        if (!amerykaSpolkaFind.getTicker().equals("")) {
            amerykaSpolki = amerykaSpolki.stream()
                    .filter(predicateFind(amerykaSpolkaFind))
                    .collect(Collectors.toList());
        }
        amerykaSpolkiFound = amerykaSpolki
                .stream()
                .filter(predicateFind(amerykaSpolkaFind))
                .collect(Collectors.toList());
        ;
        if (amerykaSpolkiFound == amerykaSpolki) {
            log.info("są to te same obiekty");
        } else {
            log.info("są to inne obiekty");
        }
        ;
        log.info("Lista znalezionych spółek: " + ANSI_YELLOW + amerykaSpolki.stream()
                .map(amerykaSpolka -> amerykaSpolka.getTicker() + ", ")
                .collect(Collectors.joining()) + ANSI_RESET);
        return "redirect:/amerykastrategie/result";
    }

    @GetMapping("/amerykastrategie/find/{id}&{name}")
    public String getSectorStrategy(@PathVariable Integer id, @PathVariable String name, Model model) {
        if (id == -3) {
            if (showSpolka.getShow()) showSpolka.setShow(false);
            else showSpolka.setShow(true);
        }
        dodajDoWyszukiwania(id, name);
//        amerykaSpolki = queryRepository.findAllWszystkieDane(currentUser.currentUserName());
        if (!showSpolka.show) queryRepository.showWD();
//        amerykaSpolki = amerykaSpolki
//                .stream()
//                .filter(szukanaPredicate())
//                .collect(Collectors.toList());
        log.info(ANSI_RED + "Ilość pokazanych spółek: " + amerykaSpolki.size() + ANSI_RESET);
//        if (szukane.size() == 0)
//            amerykaSpolki.clear();
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
            defaultSort = name;
        }
        amerykaSpolki = amerykaSpolki
                .stream()
                .sorted(sortowanieComparator(defaultSort))
                .collect(Collectors.toList());
        model.addAttribute("showAll", showSpolka.getShow());
        model.addAttribute("amerykaSpolki", amerykaSpolki);
        model.addAttribute("amerykaSpolkaModified", new AmerykaSpolka());
        model.addAttribute("wynikWyszukiwania", amerykaSpolki.size());
        model.addAttribute("filtry", filtry);
        return "amerykastrategie::#mojeZmiany";
    }

    @GetMapping("/amerykastrategie/note/{id}&{note}")
    public String chengeNote(@PathVariable Long id, @PathVariable String note, Model model) {
        String wierszTabeli = "amerykastrategie::#wierszTabeli" + id;
        AmerykaSpolka amerykaSpolka = amerykaSpolki.stream().filter(amSp -> amSp.getIdSpolka().equals(id)).findFirst().get();
        amerykaSpolka.setNote(note.replaceAll("QTTTQ", " "));
        WszystkieDane wszystkieDane = wszysDaneRepository.findById(amerykaSpolka.getIdWszystkieDane()).get();
        log.info("Notatka dla spólki: " + amerykaSpolka.getName());
        wszystkieDane.setNotatka(note.replaceAll("QTTTQ", " "));
        wszysDaneRepository.save(wszystkieDane);
        log.info("Zmieniono notatkę na: " + amerykaSpolka.getNote() + "  - " + currentUser.currentUserName());
        model.addAttribute("amerykaSpolka", amerykaSpolka);
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
                    case "1MRosnaco": {
                        if (!o1.getM1().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getM1().trim().replace("%", ""));
                        if (!o2.getM1().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getM1().trim().replace("%", ""));
                        return Double.compare(b1, b2);
                    }
                    case "1MMalejaco": {
                        if (!o1.getM1().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getM1().trim().replace("%", ""));
                        if (!o2.getM1().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getM1().trim().replace("%", ""));
                        return Double.compare(b2, b1);
                    }
                    case "3MRosnaco": {
                        if (!o1.getM3().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getM3().trim().replace("%", ""));
                        if (!o2.getM3().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getM3().trim().replace("%", ""));
                        return Double.compare(b1, b2);
                    }
                    case "3MMalejaco": {
                        if (!o1.getM3().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getM3().trim().replace("%", ""));
                        if (!o2.getM3().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getM3().trim().replace("%", ""));
                        return Double.compare(b2, b1);
                    }
                    case "12MRosnaco": {
                        if (!o1.getM12().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getM12().trim().replace("%", ""));
                        if (!o2.getM12().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getM12().trim().replace("%", ""));
                        return Double.compare(b1, b2);
                    }
                    case "12MMalejaco": {
                        if (!o1.getM12().trim().equals("brak"))
                            b1 = Double.parseDouble(o1.getM12().trim().replace("%", ""));
                        if (!o2.getM12().trim().equals("brak"))
                            b2 = Double.parseDouble(o2.getM12().trim().replace("%", ""));
                        return Double.compare(b2, b1);
                    }
                }
                return 0;
            }
        };
    }


    @NotNull
    private Predicate<AmerykaSpolka> predicateFind(@ModelAttribute AmerykaSpolka amerykaSpolkaFind) {
        return new Predicate<AmerykaSpolka>() {
            @Override
            public boolean test(AmerykaSpolka amerykaSpolka) {
                if (amerykaSpolkaFind.getIdWszystkieDane() == 1)
                    return (amerykaSpolka.getName().toUpperCase()).contains(amerykaSpolkaFind.getTicker().toUpperCase());
                if (amerykaSpolkaFind.getIdWszystkieDane() == 2)
                    return (amerykaSpolka.getTicker()).contains(amerykaSpolkaFind.getTicker().toUpperCase());
                if (amerykaSpolkaFind.getIdWszystkieDane() == 4)
                    return (amerykaSpolka.getIndustry().toUpperCase()).contains(amerykaSpolkaFind.getTicker().toUpperCase());
                if (amerykaSpolkaFind.getIdWszystkieDane() == 3)
                    return (amerykaSpolka.getSector().toUpperCase()).contains(amerykaSpolkaFind.getTicker().toUpperCase());
                return false;
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
                    case "dodatnia1M": {
                        if (!amerykaSpolka.getM1().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getM1().trim().replace("%", "")) >= 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "ujemna1M": {
                        if (!amerykaSpolka.getM1().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getM1().trim().replace("%", "")) < 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "dodatnia3M": {
                        if (!amerykaSpolka.getM3().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getM3().trim().replace("%", "")) >= 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "ujemna3M": {
                        if (!amerykaSpolka.getM3().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getM3().trim().replace("%", "")) < 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "dodatnia12M": {
                        if (!amerykaSpolka.getM12().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getM12().trim().replace("%", "")) >= 0)
                                flaga = true;
                        }
                        break;
                    }
                    case "ujemna12M": {
                        if (!amerykaSpolka.getM12().trim().equals("brak")) {
                            if (Double.parseDouble(amerykaSpolka.getM12().trim().replace("%", "")) < 0)
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


    private void dodajDoWyszukiwania(Integer id, String name) {
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
            if (szukane.size() == 0) amerykaSpolki.clear();
            amerykaSpolkiFound.clear();
        }
        if (szukane.size() > 0) amerykaSpolki = queryRepository.findAllWszystkieDane(currentUser.currentUserName())
                .stream()
                .filter(szukanaPredicate())
                .collect(Collectors.toList());
        if (id == -1) {
            if (!filtrowane.remove(name)) filtrowane.add(name);
            log.info(ANSI_YELLOW + "Dodano/Usunięto filtr: " + name + ANSI_RESET);
            log.info(ANSI_YELLOW + "Ilość filtrów: " + filtrowane.size() + ANSI_RESET);
            log.info(ANSI_YELLOW + "Lista filtrów: " + filtrowane + ANSI_RESET);
            if (amerykaSpolkiFound.size() > 0) amerykaSpolki = amerykaSpolkiFound;
        }
    }

}
