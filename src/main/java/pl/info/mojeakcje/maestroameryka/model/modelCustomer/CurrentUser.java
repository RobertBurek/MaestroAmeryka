package pl.info.mojeakcje.maestroameryka.model.modelCustomer;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {


    private String name;
    private String ipCU;
    private String pass;

    public String currentUserName() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpCU() {
        return ipCU;
    }

    public void setIpCU(String ipCU) {
        this.ipCU = ipCU;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "name='" + name + '\'' +
                ", ipCU='" + ipCU + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
