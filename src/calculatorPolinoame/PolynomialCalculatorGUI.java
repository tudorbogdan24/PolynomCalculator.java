package calculatorPolinoame;

import javax.swing.*;
import java.awt.*;

public class PolynomialCalculatorGUI {

    private JFrame frame;
    private JTextField polynomial1Field;
    private JTextField polynomial2Field;
    private JTextField polynomialResultField;
    private JButton addButton;
    private JButton subtractButton;
    private JButton multiplyButton;
    private JButton divideButton;
    private JButton derivativeButton;
    private JButton integrateButton;

    public PolynomialCalculatorGUI() {
        //window
        frame = new JFrame("Polynomial Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 6, 10, 10)); // rows, cols, hgap, vgap

        // componente
        polynomial1Field = new JTextField();
        polynomial2Field = new JTextField();
        polynomialResultField = new JTextField();
        polynomialResultField.setEditable(false);  // sa nu poata fi modificat ca input
        addButton = new JButton("Aduna");
        subtractButton = new JButton("Scade");
        multiplyButton = new JButton("Inmulteste");
        divideButton = new JButton("Imparte");
        derivativeButton = new JButton("Deriveaza");
        integrateButton = new JButton("Integraza");

        // componente in window
        frame.add(new JLabel("Polinom 1:"));
        frame.add(polynomial1Field);
        frame.add(new JLabel("Polinom 2:"));
        frame.add(polynomial2Field);
        frame.add(new JLabel("Polinom result:"));
        frame.add(polynomialResultField);
        frame.add(addButton);
        frame.add(subtractButton);
        frame.add(multiplyButton);
        frame.add(divideButton);
        frame.add(derivativeButton);
        frame.add(integrateButton);

        // se atribuie op la butoane
        addButton.addActionListener(e -> performOperation(Operation.ADD));
        subtractButton.addActionListener(e -> performOperation(Operation.SUBTRACT));
        multiplyButton.addActionListener(e -> performOperation(Operation.MULTIPLY));
        divideButton.addActionListener(e -> performOperation(Operation.DIVIDE));
        derivativeButton.addActionListener(e -> performOperation(Operation.DERIVES));
        integrateButton.addActionListener(e -> performOperation(Operation.INTEGRATES));

        //dim window
        frame.pack();
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
    private void performOperation(Operation operation) {
        Polynom polynom1 = Polynom.parseFrom(polynomial1Field.getText());
        Polynom polynom2 = Polynom.parseFrom(polynomial2Field.getText());
        Polynom result = null;
        Polynom[] result1 = null;
        Polynom quotient = null;
        Polynom remainder = null;

        switch (operation) {
            case ADD:
                result = polynom1.add(polynom2);
                break;
            case SUBTRACT:
                result = polynom1.subtract(polynom2);
                break;
            case MULTIPLY:
                result = polynom1.multiply(polynom2);
                break;
            case DIVIDE:
                result1 = polynom1.divide(polynom2);
                quotient = result1[0];
                remainder = result1[1];
                //System.out.println("Quotient: " + quotient);
               // System.out.println("Remainder: " + remainder);
                break;
            case DERIVES:
                result = polynom1.derivative();
                break;
            case INTEGRATES:
                result = polynom1.integrate();
                break;
        }

        if (result != null) {
            polynomialResultField.setText(result.toString());
        }
        if (result1 != null) {
            polynomialResultField.setText("Quotient: " + quotient + "   Remainder: " + remainder);
        }
    }
    private enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE,DERIVES,INTEGRATES;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(PolynomialCalculatorGUI::new);
    }
}
