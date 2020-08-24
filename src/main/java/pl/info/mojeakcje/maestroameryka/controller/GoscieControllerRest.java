package pl.info.mojeakcje.maestroameryka.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CurrentUser;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.Customer;
import pl.info.mojeakcje.maestroameryka.model.modelGoscie.Goscie;
import pl.info.mojeakcje.maestroameryka.repository.GoscieRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;
import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.ANSI_BLUE;

@RestController
public class GoscieControllerRest {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    GoscieRepository goscieRepository;
    CurrentUser currentUser;


    public GoscieControllerRest(GoscieRepository goscieRepository, CurrentUser currentUser) {
        this.goscieRepository = goscieRepository;
        this.currentUser = currentUser;
    }

    @PutMapping("info/{infoKto}&{infoMiasto}&{infoKraj}")
    public void infoPage(@PathVariable String infoKto, @PathVariable String infoMiasto, @PathVariable String infoKraj) {
        log.info(ANSI_YELLOW + "" + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + ", " + infoKto + ", " + infoMiasto + ", " + infoKraj + ANSI_RESET);
        currentUser.setIpCU(infoKto);
        goscieRepository.save(new Goscie(LocalDate.now(), LocalTime.now(), infoKto, infoMiasto, infoKraj));
    }


    @GetMapping("/goscie")
    public Iterable<Goscie> getDBCustomers() {
        log.info(ANSI_BLUE + "Wypisałem wszystkich z ostatnich trzech dni." + ANSI_RESET);
        return goscieRepository.findAll();
//                .stream()
//                .map(new Function<Customer, String>() {
//                    @Override
//                    public String apply(Customer customer) {
//                        return customer.getNickCustomer();
//                    }
//                }).collect(Collectors.toList());
    }

    @GetMapping("/goscie/{id_gosc}")
    public Goscie getCustomerId(@PathVariable Long id_gosc) {
        log.info(ANSI_BLUE + "Dane z bazy MaestroAmeryka z tabeli: customer, id_customer: " + id_gosc + ANSI_RESET);
        return ((List<Goscie>) goscieRepository.findAll())
                .stream()
                .filter(gosc -> gosc.getId().equals(id_gosc))
                .findAny()
                .orElse(new Goscie());
    }

}
