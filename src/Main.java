import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    static int pass = 0;
    static int fail = 0;
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Complex[] func1 = {new Complex(1, 2), new Complex(3, 4), new Complex(5, 6), new Complex(7, 8)};
        Complex[] expected1 = {new Complex(16, 20), new Complex(-8, 0), new Complex(-4, -4), new Complex(0, -8)};

        Complex[] func1fft = fft(func1);
        for(int i = 0; i < func1.length; i++) {
            if(!func1fft[i].equals(expected1[i])) {
                fail++;
                break;
            }
            if(i == func1.length - 1) pass++;
        }
        
        Complex[] func2 = {new Complex(2,0), new Complex(7,0), new Complex(4,0), new Complex(3,0)};
        Complex[] func2fft = fft(func2);

        Complex[] expected2 = {new Complex(16, 0), new Complex(-2, -4), new Complex(-4, 0), new Complex(-2, 4)};
        for(int i = 0; i < func2.length; i++) {
            if(!func2fft[i].equals(expected2[i])) {
                fail++;
                break;
            }
            if(i == func2.length - 1) pass++;
        }
        
        p("Passes: " + pass);
        p("Fails: " + fail);
        
    }

    public static Complex[] fft(Complex[] x) {
        int n = x.length;

        // base case
        if (n == 1) return new Complex[] {x[0]};

        // even fft
        Complex[] even = new Complex[n/2];
        for (int i = 0; i < n/2; i++) {
            even[i] = x[2*i];
        }
        Complex[] evenFFT = fft(even);

        // odd fft
        Complex[] odd  = even;
        for (int i = 0; i < n/2; i++) {
            odd[i] = x[2*i + 1];
        }
        Complex[] oddFFT = fft(odd);

        // combine
        Complex[] out = new Complex[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * round(Math.PI / n, 12);
            Complex rootOfUnity = new Complex(round(Math.cos(kth), 7), round(Math.sin(kth), 7));
            out[k] = evenFFT[k].add(rootOfUnity.mult(oddFFT[k]));
            out[k + n/2] = evenFFT[k].sub(rootOfUnity.mult(oddFFT[k]));
        }
        return out;
    }

    // rounding helper method
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void p(Object o) {
        System.out.println(o);
    }

}
