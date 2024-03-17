package calculatorPolinoame;

public class Monom implements Comparable<Monom> {
    private int degree;
    private double coefficient;

    public Monom(int degree, double coefficient) {
        this.degree = degree;
        this.coefficient = coefficient;
    }

    public Monom multiply(Monom other) {
        return new Monom(this.degree + other.degree, this.coefficient * other.coefficient);
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }


    @Override
    public int compareTo(Monom other) {
        return Integer.compare(this.degree, other.degree);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
