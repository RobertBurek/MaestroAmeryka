package pl.info.mojeakcje.maestroameryka.model.modelCustomer;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.info.mojeakcje.maestroameryka.model.ShowSpolka;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());


    private CustoRepository custoRepository;
    private CurrentUser currentUser;
    private QueryRepository queryRepository;
    ShowSpolka showSpolka;

    public UserDetailsServiceImpl(CustoRepository custoRepository, CurrentUser currentUser, QueryRepository queryRepository, ShowSpolka showSpolka) {
        this.custoRepository = custoRepository;
        this.currentUser = currentUser;
        this.queryRepository = queryRepository;
        this.showSpolka = showSpolka;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info(ANSI_MORSKI + ANSI_BOLD + "Próba logowania przez: " + ANSI_BLUEE + s + ANSI_RESET);
        UserDetails byNickCustomer = custoRepository.findByNickCustomer(s);
        UserDetails userDetailsNew;
        if (byNickCustomer == null){
            throw new UsernameNotFoundException(
                    "No user found with username: "+ s);
        }
        if (byNickCustomer.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            log.info("Jestem adminem!!!");
            userDetailsNew = User.builder()
                    .username(byNickCustomer.getUsername())
                    .password(byNickCustomer.getPassword())
                    .authorities("ROLE_ADMIN", "ROLE_USER")
                    .build();
            byNickCustomer = userDetailsNew;
        }
        return byNickCustomer;
    }

}
