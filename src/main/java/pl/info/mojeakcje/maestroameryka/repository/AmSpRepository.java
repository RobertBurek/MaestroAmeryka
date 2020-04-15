package pl.info.mojeakcje.maestroameryka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;


@Repository
public interface AmSpRepository extends CrudRepository<AmerykaSpolka, Long> {
//    void createTables(String nameTable);
//    void saveAllToTable(String nameTable, Iterable<AmerykaSpolka> amerykaSpolki) throws InterruptedException;
}
