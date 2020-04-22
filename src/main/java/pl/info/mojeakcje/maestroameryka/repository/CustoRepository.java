package pl.info.mojeakcje.maestroameryka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.Customer;


@Repository
public interface CustoRepository extends CrudRepository<Customer,Long> {

    Customer findByNickCustomer(String nick);
}
