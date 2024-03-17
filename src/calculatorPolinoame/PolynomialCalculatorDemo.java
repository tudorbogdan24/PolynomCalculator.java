package calculatorPolinoame;

import calculatorPolinoame.Monom;
import calculatorPolinoame.Polynom;

public class PolynomialCalculatorDemo {
    public static void main(String[] args) {
        //  -2x^4 + x^3 + 3x^2 + 2x + 1
        Polynom p1 = new Polynom();
        p1.addMonomial(new Monom(3, 1));
        //p1.addMonomial(new calculatorPolinoame.Monom(4, -2));
       // p1.addMonomial(new calculatorPolinoame.Monom(2, 3));
        p1.addMonomial(new Monom(2, -2));
        p1.addMonomial(new Monom(1, 6));
        p1.addMonomial(new Monom(0, -5));


        //  3x^4 - 3x^3+ 5x^2 - x + 2
        Polynom p2 = new Polynom();
        //p2.addMonomial(new calculatorPolinoame.Monom(3, -3));
        //p2.addMonomial(new calculatorPolinoame.Monom(4, 3));
        p2.addMonomial(new Monom(2, 1));
        //p2.addMonomial(new calculatorPolinoame.Monom(1, -1));
        p2.addMonomial(new Monom(0, -1));

       // p2.addMonomial(new calculatorPolinoame.Monom(0,-1));

        // +
        Polynom additionResult = p1.add(p2);
        System.out.println("Addition Result: " + additionResult);

        // -
        Polynom subtractionResult = p1.subtract(p2);
        System.out.println("Subtraction Result: " + subtractionResult);

        // *
        Polynom multiplicationResult = p1.multiply(p2);
        System.out.println("Multiplication Result: " + multiplicationResult);

        //derivata
        Polynom derivativeResult = p1.derivative();
        System.out.println("Derivation Result: " + derivativeResult);

        //integrala
        Polynom intefrateResult = p1.integrate();
        System.out.println("Integration Result: " + intefrateResult);

        //impartire
        Polynom[] divideResult = p1.divide(p2);
        Polynom quotient = divideResult[0];
        Polynom remainder = divideResult[1];
        System.out.println("Quotient: " + quotient);
        System.out.println("Remainder: " + remainder);
    }
}
