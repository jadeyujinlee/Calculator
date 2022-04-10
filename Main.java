import java.text.ParseException;



class Main {
  public static void main(String[] args) {
    try{
    Calculate.main(args);
    }
    catch(ParseException e){
      System.err.println("Parse Exception" + e); 
    }
    
  }
}