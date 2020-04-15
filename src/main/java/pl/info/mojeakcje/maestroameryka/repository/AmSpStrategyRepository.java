package pl.info.mojeakcje.maestroameryka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaStrategia;


@Repository
public interface AmSpStrategyRepository extends CrudRepository<AmerykaSpolkaStrategia, Long> {
//    void createTables(String nameTable);
//    void saveAllToTable(String nameTable, Iterable<AmerykaSpolka> amerykaSpolki) throws InterruptedException;
}
