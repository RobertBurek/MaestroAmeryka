package pl.info.mojeakcje.maestroameryka;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.info.mojeakcje.maestroameryka.model.modelCustomer.CurrentUser;

@Aspect
@Component
public class MyAspect {

    CurrentUser currentUser;

    public MyAspect(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    //    @Before("execution(public * *(..))")
    @Before("within(pl.info.mojeakcje.maestroameryka.controller.*)")
    public void doThisBefore() {
        System.out.print(currentUser.currentUserName() + ": ");
    }

    @After("within(pl.info.mojeakcje.maestroameryka.controller.*)")
    public void doAlwaysAfterServices() {
        //po zakończeniu (poprawnym lub z rzuceniu wyjątku) dowolnej
        //metody z klas w pakiecie pl.javastart.service

    }
}
