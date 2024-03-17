import calculatorPolinoame.Monom;
import calculatorPolinoame.Polynom;

import static org.junit.Assert.*;

public class PolynomTest {


    @org.junit.Test
    public void add() {
        Polynom p1 = new Polynom();
        p1.addMonomial(new Monom(1, 2)); // 2x
        p1.addMonomial(new Monom(0, 3)); // + 3

        Polynom p2 = new Polynom();
        p2.addMonomial(new Monom(1, 3)); // 3x
        p2.addMonomial(new Monom(0, -1)); // - 1

        Polynom expected = new Polynom();
        expected.addMonomial(new Monom(1, 5)); // 5x
        expected.addMonomial(new Monom(0, 2)); // + 2

        Polynom result = p1.add(p2);
        assertEquals("Addition Result", expected.toString(), result.toString());
    }

    @org.junit.Test
    public void subtract() {
        Polynom p1 = new Polynom();
        p1.addMonomial(new Monom(1, 2)); // 2x
        p1.addMonomial(new Monom(0, 3)); // + 3

        Polynom p2 = new Polynom();
        p2.addMonomial(new Monom(1, 3)); // 3x
        p2.addMonomial(new Monom(0, -1)); // - 1

        Polynom expected = new Polynom();
        expected.addMonomial(new Monom(1, -1)); // -x
        expected.addMonomial(new Monom(0, 4)); // + 4

        Polynom result = p1.subtract(p2);
        assertEquals("Substraction Result", expected.toString(), result.toString());
    }

    @org.junit.Test
    public void multiply() {
        Polynom p1 = new Polynom();
        p1.addMonomial(new Monom(1, 2)); // 2x
        p1.addMonomial(new Monom(0, 3)); // + 3

        Polynom p2 = new Polynom();
        p2.addMonomial(new Monom(1, 3)); // 3x
        p2.addMonomial(new Monom(0, -1)); // - 1

        Polynom expected = new Polynom();
        expected.addMonomial(new Monom(2, 6)); // 6x^2
        expected.addMonomial(new Monom(1, 7)); // -2+9
        expected.addMonomial(new Monom(0, -3)); // -3

        Polynom result = p1.multiply(p2);
        assertEquals("Multiply Result", expected.toString(), result.toString());
    }

    @org.junit.Test
    public void derivative() {
        Polynom p1 = new Polynom();
        p1.addMonomial(new Monom(1, 2)); // 2x
        p1.addMonomial(new Monom(0, 3)); // + 3

        Polynom expected = new Polynom();
        expected.addMonomial(new Monom(0, 2)); // 2
        Polynom result = p1.derivative();
        assertEquals("Derivate Result", expected.toString(), result.toString());
    }

    @org.junit.Test
    public void integrate() {
        Polynom p1 = new Polynom();
        p1.addMonomial(new Monom(1, 2)); // 2x
        p1.addMonomial(new Monom(0, 3)); // + 3

        Polynom expected = new Polynom();
        expected.addMonomial(new Monom(1, 3)); // 3x
        expected.addMonomial(new Monom(2, 1)); // x^2
        Polynom result = p1.integrate();
        assertEquals("Integrate Result", expected.toString(), result.toString());
    }

    @org.junit.Test
    public void divide() {
        Polynom p1 = new Polynom();  //x^3-2x^2+6x-5
        p1.addMonomial(new Monom(3, 1));
        p1.addMonomial(new Monom(2, -2));
        p1.addMonomial(new Monom(1, 6));
        p1.addMonomial(new Monom(0, -5));

        Polynom p2 = new Polynom();  //x^2-1
        p2.addMonomial(new Monom(2, 1));
        p2.addMonomial(new Monom(0, -1));

        Polynom expectedQuotient = new Polynom();
        expectedQuotient.addMonomial(new Monom(1, 1)); // x
        expectedQuotient.addMonomial(new Monom(0, -2)); // -2

        Polynom expectedRemainder = new Polynom();
        expectedRemainder.addMonomial(new Monom(1, 7)); // 7x
        expectedRemainder.addMonomial(new Monom(0, -7)); // -7

        Polynom[] result = p1.divide(p2);
        assertEquals("Quotient Result", expectedQuotient.toString(), result[0].toString());
        assertEquals("Remainder Result", expectedRemainder.toString(), result[1].toString());

    }
}