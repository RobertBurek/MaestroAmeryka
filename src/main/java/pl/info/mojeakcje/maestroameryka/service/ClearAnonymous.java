package pl.info.mojeakcje.maestroameryka.service;

import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.info.mojeakcje.maestroameryka.repository.QueryRepository;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import static pl.info.mojeakcje.maestroameryka.MaestroamerykaApplication.*;

@Log4j2
@Service
public class ClearAnonymous {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());


    QueryRepository queryRepository;

    public ClearAnonymous(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    // delay - minutes
    public void clearShowAnonymousUser(Long delay) {
        isDelay = true;
        LocalDateTime clearStart = LocalDateTime.now().plusMinutes(delay);
        log.info(ANSI_FIOLET + "Czyszczenie AnonymousUser odbędzie się o " + clearStart + "" + ANSI_RESET);
        TimerTask taskClear = new TimerTask() {
            @Override
            public void run() {
                queryRepository.clearSetupAnonymous();
                isDelay = false;
            }
        };
        Timer timer = new Timer();
        timer.schedule(taskClear, delay * 60 * 1000);
        queryRepository.findAllWszystkieDane("anonymousUser");
    }

}