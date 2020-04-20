package pl.info.mojeakcje.maestroameryka.model.modelCustomer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCustomer;
    private String nickCustomer;
    private String passwordCustomer;
    private String roleCustomer;
    private String ipCustomer;


    public Customer() {
    }




}
