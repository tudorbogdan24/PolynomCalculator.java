package calculatorPolinoame;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynom {
    private TreeMap<Integer, Monom> monomials;

    public Polynom() {
        monomials = new TreeMap<>();
    }

    public Polynom(TreeMap<Integer, Monom> monomials) {
        this.monomials = new TreeMap<>();
        for (Map.Entry<Integer, Monom> entry : monomials.entrySet()) {
            this.monomials.put(entry.getKey(), new Monom(entry.getValue().getDegree(), entry.getValue().getCoefficient()));
        }
    }

    // polinomul este form din monomi, in aceasta functie se fromeaza polinomul
    public void addMonomial(Monom monomial) {
        // se aduna monomi cu aceeasi putere
        monomials.merge(monomial.getDegree(), monomial,
                (existingMonomial, newMonomial) -> new Monom(monomial.getDegree(),
                        existingMonomial.getCoefficient() + newMonomial.getCoefficient()));
    }

    public Polynom add(Polynom other) {
        Polynom result = new Polynom();
        // aceeasi putere se adauga din pol1
        for (Monom monomial : this.monomials.values()) {
            result.addMonomial(new Monom(monomial.getDegree(), monomial.getCoefficient()));
        }
        // se adauga si pol2
        for (Monom monomial : other.monomials.values()) {
            result.addMonomial(new Monom(monomial.getDegree(), monomial.getCoefficient()));
        }
        return result;
    }

    // Implement polynomial subtraction
    public Polynom subtract(Polynom other) {
        Polynom result = new Polynom();
        // se pun monomi din pol1 in result
        this.monomials.forEach((degree, monomial) -> result.addMonomial(new Monom(degree, monomial.getCoefficient())));

        // se scade pol2 din result
        other.monomials.forEach((degree, monomial) -> {
            // se scad coeficientii monomilor daca au aceeasi putere
            if (result.monomials.containsKey(degree)) {
                Monom existingMonomial = result.monomials.get(degree);
                double newCoefficient = existingMonomial.getCoefficient() - monomial.getCoefficient();
                result.monomials.put(degree, new Monom(degree, newCoefficient));
            } else {
                // daca este de putere diferita, se adauga in rezultat cu -
                result.addMonomial(new Monom(degree, -monomial.getCoefficient()));
            }
        });

        return result;
    }
    public Polynom multiply(Polynom other) {
        Polynom result = new Polynom();
        // se parcurg monoamele din ambele polinoame
        for (Monom thisMonom : this.monomials.values()) {
            for (Monom otherMonom : other.monomials.values()) {
                Monom productMonom = thisMonom.multiply(otherMonom);
                //verificam daca s-au efectuat toate operatiile de puterea n
                if (result.monomials.containsKey(productMonom.getDegree())) {
                    //daca da, se adauga in fata coeficientul
                    Monom existingMonom = result.monomials.get(productMonom.getDegree());
                    double newCoefficient = existingMonom.getCoefficient() + productMonom.getCoefficient();
                    result.monomials.put(productMonom.getDegree(), new Monom(productMonom.getDegree(), newCoefficient));
                } else {
                    //daca nu se adauga monomul
                    result.monomials.put(productMonom.getDegree(), productMonom);
                }
            }
        }

        return result;
    }

    public Polynom derivative() {
        Polynom result = new Polynom();
        for (Map.Entry<Integer, Monom> entry : this.monomials.entrySet()) {
            int degree = entry.getKey();
            double coefficient = entry.getValue().getCoefficient();
            if (degree > 0) { // daca nu are putere,este constanta, ignoram
                // Aplicam regula: d/dx(ax^n) = n*ax^(n-1)
                result.addMonomial(new Monom(degree - 1, coefficient * degree));
            }
        }
        return result;
    }

    public Polynom integrate() {
        Polynom result = new Polynom();
        for (Map.Entry<Integer, Monom> entry : this.monomials.entrySet()) {
            int degree = entry.getKey();
            double coefficient = entry.getValue().getCoefficient();
            // Aplicam  I ax^n dx = ax^(n+1)/(n+1)
            double newCoefficient = coefficient / (degree + 1);
            int newDegree = degree + 1;
            result.addMonomial(new Monom(newDegree, newCoefficient));
        }
        return result;
    }
    public Polynom[] divide(Polynom divisor) {
        if (divisor.monomials.isEmpty()) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }

        Polynom quotient = new Polynom();
        Polynom remainder = new Polynom(new TreeMap<>(this.monomials)); //copiem polinomul curent la remainder

        //continuam impartirea cat timp puterea de remainder e >= cu puterea divizorului
        while (!remainder.monomials.isEmpty() && remainder.monomials.lastKey() >= divisor.monomials.lastKey()) {
            int degreeDiff = remainder.monomials.lastKey() - divisor.monomials.lastKey();
            double coefDiff = remainder.monomials.lastEntry().getValue().getCoefficient() / divisor.monomials.lastEntry().getValue().getCoefficient();

            Polynom tempPoly = new Polynom();
            tempPoly.addMonomial(new Monom(degreeDiff, coefDiff));
            quotient.addMonomial(new Monom(degreeDiff, coefDiff));  //adaugam termeni la quotient

            Polynom tempProduct = tempPoly.multiply(divisor);
            remainder = remainder.subtract(tempProduct);  //scadem produsu din remeinder

            // Remove terms with coefficient 0 from remainder
            remainder.monomials.entrySet().removeIf(entry -> Math.abs(entry.getValue().getCoefficient()) < 1E-9);
        }

        return new Polynom[]{quotient, remainder};
    }

    public static Polynom parseFrom(String expression) {
        Polynom polynom = new Polynom();
        Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            String term = matcher.group(1);
            // tot separam in coeficient si putere
            double coefficient = 0;
            int degree = 0;
            Matcher termMatcher = Pattern.compile("([+-]?\\d*)x\\^?(\\d*)").matcher(term);
            if (termMatcher.find()) {
                String coefStr = termMatcher.group(1);
                String degStr = termMatcher.group(2);

                coefficient = coefStr.isEmpty() || coefStr.equals("+") || coefStr.equals("-") ?
                        coefStr.equals("-") ? -1 : 1 : Double.parseDouble(coefStr);
                degree = degStr.isEmpty() ? (term.contains("x") ? 1 : 0) : Integer.parseInt(degStr);
            } else if (!term.contains("x"))
            {
                coefficient = Double.parseDouble(term);
                degree = 0;
            }

            polynom.addMonomial(new Monom(degree, coefficient));
        }

        return polynom;
    }

    @Override
    public String toString() {
        if (monomials.isEmpty()) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (Monom monom : monomials.descendingMap().values()) {
            if (monom.getCoefficient() == 0) {  // daca au coeficient 0 trecem peste
                continue;
            }
            //afisam semnul in fata fiecarui termen
            if (sb.length() > 0) {
                sb.append(monom.getCoefficient() > 0 ? " + " : " - ");
            } else {
                if (monom.getCoefficient() < 0) {
                    sb.append("-");  //daca primul termen este negativ punem "-" in fata
                }
            }
            double absCoefficient = Math.abs(monom.getCoefficient());
            if (absCoefficient != 1 || monom.getDegree() == 0) {
                sb.append(absCoefficient); // daca este 1 sau -1 nu se afiseaza
            }
            // se pun x si puterea lui dupa fiecare termen
            if (monom.getDegree() == 1) {
                sb.append("x"); // x^1 scriem ca x
            } else if (monom.getDegree() > 1) {
                sb.append("x^").append(monom.getDegree()); //x^n
            }
        }
        return sb.toString();
    }

}
