import java.util.Scanner;

public class Main{
    public static double[] memory_results = new double[10];
    final static double PI = 3.14159265358979323846;
    public static void main(String[] args){
        int election;
        boolean dynamic;
        Scanner scan_number = new Scanner(System.in);
        Scanner scan_operation = new Scanner(System.in);
        
        System.out.println("Bienvenido a tu calculadora. Ingrese \n1). Para acceder al modo de una operación a la vez (modo único). \n2). Para flujo de operaciones (modo dinámico). \n Cualquier otro número para abandonar.");
        election = scan_number.nextInt();
        switch(election){
            case 1:
                dynamic = false;
                Operation(scan_number, scan_operation, (boolean) dynamic);
                break;

            case 2: 
                dynamic = true;
                System.out.println("\nBienvenido al modo dinámico. Si quiere regresar al modo 1, ingrese #.");
                Operation(scan_number, scan_operation, (boolean) dynamic);
                break;
            default:
                System.out.println("¡Adiós!"); 
                break;
        }
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
 * @param dynamic variable must have been declared, and its value must be passed in the arguments when this * method is invoked.
*/
    public static void Operation(Scanner scan_num, Scanner scan_opr, boolean dynamic){
        double result = 0;
        char response = 'y';
        String parser;
        double num1 = 0;
        double num2 = 0;
        char operation;

        System.out.println("\n Type a number (or function) and later the operand (+,-,*, or /):");
        parser = scan_opr.nextLine();
        num1 = functions_calculator(parser);
        System.out.println(">> " + num1);
        while(response != 'n'){
          operation = scan_opr.nextLine().charAt(0);    
            switch(operation){
                case '+':
                    parser = scan_opr.nextLine(); 
                    num2 = functions_calculator(  parser);
                    result = num1 + num2;
                    System.out.println(">> "+ result);
                    break;
                case '-':
                    parser = scan_opr.nextLine(); 
                    num2 = functions_calculator(parser);
                    result = num1 - num2;
                    System.out.println(">> "+ result);
                    break;
                case '*':
                    parser = scan_opr.nextLine(); 
                    num2 = functions_calculator(parser);
                    result = num1*num2;
                    System.out.println(">> "+ result);
                    break;
                case '/':
                    parser = scan_opr.nextLine(); 
                    num2 = functions_calculator(parser);
                    result = num1/num2;
                    System.out.println(">> "+ result);
                    break;
                case '%':
                    parser = scan_opr.nextLine(); 
                    num2 = functions_calculator(parser);
                    result = num1%num2;
                    System.out.println(">> "+ result);
                    break;
                case '#':
                    if(dynamic){
                      System.out.println("Changing to the unique-operation mode...");
                      dynamic = false;
                      break;
                    } else {
                      System.out.println("Changing to the dynamic operation mode...");
                      System.out.println("\n Type a number (or function) and later the operand (+,-,*, or /):");
                      dynamic = true;
                    }

                    break;
                default:
                    System.out.println("Type a valid operation (+, -, * or /)");
                    break;
            }
            if(dynamic){
              num1 = result;
            } else {
              System.out.println("Do you want to input another mathematical operation? Type: \n y: Yes \n n: No, exit. \n #: Change to dynamic mode");
              response = scan_opr.nextLine().charAt(0);
              if (response == 'n'){
                System.out.println("Goodbye!");
                break;
                } else if(response == '#'){
                  dynamic = true;
                  System.out.println("Changing to the dynamic operation mode...");
                  System.out.println("\nType a number (or function) and later the operand (+,-,*, or /):");
                }
              parser = scan_opr.nextLine();
              num1 = functions_calculator(parser);
            }
            memory_results = sliceArray(memory_results, result);
        }
    }

    public static double functions_calculator(String parser){
      double num1=0;
      String[] params_array;
      double[] params;
      String params_string;
      String func_indicator;
      boolean isOnlyNumber = false;

      try {
        num1 = (double) Double.parseDouble(parser);
        isOnlyNumber = true;
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
                String mem_res_text = "\n Los últimos diez resultados en memoria son: \n";
                for(int i=0; i<memory_results.length; i++){
                  mem_res_text += "\n "+ "#" + (i+1) + "   " + memory_results[i]; 
                }
                System.out.println(mem_res_text);
              break;
            default:
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

    public static int power(int x, int y) 
    { 
        if (y == 0) 
            return 1; 
        else if (y % 2 == 0) 
            return power(x, y / 2) * power(x, y / 2); 
        else
            return x * power(x, y / 2) * power(x, y / 2); 
    } 

    public static double factorial(double num) {
		double i;
		double resultado;
		if (num==0) {
			resultado = 1;
		} else {
			resultado = 1;
			for (i=1;i<=num;i++) {
				resultado = resultado*i;
			}
		}
		return resultado;
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
        temp += Math.pow(-1, i) * (Math.pow(a, 2 * i + 1) / factorial(2 * i + 1));
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
		double n;
		ex = 0;
		for (n=0;n<100;n++) {
			ex = ex+(Math.pow(x,n))/factorial(n);
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
