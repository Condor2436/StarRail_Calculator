package zhibo;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ExpCalculator calculator = new ExpCalculator();
        System.out.println(calculator.calculate(1,70,62,212,75, 4));
    }
}
