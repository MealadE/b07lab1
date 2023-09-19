public class Polynomial {
    double[] doubleCoef;

    public Polynomial() {
        this.doubleCoef = new double[0];
    }

    public Polynomial(double[] doubleCoef) {
        int size = doubleCoef.length;
        this.doubleCoef = new double[size];
        for (int i = 0; i < size; i++) {
            this.doubleCoef[i] = doubleCoef[i];
        }
    }

    public Polynomial add(Polynomial newPoly) {
        Polynomial poly1 = new Polynomial();
        int size = Math.max(newPoly.doubleCoef.length, this.doubleCoef.length);
        poly1.doubleCoef = new double[size];
        for (int i = 0; i < this.doubleCoef.length; i++) {
            poly1.doubleCoef[i] += this.doubleCoef[i];
        }
        for (int i = 0; i < newPoly.doubleCoef.length; i++) {
            poly1.doubleCoef[i] += newPoly.doubleCoef[i];
        }
        return poly1;
    }

    public double evaluate(double value) {
        double answer = 0;
        int size = this.doubleCoef.length;
        for (int i = 0; i < size; i++) {
            answer += doubleCoef[i] * Math.pow(value, i);
        }
        return answer;
    }

    public boolean hasRoot(double value) {
        return evaluate(value) == 0;
    }
}