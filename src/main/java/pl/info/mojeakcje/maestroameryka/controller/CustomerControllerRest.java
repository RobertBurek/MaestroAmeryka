package pl.info.mojeakcje.maestroameryka.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.Customer;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

/**
 * Created by Robert Burek
 */

@RestController
public class CustomerControllerRest {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

//    @Autowired
//    CurrentUser currentUser;
//
//    @Autowired
//    CustomerService customerService;
//
//    @Autowired
//    QueryRepository queryRepository;

    @Autowired
    CustoRepository custoRepository;

    @GetMapping("/customers")
    public List<String> getDBCustomers() {
        log.info(ANSI_BLUE + "Wypisałem wszystkich customers z bazy MaestroAmeryka z tabeli: customer" + ANSI_RESET);
        return ((List<Customer>) custoRepository.findAll())
                .stream()
                .map(new Function<Customer, String>() {
                    @Override
                    public String apply(Customer customer) {
                        return customer.getNickCustomer();
                    }
                }).collect(Collectors.toList());
    }

    @GetMapping("/customers/{id_customer}")
    public String getCustomerId(@PathVariable Long id_customer) {
        log.info(ANSI_BLUE + "Dane z bazy MaestroAmeryka z tabeli: customer, id_customer: " + id_customer + ANSI_RESET);
        return ((List<Customer>) custoRepository.findAll())
                .stream()
                .filter(customer -> customer.getIdCustomer().equals(id_customer))
                .findAny()
                .map(new Function<Customer, String>() {
                    @Override
                    public String apply(Customer customer) {
                        return customer.getNickCustomer() + " IP: " + customer.getIpCustomer();
                    }
                })
                .orElse("Nie ma klienta o takim id.");
    }
}
