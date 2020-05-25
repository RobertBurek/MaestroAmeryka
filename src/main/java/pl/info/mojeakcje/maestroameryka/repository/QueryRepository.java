package pl.info.mojeakcje.maestroameryka.repository;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.info.mojeakcje.maestroameryka.model.WszystkieDane;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Log4j2
@Repository
public class QueryRepository {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;

    //    @Query("UPDATE wszystkie_dane SET cust_id = 500 WHERE wszystkie_dane.id = 1")
//    void changeIdNewCustomer(Long id);

    //    @Query("INSERT INTO wszystkie_dane SELECT * FROM nowy_customer")
//    void copyNewCustomer();

    @Transactional
    public void changeIdNewCustomer(Long id) {
        entityManager.createNativeQuery("UPDATE wszystkie_dane SET cust_id = ? WHERE wszystkie_dane.cust_id = 1")
                .setParameter(1, id)
                .executeUpdate();
    }

    @Transactional
    public void copyNewCustomer() {
        entityManager.createNativeQuery("INSERT INTO `MaestroAmerykaTeraz`.`wszystkie_dane`( `notatka`, `widoczny`, `spolka_id`, `cust_id`) SELECT `notatka`, `widoczny`, `spolka_id`, `cust_id` FROM `MaestroAmerykaTeraz`.`nowy_customer`")
                .executeUpdate();
    }


    @Transactional
    public List<WszystkieDane> getDane(Long idCustomer) {
//        CREATE VIEW widok AS

        return entityManager.createNativeQuery("SELECT * FROM wszystkie_dane INNER JOIN customer\n" +
                "ON customer.id_customer = wszystkie_dane.cust_id INNER JOIN ameryka_spolka\n" +
                "ON ameryka_spolka.id = wszystkie_dane.spolka_id\n" +
                "WHERE wszystkie_dane.cust_id= ?", WszystkieDane.class)
                .setParameter(1, idCustomer)
//                .executeUpdate();
                .getResultList();
    }


    @Transactional
    public void setView(String name, Long idCustomer) {
        entityManager.createNativeQuery("CREATE or replace VIEW " + name + " AS SELECT * FROM wszystkie_dane INNER JOIN customer " +
                "ON customer.id_customer = wszystkie_dane.cust_id INNER JOIN ameryka_spolka " +
                "ON ameryka_spolka.id_spolka = wszystkie_dane.spolka_id " +
                "WHERE wszystkie_dane.cust_id= ?;")
                .setParameter(1, idCustomer)
                .executeUpdate();
        log.info("Ustawiłem vidok dla: " + name + "  nr: " + idCustomer);
    }

    @Transactional
    public List<WszystkieDane> getDaneView(String name) {
        long startTime = System.nanoTime();
        List<WszystkieDane> wszystkieDanes= entityManager.createNativeQuery("SELECT * FROM " + name)
//        return entityManager.createQuery("SELECT WszystkieDane FROM " + name)
//                .setParameter(1, name)
//                .executeUpdate();

                .getResultList();
        long executionTime = System.nanoTime() - startTime;
        log.info("Zajęło mi to: " + (executionTime / 1000000000) + "s");
        return wszystkieDanes;
//        return entityManager.createNativeQuery("SELECT * FROM " + name, WszystkieDane.class)
////        return entityManager.createQuery("SELECT WszystkieDane FROM " + name)
////.setMaxResults(20)
////                .setParameter(1, name)
////                .executeUpdate();
//                .getResultList();
    }

//    INSERT INTO `MaestroAmerykaTeraz`.`wszystkie_dane`( `notatka`, `widoczny`, `spolka_id`, `cust_id`) SELECT `notatka`, `widoczny`, `spolka_id`, `cust_id` FROM `MaestroAmerykaTeraz`.`nowy_customer`
}
