package pl.info.mojeakcje.maestroameryka.model.modelCustomer;


public class CustomerDto {

    private String nickCustomer;
    private String passwordCustomer;
    private String matchPasswordCustomer;


    public CustomerDto() {
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

    public String getMatchPasswordCustomer() {
        return matchPasswordCustomer;
    }

    public void setMatchPasswordCustomer(String matchPasswordCustomer) {
        this.matchPasswordCustomer = matchPasswordCustomer;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "nickCustomer='" + nickCustomer + '\'' +
                ", passwordCustomer='" + passwordCustomer + '\'' +
                ", matchPasswordCustomer='" + matchPasswordCustomer + '\'' +
                '}';
    }
}
