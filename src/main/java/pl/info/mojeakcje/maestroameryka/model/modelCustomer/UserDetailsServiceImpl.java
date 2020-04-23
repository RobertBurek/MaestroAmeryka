package pl.info.mojeakcje.maestroameryka.model.modelCustomer;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    private CustoRepository custoRepository;

    @Autowired
    public UserDetailsServiceImpl(CustoRepository custoRepository) {
        this.custoRepository = custoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info(ANSI_MORSKI + ANSI_BOLD + "Próba logowania przez: " + ANSI_BLUEE + s + ANSI_RESET);
        UserDetails byNickCustomer = custoRepository.findByNickCustomer(s);
        UserDetails userDetailsNew;
        if (byNickCustomer.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            log.info("Jestem adminem!!!");
            userDetailsNew = User.builder()
                    .username(byNickCustomer.getUsername())
                    .password(byNickCustomer.getPassword())
                    .authorities("ROLE_ADMIN", "ROLE_USER")
                    .build();
            byNickCustomer = userDetailsNew;
        }
//            log.info(byNickCustomer.getAuthorities().toString());
//            log.info("" + byNickCustomer.getAuthorities().size());
//            log.info(byNickCustomer.getPassword());
//            log.info(byNickCustomer.getUsername());
        return byNickCustomer;
    }

}
