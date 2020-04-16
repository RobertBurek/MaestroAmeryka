package pl.info.mojeakcje.maestroameryka.service;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolka;
import pl.info.mojeakcje.maestroameryka.model.AmerykaSpolkaStrategia;
import pl.info.mojeakcje.maestroameryka.repository.AmSpRepository;
import pl.info.mojeakcje.maestroameryka.repository.AmSpStrategyRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

@Log4j2

public class CreateStrategy {

//    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
//
//    AmSpRepository amSpRepository;
//    AmSpStrategyRepository amSpStrategyRepository;
//
//    public CreateStrategy(AmSpRepository amSpRepository, AmSpStrategyRepository amSpStrategyRepository) {
//        this.amSpRepository = amSpRepository;
//        this.amSpStrategyRepository = amSpStrategyRepository;
//        amerykaSpolkiStretegie = ((List<AmerykaSpolka>) amSpRepository.findAll())
//                .stream()
//                .map(new Function<AmerykaSpolka, AmerykaSpolkaStrategia>() {
//                    @Override
//                    public AmerykaSpolkaStrategia apply(AmerykaSpolka amerykaSpolka) {
//                        AmerykaSpolkaStrategia amerykaSpolkaStrategiaNew = new AmerykaSpolkaStrategia(amerykaSpolka.getTicker(), amerykaSpolka.getName(), amerykaSpolka.getMarket(), amerykaSpolka.getSector(), amerykaSpolka.getIndustry());
//                        Double endYTD = 0.0;
//                        Double startYTD = 0.0;
//                        Double start1MTD = 0.0;
//                        Double start2MTD = 0.0;
//                        if (!amerykaSpolka.getDay0320().replaceAll(" ", "").equals("null"))
//                            endYTD = Double.parseDouble(amerykaSpolka.getDay0320().replaceAll(" ", ""));
//                        if (!amerykaSpolka.getDay0319().replaceAll(" ", "").equals("null"))
//                            startYTD = Double.parseDouble(amerykaSpolka.getDay0319().replaceAll(" ", ""));
//                        if (!amerykaSpolka.getDay0220().replaceAll(" ", "").equals("null"))
//                            start1MTD = Double.parseDouble(amerykaSpolka.getDay0220().replaceAll(" ", ""));
//                        if (!amerykaSpolka.getDay0120().replaceAll(" ", "").equals("null"))
//                            start2MTD = Double.parseDouble(amerykaSpolka.getDay0120().replaceAll(" ", ""));
//                        Double yTD = 0.0;
//                        Double m1TD = 0.0;
//                        Double m2TD = 0.0;
//                        if (startYTD != 0.0) {
//                            yTD = (endYTD - startYTD) * 100 / startYTD;
//                            amerykaSpolkaStrategiaNew.setyTD(String.format("%.1f", yTD));
//                        } else amerykaSpolkaStrategiaNew.setyTD("brak");
//                        if (start1MTD != 0.0) {
//                            m1TD = (endYTD - start1MTD) * 100 / start1MTD;
//                            amerykaSpolkaStrategiaNew.setM1TD(String.format("%.1f", m1TD));
//                        } else amerykaSpolkaStrategiaNew.setM1TD("brak");
//                        if (start2MTD != 0.0) {
//                            m2TD = (endYTD - start2MTD) * 100 / start2MTD;
//                            amerykaSpolkaStrategiaNew.setM2TD(String.format("%.1f", m2TD));
//                        } else amerykaSpolkaStrategiaNew.setM2TD("brak");
//                        log.info(ANSI_BLUE + amerykaSpolkaStrategiaNew.getTicker() + ANSI_YELLOW + "  " + amerykaSpolkaStrategiaNew.getyTD()
//                                + "  " + amerykaSpolkaStrategiaNew.getM1TD() + " " + amerykaSpolkaStrategiaNew.getM2TD() + ANSI_RESET);
//                        return amerykaSpolkaStrategiaNew;
//                    }
//                })
////                .map(amerykaSpolka -> new AmerykaSpolkaStrategia(amerykaSpolka.getTicker(), amerykaSpolka.getName(), amerykaSpolka.getMarket(), amerykaSpolka.getSector(), amerykaSpolka.getIndustry()))
//                .collect(Collectors.toList());
//        amSpStrategyRepository.saveAll(amerykaSpolkiStretegie);
//        log.info(ANSI_BLUE + " Zrobiłem !!! Mapowanie na Strategie !!!");
//    }
}
