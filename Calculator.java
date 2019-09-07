import java.util.Scanner;

public class Calculator{
    public static double[] memory_results = new double[10];
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
 * @param dynamic variable must have been declared, and its value must be passed in the arguments when this    * method is invoked.

*/
    public static void Operation(Scanner scan_num, Scanner scan_opr, boolean dynamic){
        double result = 0;
        int response = 1;
        String parser;
        double num1 = 0;
        double num2 = 0;
        String operation;

        System.out.println("\nIngrese un número (o función) y luego la operación:");
        parser = scan_opr.nextLine();
        num1 = functionsCalculator(parser);
        System.out.println(">> " + num1);
        while(response==1){
          operation = scan_opr.nextLine().substring(0, 1);    
            switch(operation){
                case "+":
                    parser = scan_opr.nextLine(); 
                    num2 = functionsCalculator(  parser);
                    result = num1 + num2;
                    System.out.println(">> "+ result);
                    break;
                case "-":
                    parser = scan_opr.nextLine(); 
                    num2 = functionsCalculator(parser);
                    result = num1 - num2;
                    System.out.println(">> "+ result);
                    break;
                case "*":
                    parser = scan_opr.nextLine(); 
                    num2 = functionsCalculator(parser);
                    result = num1*num2;
                    System.out.println(">> "+ result);
                    break;
                case "/":
                    parser = scan_opr.nextLine(); 
                    num2 = functionsCalculator(parser);
                    result = num1/num2;
                    System.out.println(">> "+ result);
                    break;
                case "%":
                    parser = scan_opr.nextLine(); 
                    num2 = functionsCalculator(parser);
                    result = num1%num2;
                    System.out.println(">> "+ result);
                    break;
                case "#":
                    if(dynamic){
                      System.out.println("Cambiando al modo de operación única...");
                      dynamic = false;
                      break;
                    } else {
                      System.out.println("Cambiando al modo de operación dinámica; ingresa la operación...");
                      dynamic = true;
                    }

                    break;
                default:
                    System.out.println("Ingresa una operación válida");
                    break;
            }
            if(dynamic){
              num1 = result;
            } else {
              System.out.println("¿Desea continuar con otra operación matemática? \n 1). Sí \n0). No, quiero salir.");
              response = scan_num.nextInt();
              if (response == 0){
                System.out.println("¡Adiós!");
                break;
                }
              parser = scan_opr.nextLine();
              num1 = functionsCalculator(parser);
            }
            memory_results = sliceArray(memory_results, result);
        }
    }

    public static double functionsCalculator(String parser){
      double num1=0;
      String[] params_array;
      double[] params;
      String params_string;
      String func_indicator;

      if ( parser.substring(0, 1).matches("[0-9]+") ) {
          /* If the first character is a number, then num1 is going to be a number */
            // System.out.println("Contiene número.");
            num1 = (double) Double.parseDouble(parser);
        } else {
          // System.out.println("NO Contiene número.");
          func_indicator = parser.split("[\\(\\)]")[0];
          params_string = (parser.split("[\\(\\)]").length == 1) ? "" : parser.split("[\\(\\)]")[1];
          params_array = params_string.split(",");
          params = new double[params_array.length];
          
          if(params_array.length > 1){
            for(int i=0; i<params_array.length; i++){
          /* Fill the  params array with the casted values in params_array. 
            func_indicator is, E.g. "cos" when "cos(0)".
            params[0] is the first argument of function. E.g. 9 when "ln(9)"
            params[1] is the second argument of function. E.g. 0 when "log(0,9)"
            */
            params[i] = (double) Double.parseDouble(params_array[i]);
            }
          }
                    
          // System.out.println("Función es: " + func_indicator);
          // System.out.println("Valor: " + func_value);
          switch(func_indicator){
            case "cos":
              num1 = Math.cos(params[0]);
              break;
            case "sin":
              num1 = Math.sin(params[0]);
              break;
            case "tan":
              num1 = Math.tan(params[0]);
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
              num1 = Math.exp(params[0]);
              break;
            case "fact":
              num1 = factorial(params[0]);
              break;
            case "rzc":
              num1 = Math.sqrt(params[0]);
              break;
            case "rz":
              num1 = Math.pow(params[0], 1/params[1]);
              break;
            case "pot":
              num1 = Math.pow(params[0], params[1]);
              break;
            case "grad":
              num1 = Math.toDegrees(params[0]);
              break;
            case "rad":
              num1 = Math.toRadians(params[0]);
              break;
            case "pi":
              num1 = 3.14159265359;
              break;
            case "mem":
              num1 = memory_results[ (int) params[0]+1];
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

    public static double factorial(double num) {
		double i;
		double fact_result;
		if (num==0) {
			fact_result = 1;
		} else {
			fact_result = 1;
			for (i=1;i<=num;i++) {
				fact_result = fact_result*i;
			}
		}
		return fact_result;
	}

	public static double cosine(double x) {
		double cosx;
		double n;
		cosx = 0;
		for (n=0;n<100;n++) {
			cosx = cosx+((Math.pow((-1),n)*Math.pow(x,(2*n)))/factorial(2*n));
		}
		return cosx;
	}

	public static double sine(double x) {
		double n;
		double sinx;
		sinx = 0;
		for (n=0;n<100;n++) {
			sinx = sinx+((Math.pow((-1),n)*Math.pow(x,(2*n+1)))/factorial(2*n+1));
		}
		return sinx;
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
}