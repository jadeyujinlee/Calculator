import java.util.Scanner; 
import java.util.LinkedList;
import java.io.StringReader; 
import java.util.Stack;
import java.text.ParseException;
import java.util.ArrayDeque; 

public class Calculate {
  /** Pattern that matches on words */
  public static final String WORD = "[a-zA-Z]*\\b";

  /** Pattern that matches on arithmetic symbols */
  public static final String SYMBOL = "[^\\w]"; 

  /** 
  * @param main 
  * runs Infix computation on command line input 
  * @throws ParseException 
  */
  public static void main(String[] args) throws ParseException {
	  if (args.length==0) {
	    System.err.println("Usage:  java Calculate <expr>");
	  } 
    else {
      Stack<Object> stack = new Stack<Object>();
      LinkedList<Object> queue = new LinkedList<Object>(); 
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
	    
      
	    while (input.hasNext()) {
		    if (input.hasNextDouble()) {
          queue.addLast(input.nextDouble());
		      //System.out.println(queue.getLast());
		    } 
        else if (input.hasNext(SYMBOL)) {
          String symbol = input.next(SYMBOL);
          
          if(isOperator(symbol)){
            Object o2 = new Object(); 
            if(stack.isEmpty()){
              o2 = "not an operator";
            }
            else{
              o2 = stack.peek(); 
            }
            while (!stack.isEmpty() && isOperator(o2) && isPrecedent(o2, symbol)){
              Object operator = stack.pop(); 
              queue.addLast(operator); 
              //System.out.println(queue.getLast());
            }
            stack.push(symbol); 
          }

          if(symbol.equals("(")){
            stack.push(symbol); 
          }
          if (symbol.equals(")")){
            while(!stack.isEmpty() && !stack.peek().equals("(")){
              Object item = stack.pop();
              queue.addLast(item);
              //System.out.println(queue.getLast());
            }
            if (stack.peek().equals("(")){
              stack.pop(); 
            }
          }
          
          
      } 
      else{
        input.close();
        throw new ParseException("ParseException", 0);
      }
	  }
      while(!stack.isEmpty()){
        if(stack.peek().equals("(")){
          stack.pop(); 
        }
        else{
          Object endingOperator = stack.pop(); 
          queue.addLast(endingOperator); 
          //System.out.println(queue.getLast()); 
        }
      }
      System.out.println(postFix(queue)); 
    }
    

    
  }
  /** 
  * @param symbol 
  * returns true or false if inputted symbol is a 
  * viable operator
  */ 
  public static boolean isOperator(Object symbol){
    if(symbol.equals("+") || symbol.equals("-") || symbol.equals("*") || symbol.equals("/")){
      return true;

    }
    else{
      return false; 
    }
    
  }
  /** 
  * @param o2, o1 
  * returns true or false if assigned o2 integer is 
  * smaller than or greater than the o1 integer. 
  */ 
  public static Boolean isPrecedent(Object o2, Object o1){
    int o1Value; 
    if(o1.equals("*") || o1.equals("/")){
      o1Value = 0;

    }
    else{
      o1Value = 1;
    }

    int o2Value; 
    if(o2.equals("*") || o2.equals("/")){
      o2Value = 0;

    }
    else{
      o2Value = 1;
    }

    if(o2Value <= o1Value){
      return true; 
    }
    else{
      return false; 
    }
  }
  
  /**
  * @param o
  * converts of Type Object to a double 
  * author StackOverflow @Dave B
  */ 
  public static Double asDouble(Object o){
    Double val = null; 
    if (o instanceof Number) {
      val = ((Number) o).doubleValue(); 
    }
    return val;
  }

  /**
  * @param inputQueue 
  * @returns result 
  * takes completed infix to postfix computation to
  * then compute and output the input's solution. 
  */ 
  public static Double postFix(LinkedList<Object> inputQueue) {
	  ArrayDeque<Double> stack = new ArrayDeque<Double>();
      for (int i = 0; i < inputQueue.size(); i++) {
        Object item = inputQueue.get(i); 
        if (item instanceof Double) {
          double myDouble = asDouble(item);
          stack.push(myDouble);    
        }  
        else if (item.equals("(") || item.equals(")") || isOperator(item)) {
          
          double secondNum = stack.pop();
          double firstNum = stack.pop();
          double result; 
          if(item.equals("+")){
            result = firstNum + secondNum;
            stack.push(result);
          }
          if(item.equals("-")){
            result = firstNum - secondNum;
            stack.push(result);
          }
          if(item.equals("*")){
            result = firstNum * secondNum;
            stack.push(result);
          }
          if(item.equals("/")){
            result = firstNum / secondNum;
            stack.push(result);
          }
          
        }
        
      
        
        
      }
      Double finalResult = stack.pop(); 
      return finalResult; 
    
	  }
    
}



 
