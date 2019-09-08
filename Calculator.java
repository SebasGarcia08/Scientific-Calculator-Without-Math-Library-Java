import java.util.Scanner;

public class Calculator{
    public static double[] memory_results = new double[10];
    final static double PI = 3.14159265358979323846;
    public static void main(String[] args){
        int election;
        boolean dynamic;
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Welcome to your calculator! Press: \n1). For accesing to one operation at a time (unique mode). \n2). For flow operation mode (dynamic mode). \n[1/2]: ");        
        election = sc.nextInt();
        switch(election){
            case 1:
                dynamic = false;
                Operation(dynamic);
                break;

            case 2: 
                dynamic = true;
                Operation(dynamic);
                break;
            default:
                System.out.println("Goodbay!"); 
                break;
        }
        sc.close();
    }

/** 
 * Define the basic structure of the calculator, which look like this:  <br />
 *      sen(0)   // real number (e, pi, etc...) or mathematical function that return a value. <br /> 
 *      +        // arithmetic operation: sum, substraction, division, multiplication. <br />
 *      cos(-1)  // real number (e, pi, etc...) or mathematical function that return a value. <br />
 *      >> 1.0   // result, which in dynamic  <br />
 * <b>pre: </b> election must have some value.
 * <b>post: </b> executes the flow for the calculator. 
 * @param  scan_num (numerical scanner) must have been instancied and assgined to a variable.
 * @param  scan_opr(character scanner) must have been instancied and assgined to a variable.
 * @param is_dynamic variable must have been declared, and its value must be passed in the arguments when this * method is invoked.
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
                  result = num1/num2;
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
              System.out.print("\nDo you want to input another mathematical operation? Type: \n y: Yes \n n: No, exit. \n #: Change to dynamic mode \n [y/n/#]: ");
              response = sc.nextLine().charAt(0);
              if (response == 'n'){
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

    public static double functionsCalculator(String parser){
      double num1=0;
      String[] params_array;
      double[] params;
      String params_string, func_indicator;
      boolean isOnlyNumber = true;

      try {
        num1 = Double.parseDouble(parser);
      }
      catch(Exception e){
        isOnlyNumber = false;
      }
      
      if(!isOnlyNumber) {
          // System.out.println("NO Contiene número.");
          func_indicator = parser.split("[\\(\\)]")[0];
          params_string = (parser.split("[\\(\\)]").length == 1) ? "" : parser.split("[\\(\\)]")[1];
          params_array = params_string.split(",");
          params = new double[params_array.length];
          
          if(params_array.length > 0 && params_array[0].matches("[0-9]+")){
            for(int i=0; i<params_array.length; i++){
          /* Fill the  params array with the casted values in params_array. 
            func_indicator is, E.g. "cos" when "cos(0)".
            params[0] is the first argument of function. E.g. 9 when "ln(9)"
            params[1] is the second argument of function. E.g. 0 when "log(0,9)"
            */
            params[i] = (double) Double.parseDouble(params_array[i]);
            } 
          }
                    
          //System.out.println("Función es: " + func_indicator);
          //System.out.println("Valor: " + params[0]);
          switch(func_indicator){
            case "cos":
              num1 = Math.cos(params[0]);
              break;
            case "sin":
              num1 = sin(params[0]);
              break;
            case "tan":
              num1 = sin(params[0])/Math.cos(params[0]);
              break;
            case "log10":
              num1 = Math.log10(params[0]);
             break;
            case "log":
              num1 = Math.log(params[1]) / Math.log(params[0]); 
              break;
            case "ln":
              num1 = Math.log(params[0]);
              break;
            case "exp":
              num1 = e(params[0]);
              break;
            case "fact":
              num1 = factorial(params[0]);
              break;
            case "rzc":
              num1 = sqrt(params[0]);
              break;
            case "rz":
               // rz(3,8) = 2 (third root of 8)
              num1 = Math.pow(params[1], 1/params[0]);
              break;
            case "pot":
              num1 = power((int) params[0], (int) params[1]);
              break;
            case "grad->rad":
              num1 = degrees2Rads(params[0]);
              break;
            case "rad->grad":
              num1 = rad2Degrees(params[0]);
              break;
            case "pi":
              num1 = PI;
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

    public static double[] sliceArray(double[] array, double element){
      for(int i=0; i<(array.length-1);i++){
        array[i] = array[i+1];
      }
      array[array.length-1] = element;
      return array;
    } 

    public static double power(double base, int exponent) 
    { 
        if (exponent == 0) 
            return 1; 
        else if (exponent % 2 == 0) 
            return power(base, exponent / 2) * power(base, exponent / 2); 
        else
            return base * power(base, exponent / 2) * power(base, exponent / 2); 
    } 

    public static double factorial(double num) {
    if(num==0) return 1;
    else return num * factorial(num -1);
	}


	public static double sin(double a) {

    if (a == Double.NEGATIVE_INFINITY || !(a < Double.POSITIVE_INFINITY)) {
        return Double.NaN;
    }
    // If you can't use Math.PI neither,
    // you'll have to create your own PI

    // Fix the domain for a...

    // Sine is a periodic function with period = 2*PI
    a %= 2 * PI;
    // Any negative angle can be brought back
    // to it's equivalent positive angle
    if (a < 0) {
        a = 2 * PI - a;
    }
    // Also sine is an odd function...
    // let's take advantage of it.
    int sign = 1;
    if (a > PI) {
        a -= PI;
        sign = -1;
    }
    // Now a is in range [0, pi].
    // Calculate sin(a)

    // Set precision to fit your needs.
    // Note that 171! > Double.MAX_VALUE, so
    // don't set PRECISION to anything greater
    // than 84 unless you are sure your
    // Factorial.factorial() can handle it
    final int PRECISION = 50;
    double temp = 0;
    for (int i = 0; i <= PRECISION; i++) {
        temp += power(-1, i) * (power(a, 2 * i + 1) / factorial(2 * i + 1));
    }

    return sign * temp;
}

  public static double rad2Degrees(double radians){
    double degrees = radians * 180.0 / PI;
    return degrees;
  }

  public static double degrees2Rads(double degrees){
    double radians = degrees * PI / 180.0;
    return radians;
  }

	public static double e(double x) {
		double ex;
		int n;
		ex = 0;
		for (n=0;n<100;n++) {
			ex = ex+(power(x,n))/factorial(n);
		}
		return ex;
	}

    public static double sqrt(double number) {
      double t;
    
      double squareRoot = number / 2;
    
      do {
        t = squareRoot;
        squareRoot = (t + (number / t)) / 2;
      } while ((t - squareRoot) != 0);
    
      return squareRoot;
  }

// precondition:  d is a nonnegative integer
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

}
