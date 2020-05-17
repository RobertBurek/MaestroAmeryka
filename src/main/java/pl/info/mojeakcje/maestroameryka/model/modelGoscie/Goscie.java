package pl.info.mojeakcje.maestroameryka.model.modelGoscie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Goscie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private LocalDate infoKiedy;
        private LocalTime infoOKtorej;
        private String infoKto;
        private String infoMiasto;
        private String InfoKraj;

    public Goscie() {
    }

    public Goscie(LocalDate infoKiedy, LocalTime infoOKtorej, String infoKto, String infoMiasto, String infoKraj) {
        this.infoKiedy = infoKiedy;
        this.infoOKtorej = infoOKtorej;
        this.infoKto = infoKto;
        this.infoMiasto = infoMiasto;
        InfoKraj = infoKraj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInfoKiedy() {
        return infoKiedy;
    }

    public void setInfoKiedy(LocalDate infoKiedy) {
        this.infoKiedy = infoKiedy;
    }

    public LocalTime getInfoOKtorej() {
        return infoOKtorej;
    }

    public void setInfoOKtorej(LocalTime infoOKtorej) {
        this.infoOKtorej = infoOKtorej;
    }

    public String getInfoKto() {
        return infoKto;
    }

    public void setInfoKto(String infoKto) {
        this.infoKto = infoKto;
    }

    public String getInfoMiasto() {
        return infoMiasto;
    }

    public void setInfoMiasto(String infoMiasto) {
        this.infoMiasto = infoMiasto;
    }

    public String getInfoKraj() {
        return InfoKraj;
    }

    public void setInfoKraj(String infoKraj) {
        InfoKraj = infoKraj;
    }
}
