package pl.info.mojeakcje.maestroameryka.model.modelCustomer;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.info.mojeakcje.maestroameryka.repository.CustoRepository;

@Log4j2
@Service
public class CustomerService implements IUserService {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustoRepository custoRepository;

    @Autowired
    CurrentUser currentUser;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Transactional
    @Override
    public Customer registerNewUserAccount(CustomerDto customerDto) throws Exception {
        if (customerExists(customerDto.getNickCustomer())) {
            log.info("Użytkownik o takim nick już istnieje: " + customerDto.getNickCustomer());
            throw new Exception(
                    "There is an account with that nick: "
                            + customerDto.getNickCustomer());
        }
        Customer customer = new Customer();
        customer.setNickCustomer(customerDto.getNickCustomer());
        customer.setPasswordCustomer(passwordEncoder().encode(customerDto.getPasswordCustomer()));
        currentUser.setName(customerDto.getNickCustomer());
        currentUser.setPass(customerDto.getPasswordCustomer());
        customer.setRoleCustomer("ROLE_USER");
        customer.setIpCustomer(currentUser.getIpCU());
        log.info("Zapisano nowego użytkownika: " + customer.getNickCustomer());
            return custoRepository.save(customer);
    }

    private boolean customerExists(String nickCustomer) {
        return custoRepository.findByNickCustomer(nickCustomer) != null;
    }
}

interface IUserService {
    Customer registerNewUserAccount(CustomerDto customerDto) throws Exception;
}
