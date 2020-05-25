package pl.info.mojeakcje.maestroameryka.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.info.mojeakcje.maestroameryka.model.WszystkieDane;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface WszysDaneRepository extends CrudRepository<WszystkieDane, Long> {


//    @Query("INSERT INTO wszystkie_dane SELECT * FROM nowy_customer")
//    void copyNewCustomer();

//    @Query("UPDATE wszystkie_dane SET cust_id = 500 WHERE wszystkie_dane.id = 1")
//    void changeIdNewCustomer(Long id);
}
