package pl.info.mojeakcje.maestroameryka.model.modelCustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {


    private String name;
    private Long idCU;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdCU() {
        return idCU;
    }

    public void setIdCU(Long idCU) {
        this.idCU = idCU;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "name='" + name + '\'' +
                ", idCU=" + idCU +
                '}';
    }
}
