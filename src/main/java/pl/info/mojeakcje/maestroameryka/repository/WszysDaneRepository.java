package pl.info.mojeakcje.maestroameryka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.info.mojeakcje.maestroameryka.model.WszystkieDane;

@Repository
public interface WszysDaneRepository extends CrudRepository<WszystkieDane, Long> {

}
