package ru.pravvich.quoters;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @author Pavel Ravvich.
 */
public class TerminatorQuoterTest {
    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

    @Test
    public void terminatorQuoterTest() {
        context.getBean(Quoter.class).sayQuote();
    }

    @Test
    public void profilingTest() throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
            context.getBean(Quoter.class).sayQuote();
        }
    }

    @Test
    public void transactionCashInitTest() {

    }
}