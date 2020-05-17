package pl.info.mojeakcje.maestroameryka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.info.mojeakcje.maestroameryka.model.modelGoscie.Goscie;

@Repository
public interface GoscieRepository extends CrudRepository<Goscie,Long> {
}
