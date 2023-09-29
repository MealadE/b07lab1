import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Driver {
    public static void main(String[] args) {
        double[] c1 = { 6, 5 };
        int[] e1 = { 0, 2 };
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { -2.7, 9.4 };
        int[] e2 = { 2, 1 };
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.multiply(p2);
        System.out.println(Arrays.toString(s.exponents));

        try {
            p2.saveToFile("output.txt");
            System.out.println("Polynomial saved to output.txt");
        } catch (IOException e) {
            System.err.println("Error saving the polynomial to a file: " + e.getMessage());
        }

        try

        {
            File testFile = new File("output.txt");
            Polynomial polynomial = new Polynomial(testFile);
            System.out.println("Polynomial from file: " + Arrays.toString(polynomial.coefficients));
            System.out.println("Polynomial from file: " + Arrays.toString(polynomial.exponents));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

}