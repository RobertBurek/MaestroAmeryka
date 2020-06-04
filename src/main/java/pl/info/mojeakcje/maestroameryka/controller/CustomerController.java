package pl.info.mojeakcje.maestroameryka.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.*;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.Valid;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

/**
 * Created by Robert Burek
 */

@Log4j2
@Controller
public class CustomerController {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    CurrentUser currentUser;

    @Autowired
    CustomerService customerService;

    @Autowired
    QueryRepository queryRepository;

    @Autowired
    CustoRepository custoRepository;


    @GetMapping("/loginMaestro")
    public String login(Model model) {
        log.info(ANSI_RED + "Proces logowania!!!" + ANSI_RESET);
        model.addAttribute("nickUser", "");
        model.addAttribute("passkUser", "");
        return "loginMaestro";
    }


    @GetMapping("/rejestracja")
    public String rejestracja(Model model) {
//        long startTime = System.nanoTime();
        log.info(ANSI_BLUE + "Zaczynamy rejestrację nowego użytkownika." + ANSI_RESET);
        model.addAttribute("newCustomer", new CustomerDto());
//        long executionTime = System.nanoTime() - startTime;
//        log.info("Zajęło mi to: " + (executionTime / 1000000000) + "s");
        return "rejestracja";
    }

    @GetMapping("/rejestracja/login")
    public String rejestracjaLogin(Model model) {
        log.info(ANSI_BLUE + "Logowanie nowego użytkownika." + ANSI_RESET);
        model.addAttribute("nickUser", currentUser.getName());
        model.addAttribute("passUser", currentUser.getPass());
        return "loginMaestro";
    }

    @PostMapping("/rejestracja")
    public String zapisUser(@ModelAttribute("newCustomer") @Valid CustomerDto customerDto,
                            HttpServletRequest request, Error error, Model model) throws InterruptedException {


        if (!customerDto.getPasswordCustomer().equals(customerDto.getMatchPasswordCustomer())) {
            model.addAttribute("message", "Passwords don't match, are different.");
            return "rejestracja";
        }
        try {
            Customer registered = customerService.registerNewUserAccount(customerDto);
        } catch (Exception uaeEx) {
//            System.out.println(uaeEx);
//            ModelAndView mav = new ModelAndView();
//            mav.addObject("message", "An account for that username/email already exists.");
            model.addAttribute("message", "An account for that nick already exists.");
            return "rejestracja";
        }
        String nickCustomer = customerDto.getNickCustomer();
        log.info(ANSI_RED + "Nowy użytkownik do zalogowania: " + nickCustomer + ANSI_RESET);

        Long idCustomer = custoRepository.findByNickCustomer(customerDto.getNickCustomer()).getIdCustomer();
        queryRepository.copyNewCustomer();
        queryRepository.changeIdNewCustomer(idCustomer);
        queryRepository.setView(nickCustomer, idCustomer);


//        model.addAttribute("newCustomer", customerDto);
        return "redirect:/rejestracja/login";
//        return "redirect:/";
    }


//    @Target({TYPE, ANNOTATION_TYPE})
//    @Retention(RUNTIME)
//    @Constraint(validatedBy = PasswordMatchesValidator.class)
//    @Documented
//    public @interface PasswordMatches {
//        String message() default "Passwords don't match";
//
//        Class<?>[] groups() default {};
//
//        Class<? extends Payload>[] payload() default {};
//    }


    @GetMapping("/logout")
    public String logout() {
        log.info(ANSI_RED + "Wylogowano: " + currentUser.currentUserName() + ANSI_RESET);
        return "redirect:/amerykaspolka";
    }


}
