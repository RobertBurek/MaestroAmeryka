package pl.info.mojeakcje.maestroameryka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.info.mojeakcje.maestroameryka.model.SpolkaAmeryka;


@Repository
public interface SpAmRepository extends CrudRepository<SpolkaAmeryka, Long> {
//    void createTables(String nameTable);
//    void saveAllToTable(String nameTable, Iterable<AmerykaSpolka> amerykaSpolki) throws InterruptedException;
}
