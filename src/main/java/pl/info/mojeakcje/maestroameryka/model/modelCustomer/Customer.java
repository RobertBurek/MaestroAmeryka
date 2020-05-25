package pl.info.mojeakcje.maestroameryka.model.modelCustomer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;
    private String nickCustomer;
    private String passwordCustomer;
    private String roleCustomer;
    private String ipCustomer;
//    @OneToMany(targetEntity = AmerykaSpolka.class, cascade =CascadeType.ALL) //mappedBy = "customer")
//    @JoinColumn(name = "customer_id", referencedColumnName = "id")
//    private List<AmerykaSpolka> amerykaSpolki;


    public Customer(String nickCustomer, String passwordCustomer, String roleCustomer, String ipCustomer) {
        this.nickCustomer = nickCustomer;
        this.passwordCustomer = passwordCustomer;
        this.roleCustomer = roleCustomer;
        this.ipCustomer = ipCustomer;
    }

    public Customer() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(roleCustomer));
    }

    @Override
    public String getPassword() {
        return passwordCustomer;
    }

    @Override
    public String getUsername() {
        return nickCustomer;
    }

    @Override
    public boolean isAccountNonExpired() {
//        Można rozbudować w bazie.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        Można rozbudować w bazie.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        Można rozbudować w bazie.
        return true;
    }

    @Override
    public boolean isEnabled() {
//        Można rozbudować w bazie.
        return true;
    }

    public Long getId() {
        return idCustomer;
    }

    public void setId(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNickCustomer() {
        return nickCustomer;
    }

    public void setNickCustomer(String nickCustomer) {
        this.nickCustomer = nickCustomer;
    }

    public String getPasswordCustomer() {
        return passwordCustomer;
    }

    public void setPasswordCustomer(String passwordCustomer) {
        this.passwordCustomer = passwordCustomer;
    }

    public String getRoleCustomer() {
        return roleCustomer;
    }

    public void setRoleCustomer(String roleCustomer) {
        this.roleCustomer = roleCustomer;
    }

    public String getIpCustomer() {
        return ipCustomer;
    }

    public void setIpCustomer(String ipCustomer) {
        this.ipCustomer = ipCustomer;
    }

//    public List<AmerykaSpolka> getAmerykaSpolki() {
//        return amerykaSpolki;
//    }
//
//    public void setAmerykaSpolki(List<AmerykaSpolka> amerykaSpolki) {
//        this.amerykaSpolki = amerykaSpolki;
//    }


    @Override
    public String toString() {
        return "Customer{" +
                "idCustomer=" + idCustomer +
                ", nickCustomer='" + nickCustomer + '\'' +
                ", passwordCustomer='" + passwordCustomer + '\'' +
                ", roleCustomer='" + roleCustomer + '\'' +
                ", ipCustomer='" + ipCustomer + '\'' +
                '}';
    }
}
