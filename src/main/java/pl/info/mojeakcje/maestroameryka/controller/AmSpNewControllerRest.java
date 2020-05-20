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

import java.util.Random;

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
    public String modyfikuj() throws InterruptedException {
        AmerykaSpolka amerykaSpolka;
        AmerykaSpolkaNew amerykaSpolkaNew;
//        for (long i = 1; i < amSpRepository.count(); i++) {
            amerykaSpolka = amSpRepository.findById(788L).get();
            amerykaSpolkaNew = new AmerykaSpolkaNew(amerykaSpolka.getTicker()
                    , amerykaSpolka.getName()
                    , amerykaSpolka.getMarket()
                    , amerykaSpolka.getSector()
                    , amerykaSpolka.getIndustry()
                    , amerykaSpolka.getNote());
            amerykaSpolkaNew.setCourseCurrent(amerykaSpolka.getCourseCurrent());
            amerykaSpolkaNew.setCourse1M(amerykaSpolka.getCourse1M());
            amerykaSpolkaNew.setCourse3M(amerykaSpolka.getCourse3M());
            amerykaSpolkaNew.setCourse12M(amerykaSpolka.getCourse12M());
            amerykaSpolkaNew.setCourseYTD(amerykaSpolka.getCourseYTD());
            amerykaSpolkaNew.setDayCourseCurrent(amerykaSpolka.getDayCourseCurrent());
            amerykaSpolkaNew.setDay1M(amerykaSpolka.getDay1M());
            amerykaSpolkaNew.setDay3M(amerykaSpolka.getDay3M());
            amerykaSpolkaNew.setDay12M(amerykaSpolka.getDay12M());
            amerykaSpolkaNew.setDayYTD(amerykaSpolka.getDayYTD());
            amerykaSpolkaNew.setM1(amerykaSpolka.getM1());
            amerykaSpolkaNew.setM3(amerykaSpolka.getM3());
            amerykaSpolkaNew.setM12(amerykaSpolka.getM12());
            amerykaSpolkaNew.setyTD(amerykaSpolka.getyTD());

            String url = "https://finance.yahoo.com/quote/" + amerykaSpolka.getTicker() + "/profile?p=" + amerykaSpolka.getTicker();
            RestTemplate restTemplate = new RestTemplate();
            String values = restTemplate.getForObject(url, String.class);
            String odpowiedniaLinia = values.substring(values.lastIndexOf("website") + 10, values.lastIndexOf("website") + 200);
            String webSite = "";
            if (odpowiedniaLinia.startsWith("http")) {
                webSite = odpowiedniaLinia.substring(0, odpowiedniaLinia.indexOf("maxAge") - 3).replace("u002F", "").replace("\\\\", "//");
            } else {
                webSite = "#brak";
            }
            amerykaSpolkaNew.setWebsite(webSite);
            System.out.println(amerykaSpolka.getId() + "  " + webSite);
            amSpNewRepository.save(amerykaSpolkaNew);
            int millis = new Random().nextInt(400) + 300;
            Thread.sleep(millis);
//        }

        return "Zrobine!!!";
    }
}
