import java.text.DecimalFormat;

public class test
{

    public static double squareRootFunction(int input) //Takes a single int and uses maths to return square root
    {
        double a;                   //Variable to store final square root number
        double b = input/2;         //Variable to store user input/2
        do {
            a = b;
            b = (a+(input/a))/2;}
        while ((a-b)!=0);           //Loop until a subtract b is equal to 0
        return a;                   //Return the square root
    }

    public static String decimalFormat(int decimals) //Takes a single int and appends a string to "0.(input number of dec places)"
    {
        StringBuilder a = new StringBuilder("0.0"); //New string builder starting at 0.0
        for(int i = 1; i < decimals; i++) {         //For desired user number append that many 0's on end of string
            a.append("0");}
        return String.valueOf(a);                   //Return new decimal formatted string
    }

    public static void main(String[] args)
    {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.println(                           //Intro text
                """
                                                
                        Square Root Calculator
                        ----------------------
                        Please enter a positive number:""");
        int num1 = scanner.nextInt();                 //Number to find square root of from user
        System.out.println("How many decimal places do you want the solution calculated to:");
        int num2 = scanner.nextInt();                 //How many decimal places user wants

        double d = 1.0;                               //Creating variable for decimal places as double
        for(int i=0; i<num2; i++) {                   //Extends negative decimal by num2 times
            d = d/10;}                                //e.g. num2=3  1.0 -> 0.001

        String decStr = decimalFormat(num2);          //Calling decimalFormat function to get decimal places same as above -
                                                      //-but in a string with no 1 at end for decimal formatting final output

        double squareRoot = squareRootFunction(num1); //Calling squareRootFunction and applying maths to num1
        double U = num1*0.99;                         //Setting Upper band
        double L = num1*0.01;                         //Setting Lower band

        while(L-U > d) {                              //Loop while difference between lower and upper band is larger than decimal places (d)
            double avg = (L+U)/2;
            double squareAvg = avg*avg;
            if (squareAvg>num1) {                     //Updating Upper band if squared average is larger than num1
                U = avg;}
            else if (squareAvg<num1) {                //Updating Lower band if squared average is lower than num1
                L = avg;}
        }
        System.out.println("The square root of "+num1+" to "+num2+" decimal places is ");
        java.text.DecimalFormat formatter=new java.text.DecimalFormat(decStr); //Sets formatter to String decimal places
        System.out.println(formatter.format(squareRoot));                      //Prints square root to user formatted decimals
    }
}


