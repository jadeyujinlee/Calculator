import java.util.Scanner;
import java.io.StringReader;
import java.util.ArrayDeque;
import static java.lang.Math.pow;
import java.text.ParseException; 

/** 
 *  Shows use of StreamTokenizer.
 *  @author Nick Howe
 *  @version 26 September 2013
 */
public class Postfix {
  /** Pattern that matches on words */
  public static final String WORD = "[a-zA-Z]*\\b";

  /** Pattern that matches on arithmetic symbols */
  public static final String SYMBOL = "[^\\w]";

  /**
  * @param args 
  * @throws ParseException 
  * takes in command line input and computes the
  * input's solution by using stacks and queues for
  * doubles and symbols (operators). 
  */
  public static void main(String[] args) throws ParseException{
	  if (args.length==0) {
	    System.err.println("Usage:  java Postfix <expr>");
    } 
    else {
	    Scanner input = new Scanner(new StringReader(args[0]));
	    // Below is a complicated regular expression that will split the
	    // input string at various boundaries.
	    input.useDelimiter
		("(\\s+"                             // whitespace
		 +"|(?<=[a-zA-Z])(?=[^a-zA-Z])"      // word->non-word
		 +"|(?<=[^a-zA-Z])(?=[a-zA-Z])"      // non-word->word
		 +"|(?<=[^0-9\\056])(?=[0-9\\056])"  // non-number->number
		 +"|(?<=[0-9\\056])(?=[^0-9\\056])"  // number->non-number
		 +"|(?<=[^\\w])(?=[^\\w]))");        // symbol->symbol
	  ArrayDeque<Double> stack = new ArrayDeque<>();
    double result = 0;
    
      while (input.hasNext()) {
        if (input.hasNextDouble()) {
          stack.push(input.nextDouble());    
        }  
        else if (input.hasNext(SYMBOL)) {
          String symbol = input.next(SYMBOL);
          double secondNum = stack.pop();
          double firstNum = stack.pop();
          if(symbol.equals("+")){
            result = firstNum + secondNum;
            stack.push(result);
          }
          if(symbol.equals("-")){
            result = firstNum - secondNum;
            stack.push(result);
          }
          if(symbol.equals("*")){
            result = firstNum * secondNum;
            stack.push(result);
          }
          if(symbol.equals("/")){
            result = firstNum / secondNum;
            stack.push(result);
          }
          if(symbol.equals("^")){
            result = pow(firstNum, secondNum);
            stack.push(result);
          }
          //System.out.println("Symbol:  "+input.next(SYMBOL));
        } 
      
        else {
          input.close();
          throw new ParseException("ParseException on line 71", 0);
          //System.out.println("Unknown:  "+input.next()); 
        }
        
      }
    System.out.println(result);
	  }
  }
}