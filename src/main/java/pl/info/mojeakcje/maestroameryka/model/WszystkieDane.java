package pl.info.mojeakcje.maestroameryka.model;

import pl.info.mojeakcje.maestroameryka.model.modelCustomer.Customer;

import javax.persistence.*;
import java.util.List;

@Entity
public class WszystkieDane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "cust_id", referencedColumnName = "idCustomer")
    private Customer customer;
//        @OneToMany//targetEntity = AmerykaSpolka.class, cascade = CascadeType.ALL //mappedBy = "customer")
//    @JoinColumn(name = "spolka_id", referencedColumnName = "id")
//    private List<AmerykaSpolka> amerykaSpolki;
    @OneToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "spolka_id", referencedColumnName = "idSpolka")
    private AmerykaSpolka amerykaSpolka;
    private String notatka;
    private Boolean widoczny;

    public WszystkieDane() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

//    public List<AmerykaSpolka> getAmerykaSpolki() {
//        return amerykaSpolki;
//    }
//
//    public void setAmerykaSpolki(List<AmerykaSpolka> amerykaSpolki) {
//        this.amerykaSpolki = amerykaSpolki;
//    }

        public AmerykaSpolka getAmerykaSpolka() {
        return amerykaSpolka;
    }

    public void setAmerykaSpolka(AmerykaSpolka amerykaSpolka) {
        this.amerykaSpolka = amerykaSpolka;
    }

    public String getNotatka() {
        return notatka;
    }

    public void setNotatka(String notatka) {
        this.notatka = notatka;
    }

    public Boolean getWidoczny() {
        return widoczny;
    }

    public void setWidoczny(Boolean widoczny) {
        this.widoczny = widoczny;
    }

    @Override
    public String toString() {
        return "WszystkieDane{" +
                "id=" + id +
                ", customer=" + customer +
                ", amerykaSpolka=" + amerykaSpolka +
                ", notatka='" + notatka + '\'' +
                ", widoczny=" + widoczny +
                '}';
    }
}
