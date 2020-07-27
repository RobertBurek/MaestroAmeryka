package pl.info.mojeakcje.maestroameryka.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CurrentUser;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.Customer;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CustomerDto;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CustomerService;
import pl.info.mojeakcje.maestroameryka.model.modelGoscie.Goscie;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.function.Function;

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
    public Iterable<Customer> getDBCustomers() {
        log.info(ANSI_BLUE + "Wypisałem wszystkich customers z bazy MaestroAmeryka z tabeli: customer" + ANSI_RESET);
        return custoRepository.findAll();
    }

    @GetMapping("/customers/{id_customer}")
    public Customer getCustomerId(@PathVariable Long id_customers) {
        log.info(ANSI_BLUE + "Dane z bazy MaestroAmeryka z tabeli: customer, id_customer: " + id_customers + ANSI_RESET);
        return ((List<Customer>) custoRepository.findAll())
                .stream()
                .filter(customer -> customer.getIdCustomer().equals(id_customers))
                .findAny().orElse(new Customer());
    }
}
