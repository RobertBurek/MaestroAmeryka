package pl.info.mojeakcje.maestroameryka.model.modelCustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NewUserDetailsService implements UserDetailsService {

    @Autowired
    private CustoRepository custoRepository;


    public UserDetails loadUserByUsername(String nick)
            throws UsernameNotFoundException {

        Customer customer = custoRepository.findByNickCustomer(nick);
        if (customer == null) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + nick);
        }
        List<String> roles = new ArrayList<>();
        roles.add(RoleCustomer.ROLE_USER.toString());
        return new org.springframework.security.core.userdetails.User
                (customer.getNickCustomer(),
                        customer.getPassword().toLowerCase(),
                        getAuthorities(roles));
    }

    private static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}

