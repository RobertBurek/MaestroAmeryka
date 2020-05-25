package pl.info.mojeakcje.maestroameryka;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.WszystkieDane;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.Customer;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.UserDetailsServiceImpl;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;
import pl.info.mojeakcje.maestroameryka.repository.WszysDaneRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
@Slf4j

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    private UserDetailsServiceImpl userDetailsService;
    private CustoRepository custoRepository;
    private AmSpRepository amSpRepository;
    private WszysDaneRepository wszysDaneRepository;
    private QueryRepository queryRepository;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, CustoRepository custoRepository, AmSpRepository amSpRepository, WszysDaneRepository wszysDaneRepository, QueryRepository queryRepository) {
        this.userDetailsService = userDetailsService;
        this.custoRepository = custoRepository;
        this.amSpRepository = amSpRepository;
        this.wszysDaneRepository = wszysDaneRepository;
        this.queryRepository = queryRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser(new User("Admin", passwordEncoder().encode("Admin"), Collections.singleton(new SimpleGrantedAuthority("admin"))));

        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .authorizeRequests()
                .antMatchers("/amerykaspolka").permitAll()
//                .antMatchers("/amerykastrategie").hasRole("USER")
                .antMatchers("/amerykaspolka/save/edit").hasRole("ADMIN")
                .antMatchers("/amerykaspolka/delete").hasRole("ADMIN")


//                .antMatchers("/**").hasRole("ADMIN")
//                .antMatchers("/amerykastrategie").hasRole("ADMIN")

//                .authenticated()
                .and()
                .formLogin()
                .loginPage("/loginMaestro").permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .headers().disable()
        ;
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/loginOpenID")
//                .authenticated();

//            http
//                    .authorizeRequests()
//                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                    .permitAll()
////                    .anyRequest().authenticated()
//                    .antMatchers("/nameDay/save").authenticated()
//                    .antMatchers("/nameDay/save/edit").authenticated()
//                    .antMatchers("/nameDay/del").authenticated()
//                    .antMatchers("/nameDayRest/save").authenticated()
//                    .antMatchers("/nameDayRest/delete").authenticated()
//                    .antMatchers("/loginOpenID").authenticated()
//                    .and()
//                    .oauth2Login()
//                    .loginPage("/login")
//                    .permitAll();
//            log.info(PathRequest.toStaticResources().atCommonLocations().toString());


//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/amerykastrategie").authenticated()
//                .antMatchers("/amerykaspolka/edit").authenticated()
////                .antMatchers("/nameDay/del").authenticated()
////                .antMatchers("/nameDayRest/save").authenticated()
////                .antMatchers("/nameDayRest/delete").authenticated()
////                .antMatchers("/loginOpenID").authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/loginMaestro");
    }

    //    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//            Metoda dodawania customers do bazy danych , wykonywana zawsze przy uruchomianiu aplikacji
    @EventListener(ApplicationReadyEvent.class)
    public void get() {
//        Customer customer1 = new Customer("Wiki", passwordEncoder().encode("wiki"), RoleCustomer.ROLE_ADMIN.toString(), "168.1.21.1");
//        Customer customer1 = new Customer("Maja", passwordEncoder().encode("Maja2006"), RoleCustomer.ROLE_ADMIN.toString(), "168.1.31.3");
//        Customer customer1 = new Customer("Robert", passwordEncoder().encode("Robert10"), RoleCustomer.ROLE_ADMIN.toString(), "168.1.21.1");
//        Customer customer2 = new Customer("Maciej", passwordEncoder().encode("Maciej10"), RoleCustomer.ADMIN.toString(), "165.12.2.21");
//        Customer customer3 = new Customer("Adam", passwordEncoder().encode("Adam10"), RoleCustomer.USER.toString(), "165.161.251.13");
//        Customer customer4 = new Customer("Nowy", passwordEncoder().encode("Nowy123"), RoleCustomer.USER.toString(), "125.14.214.54");
//        custoRepository.save(customer1);
//        custoRepository.save(customer2);
//        custoRepository.save(customer3);
//        custoRepository.save(customer4);

        List<AmerykaSpolka> amerykaSpolki = new ArrayList<>();
        Customer robert = custoRepository.findById(1L).get();

        // relacja onetomany customer i amerykaspolka
//        AmerykaSpolka stara = amSpRepository.findById(1L).get();
//        AmerykaSpolka amerykaSpolka = new AmerykaSpolka();
//        amerykaSpolka.setName(stara.getName());
//        amerykaSpolka.setTicker(stara.getTicker());
//        amerykaSpolka.setIndustry(stara.getIndustry());
//        amerykaSpolka.setNote(stara.getNote());
//        amerykaSpolki.add(amerykaSpolka);
//        stara = amSpRepository.findById(21L).get();
//        amerykaSpolka = new AmerykaSpolka();
//        amerykaSpolka.setName(stara.getName());
//        amerykaSpolka.setTicker(stara.getTicker());
//        amerykaSpolka.setIndustry(stara.getIndustry());
//        amerykaSpolka.setNote(stara.getNote());
//        amerykaSpolki.add(stara);
//        stara = amSpRepository.findById(50L).get();
//        stara.setId(null);
//        amerykaSpolki.add(stara);
//        System.out.println(amerykaSpolki);
//        robert.setAmerykaSpolki(amerykaSpolki);
//        custoRepository.save(robert);

        // relacja z dodatkową tabelą Maintable
        WszystkieDane wszystkieDane = new WszystkieDane();

//        AmerykaSpolka amerykaSpolka = amSpRepository.findById(1L).get();
//        amerykaSpolki.add(amSpRepository.findById(1L).get());
//        amerykaSpolki.add(amSpRepository.findById(3L).get());
//        amerykaSpolki.add(amSpRepository.findById(4L).get());
//            amerykaSpolka.setId(null);
        wszystkieDane.setCustomer(robert);
        wszystkieDane.setWidoczny(true);
        wszystkieDane.setNotatka("");
        System.out.println(wszystkieDane);
//        Long i = wszysDaneRepository.count();
//        ((List<AmerykaSpolka>)amSpRepository.findAll()).stream().forEach(amerykaSpolka -> {
//            wszystkieDane.setAmerykaSpolka(amerykaSpolka);
//            wszystkieDane.setId(null);
////            System.out.println(wszystkieDane);
//            wszysDaneRepository.save(wszystkieDane);
//        });
//        log.info("Zrobione!!!");

//        AmerykaSpolka amerykaSpolka = new AmerykaSpolka();
//        Long j =1L;
//        for (int i = 1; i < amSpRepository.count()+1; i++) {
//            amerykaSpolka.setId(j++);
//            wszystkieDane.setAmerykaSpolka(amerykaSpolka);
//            wszystkieDane.setId(null);
//            wszysDaneRepository.save(wszystkieDane);
//        }
//        -----------------------------------------------------------------------------
//        queryRepository.copyNewCustomer();
//
//        queryRepository.changeIdNewCustomer(2L);

//        queryRepository.setView("Robert", 3L);
//        log.info("zmieniłem na 2");  //-----------------------------------------------

//        long startTime = System.nanoTime();
        List<WszystkieDane> wszystkieDanes = queryRepository.getDaneView("Robert");
        System.out.println(wszystkieDanes.get(0).getAmerykaSpolka());
//        System.out.println(wszystkieDanes.get(0).getAmerykaSpolka());
//        System.out.println(wszystkieDanes.get(0).getCustomer());
//        long executionTime = System.nanoTime() - startTime;
//        log.info("Zajęło mi to: " + (executionTime / 1000000000) + "s");
    }
}
