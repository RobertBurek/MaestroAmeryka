package pl.info.mojeakcje.maestroameryka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaNew;

@Repository
public interface AmSpNewRepository extends CrudRepository<AmerykaSpolkaNew,Long> {
}
