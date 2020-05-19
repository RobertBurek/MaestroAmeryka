package pl.info.mojeakcje.maestroameryka.controller;


import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaNew;
import pl.info.mojeakcje.maestroameryka.repository.AmSpNewRepository;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
public class AmSpNewControllerRest {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    AmSpNewRepository amSpNewRepository;
    AmSpRepository amSpRepository;

    public AmSpNewControllerRest(AmSpNewRepository amSpNewRepository, AmSpRepository amSpRepository) {
        this.amSpNewRepository = amSpNewRepository;
        this.amSpRepository = amSpRepository;
    }

    @GetMapping("/modyfikuj")
    public String modyfikuj() {

        for (long i = 1; i < 50; i++) {

            AmerykaSpolka amerykaSpolka = amSpRepository.findById(i).get();
            String url = "https://finance.yahoo.com/quote/" + amerykaSpolka.getTicker() + "/profile?p=" + amerykaSpolka.getTicker();
            RestTemplate restTemplate = new RestTemplate();
            String values = restTemplate.getForObject(url, String.class);
            String odpowiedniaLinia = values.substring(values.lastIndexOf("website") + 10, values.lastIndexOf("website") + 200);
//            System.out.println(odpowiedniaLinia);
            if (odpowiedniaLinia.startsWith("http")) {
                System.out.println(odpowiedniaLinia.substring(0, odpowiedniaLinia.indexOf("maxAge") - 3).replace("u002F", "").replace("\\\\", "//"));
            }else System.out.println("#brak");
        }



        return "Zrobine!!!";
    }
}
