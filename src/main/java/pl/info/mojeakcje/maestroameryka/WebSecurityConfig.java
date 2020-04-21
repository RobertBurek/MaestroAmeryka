package pl.info.mojeakcje.maestroameryka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
@Slf4j

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(new User("Admin", passwordEncoder().encode("Admin"), Collections.singleton(new SimpleGrantedAuthority("admin"))));
//                .withUser("Admin")
//                .password(passwordEncoder().encode("Admin"))
//                .roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/amerykastrategie")
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/loginMaestro");
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

}
