package pl.info.mojeakcje.maestroameryka.model;

import org.springframework.stereotype.Component;

@Component
public class ShowSpolka {

    public static Boolean show = false;

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}
