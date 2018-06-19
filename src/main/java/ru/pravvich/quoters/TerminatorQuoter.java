package ru.pravvich.quoters;

import javax.annotation.PostConstruct;

/**
 * @author Pavel Ravvich.
 */
@Profiling
public class TerminatorQuoter implements Quoter {
    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    private String massage;

    @PostConstruct
    public void init() {
        System.out.println("Phase 2");
        System.out.println(repeat);
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public TerminatorQuoter() {
        System.out.println("Phase 1");
        System.out.println(repeat);
    }

    @Override
    @PostProxy
    public void sayQuote() {
        System.out.println("Phase 3");
        for (int i = 0; i < repeat; i++) {
            System.out.println("Massage = " + massage);
        }
    }
}
