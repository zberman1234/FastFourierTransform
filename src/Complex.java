import java.math.BigDecimal;
import java.math.RoundingMode;

public class Complex {
    double real;
    double imag;
    
    public Complex(double rl, double im) {
        real = rl;
        imag = im;
    }

    /**
     * Add a complex to this
     * @param o the other complex number
     * @return the sum of the two complex numbers
     */
    public Complex add(Complex o) {
        return new Complex(this.real + o.real, this.imag + o.imag);
    }

    
    public boolean equals(Complex o) {
        return this.real == o.real && this.imag == o.imag;
    }

    /**
     * Add a complex to this
     * @param o the other complex number
     * @return the sum of the two complex numbers
     */
    public Complex sub(Complex o) {
        return new Complex(this.real - o.real, this.imag - o.imag);
    }


    /**
     * Multiply this by another complex number
     * @param o the other complex number
     * @return the product of the two complex numbers
     */
    public Complex mult(Complex o) {
        double _real = o.real * this.real - o.imag * this.imag;
        double _imag = o.imag * this.real + this.imag * o.real;

        return new Complex(_real, _imag);
    }

    /**
     * Get the kth out of n root of unity
     * @param k the kth root
     * @param n the number of roots
     * @return the kth root of unity
     */
    public static Complex rootOfUnity(double k, double n) {
        double real = Math.cos(2*Math.PI*(k/n));
        double imag = Math.sin(2*Math.PI*(k/n));

        return new Complex(round(real, 11), round(imag, 11));
    }

    /**
     * Get all k roots of unity for n roots
     * @param n the number of roots
     * @return an array of all k roots of unity
     */
    public static Complex[] rootsOfUnity(int n) {
        Complex[] out = new Complex[n];
        for(int k = 0; k < n; k++) {
            out[k] = rootOfUnity(n, k);
        }
        return out;
    }

    /**
     * Complex number (this) to the power of an integer (complex not needed for fourier transform) 
     * @param o the exponent
     * @return (this) to the o
     */
    public Complex exp(int exp) {
        double r = Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2));
        double theta = Math.atan(imag/real);
        double coeff = Math.pow(r, exp);
        double rl = coeff * Math.cos(exp*theta);
        double im = coeff * Math.sin(exp*theta);

        return new Complex(round(rl, 11), round(im, 11));
    }

    public String toString() {
        return real + " + " + imag + "i";
    }
    
    // rounding helper method
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}