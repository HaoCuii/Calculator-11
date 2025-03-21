import java.util.Scanner;

//helper class
class Pair {
    double first;
    double second;

    Pair(double first, double second) {
        this.first = first;
        this.second = second;
    }
}

public class Calculator {

    public static double power(double x, int exp){
        double total = 1;
        for (int i = 1; i <= exp; i++){
            total *= x;
        }
        return total;
    }

    public static double absoluteValue(double x){
        if (x < 0){
            x *= -1;
        }
        return x;
    }

    //modulo with floor divison rounding towards negative infinity
    public static double mod(double a, double b) {
        int add_negative = 0;
        if (a < 0) {
            add_negative = -1;
        }
        int floorDivision = (int) (a / b) + add_negative;
        return a - b * floorDivision;

    }

    public static double factorial(int n){
        //factorial function, only works for whole numbers
        if (n < 0){
            throw new IllegalArgumentException("Factorial of a number must be positive (for this calculator");
        }

        if (n == 0 || n == 1) {
            return 1;
        }
        return n * (factorial(n - 1));
    }

    public static double e(){
        //calculates e to 15 decimal points
        int iterations = 1000;
        double total = 0;

        for (int i = 0; i <= iterations; i++){
            total += 1 / factorial(i);
        }

        return total;
    }

    public static double ln(double x){
        if (x < 0){
            throw new IllegalArgumentException("Argument must be a positive number for ln(x)");
        }
        // taylor series expansion of ln(x)
        double total = 0;
        for (int n = 0; n <= 1000; n++){
            total += Powers.power(((x - 1) / (x + 1)), 2 * n + 1) / (2 * n + 1);
        }

        return 2 * total;
    }

    public static double log(double base, double argument){
        if (base < 0 || argument < 0){
            throw new IllegalArgumentException("All parts of a logarithm must be positive");
        }
        //log base a of b = ln(a)/ln(b)
        return ln(base) / ln(argument);
    }


    public static double arcsin(double x) {
        double total = x;
        for (int i = 3; i <= 31; i += 2) {
            total += power(x, i) *  factorial(i-1) / (i * power(4,(i-1)/2) * power(factorial((i-1)/2),2));
        }
        return total;
    }

    public static double arctan(double x) {
        double total = 0.0;
        int neg = 1;
        for (int i = 1; i <= 31; i += 2) {
            total += (power(x, i) * neg) / i;
            neg *= -1;  // Alternate signs
        }

        return total;
    }

    public static double pi() {
        return 16*arctan((double) 1/5) - 4 * arctan((double) 1/239);

    }

    public static double arccos(double x){
        return  pi()/2 - arcsin(x);
    }

    public static double cos(double x) {
        return taylorExpansion(x, 100, false);
    }

    public static double sin(double x) {
        return taylorExpansion(x, 100, true);
    }

    public static double tan(double x){
        // tan = sin/cos
        return sin(x) / cos(x);
    }

    private static double taylorExpansion(double x, int iterations, boolean sin) {
        // uses taylor expansion to calculate sin and cos
        x = mod(x, (2 * pi()));
        double total = 0;
        int add_sin = sin ? 1 : 0;
        double denominator;

        for (int n = 0; n < iterations; n++) {
            denominator = factorial(2 * n + add_sin);
            double sum = power(x, (2 * n + add_sin)) / denominator;

            if (n % 2 == 0) {
                total += sum;
            } else {
                total -= sum;
            }
        }

        return total;
    }
    public static double sqrtNewton(double x) {
        if (x < 0){
            throw new IllegalArgumentException("Cannot do squareroot of a negative number");
        }
        // newton's square root finding method
        double guess = x / 2;
        double tolerance = 1e-15;

        while (absoluteValue(guess * guess - x) > tolerance) {
            guess = (guess + x / guess) / 2.0;
        }

        return guess;
    }

    public static double add(double a, double b){
        return a + b;
    }

    public static double subtract(double a, double b){
        return a - b;
    }

    public static double multiply(double a, double b){
        return a * b;
    }

    public static double divide(double a, double b){
        return a / b;
    }

    public static void options(){
        //prints out user's options
        System.out.println("Here are the functions we offer:\n" +
                "1. Addition\n" +
                "2. Subtraction\n" +
                "3. Multiplication\n" +
                "4. Division\n" +
                "5. Power\n" +
                "6. Squareroots\n" +
                "7. Factorials (only whole numbers)\n" +
                "8. Modulo\n" +
                "9. Log\n" +
                "10. Natural Log (ln)\n" +
                "11. Sine\n" +
                "12. Cosine\n" +
                "13. Tangent\n" +
                "14. Arcsine\n" +
                "15. Arccosine\n" +
                "16. Arctangent");
    }
    public static double getValue(String function, Scanner UI){
        //returns a double with option of e or pi
        double num;
        System.out.println("Please enter a value for your " + function + " function");
        System.out.println("If you would like to use e or pi, please type 1 right now");
        String piOrE = UI.nextLine();
        if (piOrE.equals("1")){
            System.out.println("PI for pi and E for e");
            while (true) {
                String res = UI.nextLine();
                if (res.equals("PI")) {
                    return pi();
                } else if (res.equals("E")) {
                    return e();
                }
                System.out.println("Please enter PI or E");
            }
        }

        System.out.println("Please enter your number");
        double responseDouble = UI.nextDouble();
        UI.nextLine();
        return responseDouble;
    }

    //helper function for returning two values at a time
    public static Pair twoValues(String function, Scanner UI){
        System.out.println("First value of " + function + " function");
        double num1 = getValue(function, UI);
        System.out.println("Second value of " + function + " function");
        double num2 = getValue(function, UI);

        return new Pair(num1, num2);
    }

    public static void main(String[] args){
        System.out.println("Welcome to Sean and Hao's calculator!");
        Scanner UI = new Scanner(System.in);

        while (true) {
            options();
            System.out.println("What would you like to choose?");
            int response = UI.nextInt();
            UI.nextLine();
            Pair values;
            double value;

            switch (response) {
                case 1:
                    values = twoValues("Addition", UI);
                    System.out.println(add(values.first, values.second));
                    break;
                case 2:
                    values = twoValues("Subtraction", UI);
                    System.out.println(subtract(values.first, values.second));
                    break;
                case 3:
                    values = twoValues("Multiplication", UI);
                    System.out.println(multiply(values.first, values.second));
                    break;
                case 4:
                    values = twoValues("Division", UI);
                    System.out.println(divide(values.first, values.second));
                    break;
                case 5:
                    values = twoValues("Power", UI);
                    System.out.println(power(values.first, (int) values.second));
                    break;
                case 6:
                    value = getValue("Squareroot", UI);
                    System.out.println(sqrtNewton(value));
                    break;
                case 7:
                    int factorialValue = (int) getValue("Factorial", UI);
                    System.out.println(factorial(factorialValue));
                    break;
                case 8:
                    values = twoValues("Modulo", UI);
                    System.out.println(mod(values.first, values.second));
                    break;
                case 9:
                    values = twoValues("Log", UI);
                    System.out.println(log(values.first, values.second));
                    break;
                case 10:
                    value = getValue("Natural Log (ln)", UI);
                    System.out.println(ln(value));
                    break;
                case 11:
                    value = getValue("Sine", UI);
                    System.out.println(sin(value));
                    break;
                case 12:
                    value = getValue("Cosine", UI);
                    System.out.println(cos(value));
                    break;
                case 13:
                    value = getValue("Tangent", UI);
                    System.out.println(tan(value));
                    break;
                case 14:
                    value = getValue("Arcsine", UI);
                    System.out.println(arcsin(value));
                    break;
                case 15:
                    value = getValue("Arccosine", UI);
                    System.out.println(arccos(value));
                    break;
                case 16:
                    value = getValue("Arctangent", UI);
                    System.out.println(arctan(value));
                    break;
            }

            //looping mechanism
            System.out.println("Would you like to continue? (yes/no)");
            while (true) {
                String res = UI.nextLine();
                if (res.equals("no")) {
                    System.out.println("bye");
                    return;
                } else if (res.equals("yes")){
                    break;
                }
                System.out.println("Please enter just (yes/no)");
            }
        }
    }
}
