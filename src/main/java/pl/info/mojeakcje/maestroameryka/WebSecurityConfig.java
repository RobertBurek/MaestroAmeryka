package pl.info.mojeakcje.maestroameryka;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.Customer;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.RoleCustomer;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.UserDetailsServiceImpl;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
@Slf4j

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    private UserDetailsServiceImpl userDetailsService;
    private CustoRepository custoRepository;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, CustoRepository custoRepository) {
        this.userDetailsService = userDetailsService;
        this.custoRepository = custoRepository;
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
//                .antMatchers("/amerykaspolka/edit").hasRole("ADMIN")


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
//    @EventListener(ApplicationReadyEvent.class)
//    public void get() {
//        Customer customer1 = new Customer("Robert", passwordEncoder().encode("Robert10"), RoleCustomer.ADMIN.toString(), "168.1.21.1");
//        Customer customer2 = new Customer("Maciej", passwordEncoder().encode("Maciej10"), RoleCustomer.ADMIN.toString(), "165.12.2.21");
//        Customer customer3 = new Customer("Adam", passwordEncoder().encode("Adam10"), RoleCustomer.USER.toString(), "165.161.251.13");
//        Customer customer4 = new Customer("Nowy", passwordEncoder().encode("Nowy123"), RoleCustomer.USER.toString(), "125.14.214.54");
//        custoRepository.save(customer1);
//        custoRepository.save(customer2);
//        custoRepository.save(customer3);
//        custoRepository.save(customer4);
//    }
}
