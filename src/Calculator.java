import java.util.Scanner;
import java.util.Map; 
import java.util.HashMap;

public class Calculator{
    public static double[] memory_results = new double[10];
    final static double PI = 3.14159265358979323846;
    final static double E = 2.71828182845905;
    public static void main(String[] args){
        boolean dynamic;
        char manual_election, election= 'u';
        boolean want_to_read_manual;
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\n\n[================ Welcome to your scientific calculator! ================]\nDo you want to read the instructions manual? [y/n]: ");
        manual_election = sc.nextLine().charAt(0);
        want_to_read_manual = (manual_election=='y') ? true: false;
        
        if(want_to_read_manual){
          help(want_to_read_manual, "");
        }
        
        while(election !='e'){
            System.out.print("\nPress: \n u: For unique mode (accesing to one operation at a time). \n d: For dynamic mode (flow operation). \n e: for exit \n[u/d/e]: ");        
            election = sc.nextLine().charAt(0);
            switch(election){
              case 'u':
                  dynamic = false;
                  Operation(dynamic);
                  election='e';
                  break;
              case 'd': 
                  dynamic = true;
                  Operation(dynamic);
                  election='e';
                  break;
              case 'e':
                  System.out.println("Goodbye!");
                  sc.close();
                  break;
              default:
                  System.out.println("Select a valid answer."); 
                  break;
            }
        }
    }

/** 
 * Define the basic structure of the calculator, which look like this:  <br>
 *      sen(0)   // real number (e, pi, etc...) or mathematical function that return a value. 
 *      +        // arithmetic operation: sum, substraction, division, multiplication. 
 *      cos(-1)  // real number (e, pi, etc...) or mathematical function that return a value. 
 *      >> 1.0   // result, which in dynamic  
 * <b>pre: </b> election must have some value.
 * <b>post: </b> executes the operation-flow for the calculator.  
 * @param is_dynamic variable must have been declared, and its value must be passed in the arguments when this method is invoked.
*/
    public static void Operation(boolean is_dynamic){
        Scanner sc = new Scanner(System.in);
        double result = 0, num1=0, num2=0;
        char response = 'y', operation;
        String parser;

        System.out.println("\n################ Welcome to the dynamic mode! ################\nType a number (or function) and later the operand (+,-,*, or /):");
        parser = sc.nextLine();
        num1 = functionsCalculator(parser);
        System.out.println(">> " + num1);
        while(response != 'n'){

          operation = sc.nextLine().charAt(0);
          
          if(operation == '#'){
            is_dynamic = !is_dynamic;
            System.out.println( (is_dynamic) ? "Changing to the dynamic operation mode..." : "Changing to the unique-operation mode..." );
          } 
          else{
            parser = sc.nextLine(); 
            num2 = functionsCalculator(parser);
            switch(operation){
              case '+':                         
                  result = num1 + num2;
                  break;
              case '-':
                  result = num1 - num2;
                  break;
              case '*':
                  result = num1*num2;
                  break;
              case '/':
                  if(num2!=0)
                    result = num1/num2;
                  else 
                    System.out.println("Do you want the universe to explode? Division by zero isn't supported.");
                  break;
              case '%':
                  result = num1%num2;
                  break;
              default:
                  System.out.println("Type a valid operand (+, -, *, /, %, or #)");
                  break;
          }
          System.out.println(">> "+ result);
        }

            if(is_dynamic){
              num1 = result;
            } else {
              System.out.print("\nDo you want to input another mathematical operation? Type: \n y: Yes \n e: exit. \n #: Change to dynamic mode \n [y/e/#]: ");
              response = sc.nextLine().charAt(0);
              if (response == 'e'){
                System.out.println("Goodbye!");
                sc.close();
                break;
                } else if(response == '#'){
                  is_dynamic = true;
                  System.out.println("Changing to the dynamic operation mode...");
                  System.out.println("\nType a number (or function) and later the operand (+, -, *, /, %, or #):");
                }
              parser = sc.nextLine();
              num1 = functionsCalculator(parser);
            }
            memory_results = sliceArray(memory_results, result);
        }
    }

  /**
   * functionsCalculator is encharged of assigning its corrsponding value to the num1 and num2 variables above. <br>
   * Which is achieved by parsing the string input and deciding wheter is a plain number or a function.
   * <b>post: <b/> num1 and num2 will never have a invalid value since their value always is parsed using this method. 
   * @param parser must have a value assgined to it; it cannot be null or empty. 
   * @return a double number corresponding to the value expressed in parser; if this isn't valid, then returns 0 and notifies it to the user. 
   */
    public static double functionsCalculator(String parser){
      double num1=0;
      String[] params_array;
      double[] params;
      String params_string, func_indicator;
      boolean isOnlyNumber = true;

      try {
        num1 = (parser.contains("/")) ? parseFraction(parser) : Double.parseDouble(parser);
      }
      catch(Exception e){
        isOnlyNumber = false;
      }
      
      if(!isOnlyNumber) {
          func_indicator = parser.split("[\\(\\)]")[0];
          params_string = (parser.split("[\\(\\)]").length == 1) ? "" : parser.split("[\\(\\)]")[1];
          params_array = params_string.split(",");
          params = new double[params_array.length];
          
          if(params_array.length > 0){
            try{
              for(int i=0; i<params_array.length; i++){
                /* Fill the  params array with the casted values in params_array. 
                  func_indicator is, E.g. "cos" when "cos(0)".
                  params[0] is the first argument of function. E.g. 9 when "ln(9)"
                  params[1] is the second argument of function. E.g. 0 when "log(0,9)"
                  */
                  params[i] = (params_array[i].contains("/")) ? parseFraction(params_array[0]) : Double.parseDouble(params_array[i]);
                  }
            } catch(Exception e){
              // If exception occured, means that the input isn't a number, and be handled below.
            }
          }
            
          switch(func_indicator){
            case "cos":
              num1 = cosApprox(params[0]);
              break;
            case "sin":
              num1 = sinApprox(params[0]);
              break;
            case "tan":
              num1 = sinApprox(params[0])/cosApprox(params[0]);
              break;
            case "log10":
              num1 = log(10, params[0]);
             break;
            case "log":
              //      log(base, x), e.g. log(5, 25) = 2
              try {
                num1 =  log(params[0], params[1]); 
              } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("You forgot to add the other argument! Remember that log(base, N)");
              }
              break;
            case "ln":
              num1 = ln(params[0]);
              break;
            case "exp":
              num1 = e(params[0]);
              break;
            case "fact":
              num1 = factorial( (int) params[0]);
              break;
            case "sqrt":
              num1 = sqrt(params[0]);
              break;
            case "rt":
               // rz(3,8) = 2 (third root of 8)
              num1 = nthRoot((int) params[0], params[1]);
              break;
            case "pow":
              num1 = power(params[0], params[1]);
              break;
            case "deg->rad":
              num1 = degrees2Rads(params[0]);
              break;
            case "rad->deg":
              num1 = rad2Degrees(params[0]);
              break;
            case "pi":
              num1 = Calculator.PI;
              break;
            case "e":
              num1 = Calculator.E;
              break;
            case "help":
              help(false, params_array[0]);
              break;
            case "mem":
              num1 = memory_results[ (int) params[0]-1];
              break;
            case "convert":
                 System.out.println("Decimal to hexadecimal: " + decimalToHex((int) params[0]));
                 System.out.println("Hexadecimal to decimal: " + hexToDecimal(params_array[0]));
                 System.out.println("Decimal to binary: " + Integer.toString((int) params[0], 2));
              break;
            case "memory":
                System.out.println("\nThe last ten results in memory are the following:");
                for(int i=0; i<memory_results.length; i++){
                    System.out.println("#" + (i+1) + "   " + memory_results[i]);
                }
              break;
            default:
              System.out.println("You typed a unvalid function or number, this will be taken as 0.");
              break;
          }
      }
      return num1;
    }

/**
 * Slices an double array to the left, i. e. the first element is dropped and another is added at last position <br>
 * <b>post: <b/> every result in calculator will be added at last position of the memory_results array.
 * @param array must be initialized and must have a length assigned.
 * @param element must be double. 
 * @return the same array with the same length, but its last element being the passed in parameter and its oldest one being dropped. 
 */
    public static double[] sliceArray(double[] array, double element){
      for(int i=0; i<(array.length-1);i++){
        array[i] = array[i+1];
      }
      array[array.length-1] = element;
      return array;
    } 

  /**
   * Converts a string that contains a fraction into its double value. E.g. converts "1/2" into 0.5
   * @param expression must contain a fraction in it; otherwise it would invoke an error.
   * @return fraction, the numerical equivalent of the fraction string passed.
   */
    public static double parseFraction(String expression){
        String[] rat = expression.split("/");
        double fraction = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
      return fraction;
    }

    /**
     * Calculates the nth-root of a double number. <br>
     * @param n must be positive integer number; otherwise it would ran an error.
     * @param A must be positive real number whenever the index n be even number; otherwise it would invoke an imaginary response.
     * @return the nth-root of Power; if and only if none of these are undefined or unreal numbers.
     */
    public static double nthRoot(int n, double A) {
      double x0 = 1;
      boolean accurate = false;
      while (!accurate) {
          double x1 = (1 / (double)n) * ((n - 1) * x0 + A / pow(x0, n - 1));
          accurate = accurate(x0, x1);
          x0 = x1;
      }
      return x0;
  }

  public static boolean accurate(double x0, double x1) {
      return abs(x1-x0) < 0.000001;
  }

  /**
   * Calculates the logarithm of base B for a number N, taking advantage of the logarithm's properties.<br>
   * @param B must be real positive number; otherwise would not be defined.
   * @param N must be real positive number; otherwise would not be defined.
   * @return the logarithm of base B for a number N.
   */
    public static double log(double B, double N){
      if (B==2) return (ln(N) / 0.69314718055995);
      else if (B==10) return (ln(N) / 2.30258509299405);
      else return (ln(N) / ln(B)); 
    }

    /**
     * Calculates the natural logarithm for power x by its integral.<br>
     * @param Power cannot be negative, otherwise the result would be be undefined.
     * @return Natural logarithm of x.
     */
    public static double ln(double Power){
      if(Power < 0){
        System.out.println("Do you want the universe to explode? Undefined result.");
        return 0;
      }
      else {
          double N, P, L, R, A, E;
          E = 2.71828182845905;
          P = Power;
          N = 0.0;
                  // This speeds up the convergence by calculating the integral
          while(P >= E)
          {
              P /= E;
              N++;
          }
          N += (P / E);
          P = Power;
          do
          {
              A = N;
              L = (P / (e(N - 1.0)));
              R = ((N - 1.0) * E);
              N = ((L + R) / E);
          }while(N != A);

          return N;
        }
      }
    
      /**
       * Calculates the absolute value for number num.<br>
       * @param num must be real number.
       * @return absolute value of num.
       */
    public static double abs(double num){
      if (num<0) return num*-1;
      else return num;
    }

    /**
     * Caculates the decimal power of decimal base and decimal exponent using the properties of natural logarithms and exponential function.<br>
     * @param base must be declared and initialized.
     * @param exponent must be declared and initialized.
     * @return the n-th power of base n number.
     */
     public static double power(double base, double exponent){
      if(exponent == 0){
        return 1;
      } else if(exponent==1){
        return base;
      } else if(exponent < 0){
        return 1/power(base, abs(exponent));
      }
      else if(base < 0 && exponent<1){
        System.out.println("Not in real numbers");
        return 0;
      } else {
        return e(exponent * ln(abs(base)));
      }
    }

    /**
     * Calculates an aproximation for the exponential function.<br>
     * @param Exponent must be decimal real number. 
     * @return euler's number to the exponent-th power.
     */
    public static double e(double Exponent){
      double X, P, Frac, I, L;
      X = Exponent;
      Frac = X;
      P = (1.0 + X);
      I = 1.0;

      do
      {
          I++;
          Frac *= (X / I);
          L = P;
          P += Frac;
      }while(L != P);

      return P;
    }

    /**
     * Calculates the factorial of number num using recursion. <br>
     * @param num must be positive integer number.
     * @return the factorial for num number.
     */
    public static double factorial(int num) {
      double fact_result=1;
      if (num==0) {
        return fact_result;
      } else {
        for (int i=1;i<=num;i++) {
          fact_result = fact_result*i;
        }
      }
      return fact_result;
    }

/**
 * Calculates the n-th power for base number a. <br>
 * @param x must be declared and initalized number.
 * @param n must be declared and initalized number.
 * @return the n-th power for base number a.
 */
  public static double pow(double x, int n) {
    if(n == 0) {
        return 1;
    }
    return x * pow(x, n-1);
}

  /**
   * Calculates the approximate value of sinusodial function for number a using Taylor series with periodic accuracy improvement. <br>
   * @param x cannot be infinity, must be real number.
   * @return aproximation value of sinusodial function at point a.
   */
  public static double sinApprox(double x) {
    if (x == Double.NEGATIVE_INFINITY || !(x < Double.POSITIVE_INFINITY)) {
        return Double.NaN;
    }
    // If you can't use Math.PI neither,
    // you'll have to create your own PI

    // Fix the domain for a...

    // Sine is a periodic function with period = 2*PI
    x %= 2 * PI;
    // Any negative angle can be brought back
    // to it's equivalent positive angle
    if (x < 0) {
        x = 2 * PI - x;
    }
    // Also sine is an odd function...
    // let's take advantage of it.
    int sign = 1;
    if (x > PI) {
        x -= PI;
        sign = -1;
    }
    // Now a is in range [0, pi].
    // Calculate sin(a)

    // Set precision to fit your needs.
    // Note that 171! > Double.MAX_VALUE, so
    // don't set PRECISION to anything greater
    // than 84 unless you are sure your
    // Factorial.factorial() can handle it
    final int PRECISION = 84;
    double temp = 0;
    for (int i = 0; i <= PRECISION; i++) {
        temp += pow(-1, i) * (pow(x, 2 * i + 1) / factorial(2 * i + 1));
    }
    return sign * temp;
}

/**
 * Calculates approximation value for cosine function through Taylor Series expansion
 * @param x must be real number value.
 * @return approximation (in radians) to the cosine function at point x. 
 */
  public static double cosApprox(double x){   
      double solution = 1;
      double eachTerm = 1;
      int termsSoFar = 1;
      for(int i = 1; i < (2 * 100); ++i){   
        eachTerm *= x / i;
          if(i % 2 == 0){   
            ++termsSoFar;
              if(termsSoFar % 2 == 0) {   
                solution -= eachTerm;
              }
              else{   
                solution += eachTerm;
              }
          }
      }
      return solution;
  }

  public static double rad2Degrees(double radians){
    double degrees = radians * 180.0 / PI;
    return degrees;
  }

  public static double degrees2Rads(double degrees){
    double radians = degrees * PI / 180.0;
    return radians;
  }

    /**
     * Calculates the square root of number.<br>
     * @param number must be a real, positive, and defined number; 
     * @return square root for number.
     */
    public static double sqrt(double number) {
      double t;
    
      double squareRoot = number / 2;
    
      do {
        t = squareRoot;
        squareRoot = (t + (number / t)) / 2;
      } while ((t - squareRoot) != 0);
    
      return squareRoot;
  }

/**
 * Calculates the hexadecimal value of integer d.<br>
 * @param d is a nonnegative integer
 * @return the hexadecimal value of integer d
 */
    public static String decimalToHex(int d) {
        String digits = "0123456789ABCDEF";
        if (d == 0) return "0";
        String hex = "";
        while (d > 0) {
            int digit = d % 16;                // rightmost digit
            hex = digits.charAt(digit) + hex;  // string concatenation
            d = d / 16;
        }
        return hex;
    }

    /**
     * Calculates the integer value for String input s.<br>
     * @param s must be compatible with hexadecimal system.
     * @return integer value for String input s.
     */
    public static int hexToDecimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

    public static void help(boolean intitial_manual, String functionMissunderstood){
      Map<String, String> manual = new HashMap<String, String>();
      manual.put("cos",         "FUNCTION cos(real number) Example: \ncos(-1) = 0.5403023059 //in radians");
      manual.put("sin",         "FUNCTION sin(real number) Example: \nsin(-1) = -0.8414709848 //in radians");
      manual.put("tan",         "FUNCTION tan(real number) Example: \ntan(-1) = -1.557 //in radians");
      manual.put("log10",       "FUNCTION log10(POSITIVE real number) Example: \nlog10(2) = 0.30102999257");
      manual.put("log",         "FUNCTION log(base: real number, n: real number) Example: \nlog(5,25) = 2.0");
      manual.put("ln",          "FUNCTION ln(POSITIVE real number) Example: \nln(2) = 0.69");
      manual.put("exp",         "FUNCTION exp(real number) Example: \nexp(-2)\n>>0.13");
      manual.put("fact",        "FUNCTION fact(integer number) Example: \nfact(4)=24");
      manual.put("sqrt",        "FUNCTION sqrt(POSITIVE real number) Example: \nsqrt(2) = 1.4");
      manual.put("rt",          "FUNCTION rt(index : POSITIVE real number, radicand: POSITIVE real number if index is even, NEGATIVE real number if index is odd) Example: \nrt(3,-8) = 2");
      manual.put("pow",         "FUNCTION pow(base: real number, exponent: real number) Example: \npow(2, -1) = 0.5");
      manual.put("deg->rad",    "FUNCTION deg->rad(degrees) Example: \ndeg->rad(90) = -0.44");
      manual.put("rad->deg",    "FUNCTION rad->deg(radians) Example: \ndeg->rad(-0.44) = 90");
      manual.put("pi",          "CONSTANT pi  Example: \npi = 3.14159265358979323846");
      manual.put("e",           "CONSTANT e Example: \ne = 2.71828182845905");
      manual.put("mem",         "FUNCTION mem(1:first result or 10:last result) This function returns the result at position passed. Example: \nmem(10) = 3");
      manual.put("convert",     "FUNCTION convert(decimal or binary or hexadecimal string) Example \nconvert(247242)");
      manual.put("memory",      "COMMAND  memory Example:");

      if(intitial_manual && functionMissunderstood==""){
        System.out.println("\n\n\n\n############################################# INITIAL MANUAL ##############################################");
        System.out.println("So far, this command-based scientific calculator supports 15 functions.\nLet's take an overview of the overall strcutre and then how each one works:\n");
        System.out.println("The basic structure of this calculator is the following: \n\nlog(5,25) // you input a number or function \n+ // then a operand (+,-,*,/, or %) \n2 // and then another number or function \n>> 4.0");
        System.out.println("You can choose between unique-mode operation or flow-operation mode; and can change through them typing # instead of an operand.");
        System.out.println("\n##########################################  Example instuctions ##########################################");
        for (Map.Entry<String,String> entry : manual.entrySet())  
            System.out.println("## " + entry.getValue() + "\n");
        System.out.println("if you want to remember how x function works, type help(x) and get and example."); 
      } else {
        System.out.print(manual.get(functionMissunderstood) + "\n");
      }
    }

}