package mycalculator;

/**My math lib for the calculator
 *
 * @author dlshle(Xuri Li)
 */
public class MyMath {
    
    public static double GCD(double a, double b){
        if(a==0)
            return b;
        if(b==0)
            return a;
        return GCD(b, a%b);
    }
    
    public static double LCD(double a, double b){
        return (a*b)/GCD(a,b);
    }
    
    public static double ABS(double num){
        if(num<0)
            return (-1)*num;
        return num;
    }
    
    public static double MAX(double a, double b){
        if(a<b)
            return b;
        return a;
    }
    
    public static double MIN(double a, double b){
        if(a>b)
            return b;
        return a;
    }
    
    

    public static double factorial(double n) {
        int remain = (int) n;

        if (remain == 0 || remain == 1) {
            return 1;
        }

        double result = 1;

        while (remain > 1) {
            result *= remain--;
        }

        return result;
    }   
    
    public static boolean isInteger(double n){
        return (int)n==n;
    }
    
    public static boolean isEven(double n){
        return n%2!=0;
    }
    
    public static boolean isOdd(double n){
        if(isEven(n))
            return false;
        //check if the number is integer
        return !isInteger(n);
    }
    
    public static int digitalRoot(long n){
        if(n/10==0)
            return (int)n;
        int sum = (int)(n%10);
        n/=10;
        while(n!=0){
            sum+=n%10;
            n/=10;
        }
        return digitalRoot(sum);
    }    
}
