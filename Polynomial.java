import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        this.coefficients = new double[0];
        this.exponents = new int[0];
    }

    public void saveToFile(String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write each term of the polynomial to the file
            for (int i = 0; i < coefficients.length; i++) {
                double coefficient = coefficients[i];
                int exponent = exponents[i];

                if (i > 0 && coefficient >= 0) {
                    // Add a plus sign for positive coefficients except for the first term
                    writer.print("+");
                }

                if (coefficient != 0) {
                    // Write the coefficient
                    writer.print(coefficient);

                    if (exponent > 0) {
                        // Write the 'x' term if the exponent is greater than 0
                        writer.print("x");

                        if (exponent > 1) {
                            // Write the exponent if it's greater than 1
                            writer.print(exponent);
                        }
                    }
                }
            }
        }
    }

    public Polynomial(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String line = scanner.nextLine();
        scanner.close();

        String[] terms = line.split("(?=[+-])");
        coefficients = new double[terms.length];
        exponents = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            double coefficient;
            int exponent;

            if (term.contains("x")) {
                String[] parts = term.split("x");

                if (parts[0].isEmpty()) {
                    coefficient = 1.0;
                } else if (parts[0].equals("-")) {
                    coefficient = -1.0;
                } else {
                    coefficient = Double.parseDouble(parts[0]);
                }

                if (parts.length > 1) {
                    if (parts[1].isEmpty()) {
                        exponent = 1;
                    } else {
                        exponent = Integer.parseInt(parts[1]);
                    }
                } else {
                    exponent = 1;
                }
            } else {
                coefficient = Double.parseDouble(term);
                exponent = 0;
            }

            coefficients[i] = coefficient;
            exponents[i] = exponent;
        }
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        int size = coefficients.length;
        this.coefficients = new double[size];
        this.exponents = new int[size];
        for (int i = 0; i < size; i++) {
            if (coefficients[i] != 0) {
                this.coefficients[i] = coefficients[i];
                this.exponents[i] = exponents[i];
            }
        }
    }

    public Polynomial add(Polynomial newPoly) {
        Polynomial poly1 = new Polynomial();
        int size = Math.max(newPoly.coefficients.length, this.coefficients.length);
        poly1.coefficients = new double[size];
        for (int i = 0; i < this.coefficients.length; i++) {
            poly1.coefficients[i] += this.coefficients[i];
            poly1.exponents[i] += this.exponents[i];
        }

        for (int i = 0; i < newPoly.coefficients.length; i++) {
            poly1.coefficients[i] += newPoly.coefficients[i];
            poly1.exponents[i] = newPoly.exponents[i];
        }
        return poly1;
    }

    public double evaluate(double value) {
        double answer = 0;
        int size = this.coefficients.length;
        for (int i = 0; i < size; i++) {
            answer += coefficients[i] * Math.pow(value, this.exponents[i]);
        }
        return answer;
    }

    public boolean hasRoot(double value) {
        return evaluate(value) == 0;
    }

    public Polynomial multiply(Polynomial other) {
        int thisDegree = this.coefficients.length;
        int otherDegree = other.coefficients.length;
        int resultDegree = thisDegree + otherDegree - 1;

        double[] resultCoefficients = new double[resultDegree];
        int[] resultExponents = new int[resultDegree];

        for (int i = 0; i < thisDegree; i++) {
            for (int j = 0; j < otherDegree; j++) {
                int newDegree = this.exponents[i] + other.exponents[j];
                double newCoefficient = this.coefficients[i] * other.coefficients[j];
                addTerm(resultCoefficients, resultExponents, newCoefficient, newDegree);
            }
        }

        Polynomial result = new Polynomial(resultCoefficients, resultExponents);
        return result;
    }

    public void addTerm(double[] coefficients, int[] exponents, double newCoefficient, int newDegree) {
        for (int i = 0; i < coefficients.length; i++) {
            if (exponents[i] == newDegree) {
                coefficients[i] += newCoefficient;
                return;
            }
        }

        // If the term doesn't exist, add it as a new term
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] == 0.0) {
                coefficients[i] = newCoefficient;
                exponents[i] = newDegree;
                return;
            }
        }
    }

}