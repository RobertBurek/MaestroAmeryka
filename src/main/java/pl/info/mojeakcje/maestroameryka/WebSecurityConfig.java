package pl.info.mojeakcje.maestroameryka;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.NewUserDetailsService;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.UserDetailsServiceImpl;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;
import pl.info.mojeakcje.maestroameryka.repository.WszysDaneRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    private UserDetailsServiceImpl userDetailsService;
    private CustoRepository custoRepository;
    private AmSpRepository amSpRepository;
    private WszysDaneRepository wszysDaneRepository;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, CustoRepository custoRepository, AmSpRepository amSpRepository, WszysDaneRepository wszysDaneRepository) {
        this.userDetailsService = userDetailsService;
        this.custoRepository = custoRepository;
        this.amSpRepository = amSpRepository;
        this.wszysDaneRepository = wszysDaneRepository;
    }

    @Autowired
    NewUserDetailsService newUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).getUserDetailsService();
        auth.userDetailsService(newUserDetailsService).getUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/amerykaspolka").permitAll()
                .antMatchers("/czytajDane").permitAll()
                .antMatchers("/amerykastrategie").hasRole("USER")
                .antMatchers("/amerykaspolka/show").hasRole("USER")
                .antMatchers("/usunSpolke/{id}&{ticker}").hasRole("ADMIN")
                .antMatchers("/odczytdanychzplikucsv/{nameFile}").hasRole("ADMIN")
                .antMatchers("/naprawWidoki").hasRole("ADMIN")
                .antMatchers("/customers").hasRole("ADMIN")
                .antMatchers("/customers/{id_customer}").hasRole("ADMIN")
                .antMatchers("/napraw").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/loginMaestro").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/amerykaspolka")
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .headers().disable();
    }
}
