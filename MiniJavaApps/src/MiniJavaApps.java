//Liam Hammond - Module Code: 55-407816 - Assignment Title: Mini Java Apps

import java.util.Scanner;

public class MiniJavaApps
{
    static Scanner scanner = new Scanner(System.in);                  //Setting global scanner for all apps to use

    public static void main(String[]args)
    {
        boolean menu = true;
        do
        {
            try {
                System.out.println(
                        """
                                    
                                    
                                P4CS Mini Applications
                                ----------------------
                                Please select an option:
                                1) Keep Counting Game
                                2) Square Root Calculator
                                3) Check-Digit Generator
                                4) Check-Digit Checker
                                9) Quit
                                    
                                Please enter option:""");
                int userAppSelection = scanner.nextInt();

                switch (userAppSelection) {
                    case 1 -> keepCounting();        //Keep Counting Game
                    case 2 -> squareRoot();          //Square Root Calculator
                    case 3 -> checkDigitGenerator(); //Check-Digit Generator
                    case 4 -> checkDigitChecker();   //Check-Digit Checker
                    case 9 -> {                      //Quit
                        System.out.print("Quit");
                        menu = false;}               //Quit loop and app by changing boolean to false
                    default -> System.out.println("Please select an option from the menu with either 1,2,3,4 or 9");
                }
            } catch(Exception e) {
                System.out.println("Incorrect input, returning to menu");
                scanner.nextLine();}
        }
        while (menu);
    }

    //----------Method for Square Root Calculator-----------------------------------------------------------------------
    public static String decimalFormat(int decimals)                  //Takes a single int and appends a string to "0.(input number of dec places)"
    {
        StringBuilder a = new StringBuilder("0.0"); //New string builder starting at "0.0"
        for(int i = 1; i < decimals; i++) {         //For desired user number append that many 0's on end of string
            a.append("0");}
        return String.valueOf(a);                   //Return new decimal formatted string
    }
    //----------Methods for Check Digit Generator and Check Digit Checker-----------------------------------------------
    public static int lengthChecker(int userNumber, int lengthWanted) //Checks length of a variable and compares it to a variable length
    {
        boolean correct = false;                                      //Creating a boolean to run the while loop until it's true
        while (!correct)                                              //While the lengths incorrect repeat code block
        {
            int userNumLength = String.valueOf(userNumber).length();  //Convert to string to count the amount of digits in the first input
            if (userNumLength == lengthWanted) {                      //When user number is the correct length break the loop as it's correct
                correct = true;}
            else {                                                    //When incorrect prompt user for a correct input and take new input
                System.out.println("Incorrect length input, please enter a " + lengthWanted + "-digit number:");
                userNumber = scanner.nextInt();}
        }
        return userNumber;                                            //Return the correct length user input
    }
    public static int checkDigitFunctions(int userNum)                //Takes a 5 digit int, returns a single int
    {
        int checkDigit = 0;                  //Declaring variable to assign the check digit too

        int num1 = userNum / 10000;          //Breaking down user input into 5 separate digits
        int num2 = userNum / 1000;
        int num3 = userNum / 100;
        int num4 = userNum / 10;
        int num5 = userNum - num4 * 10;

        int odds = (num1 + num3 + num5) * 7; //Addition of odd position numbers multiplied by 7
        int evens = (num2 + num4) * 3;       //Addition of even position numbers multiplied by 3
        int mod = (odds + evens) % 10;       //Adding odds and evens, modulo by 10
        if (mod >= 1) {                      //If remainder is 1-9 then subtract from 10
            checkDigit = 10 - mod;}          //Update check digit with new value

        return checkDigit;                   //Returning the single check digit
    }
    //------------------------------------------------------------------------------------------------------------------


    //----------Mini Apps----------------------
    public static void keepCounting()
    {
        /* TEST PLAN - keepCounting
         * INPUTS           EXPECTED OUTPUTS
         * Enter            First question is displayed with two random number 1-9 inclusive
         * 10 answers       9 further questions with the first number being the sum of the prior question
         *                  Timer of how long questions were answered if all 10 were correct
         */

        try {
            long startTime = System.currentTimeMillis();                 //Start timer

            System.out.println( //Intro text
                    """
                                                    
                            Keep Counting
                            -------------
                            You will be presented with 10 addition questions
                            After the first question, the left-hand operand is the result of the previous addition.
                            Press enter to start...""");

            scanner.nextLine();                                           //Clearing scanner
            scanner.nextLine();                                           //User 'Enter' to begin

            int score = 0;                                                //Set score to see if all 10 questions are answered correct
            int firstNum = (int) (0 + (Math.random() * (10 + 1)));        //First 1-10 inclusive number, only displayed in first question
            for (int i = 1; i < 11; i++)                                  //For loop to display 10 questions
            {
                int secondNum = (int) (0 + (Math.random() * (10 + 1)));   //Second 1-10 inclusive number, in all further questions

                String[] plusAndMinus = {" + ", " - "};                   //Array to store plus or minus string
                int plusMinusNum = (int) (0 + (Math.random() * (1 + 1))); //Random number either 0 or 1 to call value from array

                System.out.println("Question " + i + ": " + firstNum + plusAndMinus[plusMinusNum] + secondNum + " =");
                int userGuess = scanner.nextInt();                        //Displaying question and getting user guess
                int sum = plusMinusNum == 0 ? firstNum + secondNum : firstNum - secondNum; //If question was +/- and updating sum
                if (userGuess == sum) {                                   //Checking if user guess was correct
                    firstNum = sum;                                       //Updating first number to become last answer
                    score++;
                }                                             //Increasing score tally
                else {
                    System.out.println("Sorry, the correct answer was: " + sum);
                    break;
                }                                               //Exiting loop if user guesses incorrectly
            }
            long endTime = System.currentTimeMillis();                    //Finish timer
            long testTime = (endTime - startTime) / 1000;                 //Calculating how long user took to answer questions
            if (score == 10) {                                            //Only display time taken if all 10 questions are correct
                System.out.println("Well done! All questions were completed in " + testTime + " seconds!");
            }
        } catch (Exception e) {
            System.out.println("Error, returning to menu");}
    }

    public static void squareRoot()
    {
        /* TEST PLAN - squareRoot
         * INPUTS                         EXPECTED OUTPUTS
         * Positive int                   Square root | Upper/Lower band refinement | Square root to decimal placement
         * How many decimal places 1-7
         *
         * 5, 4                           2.2360679775| (upper-lower) > 0.0001      | 2.2361
         * 38, 7                          6.1644140029| (upper-lower) > 0.0000001   | 6.1644140
         * -4, 0                          negative num| N/A                         | N/A + decimal needs to be 1-7
         */

        System.out.println(                                   //Intro text
                """
                                                
                        Square Root Calculator
                        ----------------------
                        Please enter a positive number:""");

        int num1 = 0;                                         //Creating variable to store first user input
        try {
            num1 = scanner.nextInt();                         //Number to find square root of from user
            while (num1 < 0) {                                //Testing if the user number is negative
                System.out.println("Please enter a positive number only:");
                num1 = scanner.nextInt();
            }
        } catch (Exception e) {
            System.out.println("Incorrect input, returning to menu");
        }

        boolean correctInput = false;                         //Creating a boolean to run loop until second input is correct
        while(!correctInput)
        {
            System.out.println("How many decimal places do you want the solution calculated to between 1-7:");
            int num2 = scanner.nextInt();                     //How many decimal places user wants

            if(num2>0 && num2<8) {                            //Checking if input is between 1-7 only
                correctInput = true;
                double decPrecision = 1.0;                    //Creating variable for decimal places as double
                for (int i = 0; i < num2; i++) {              //Extends negative decimal by num2 times
                    decPrecision = decPrecision / 10;}        //e.g. num2=3  1.0 -> 0.001
                String decStr = decimalFormat(num2);          //Calling decimalFormat function to get decimal places same as above -
                                                              //-but in a string with no 1 at end for decimal formatting final output

                double upper = num1;                          //Setting upper band
                double lower = 0;                             //Setting lower band
                double avg = 0;                               //Setting the average of the upper and lower bands

                while ((upper-lower) > decPrecision) {        //Loop while difference between lower and upper band is larger than decimal places (decPrecision)
                    avg = (lower + upper) / 2;
                    double squareAvg = avg * avg;
                    if (squareAvg > num1) {                   //Updating upper band if squared average is larger than num1
                        upper = avg;}
                    else if (squareAvg < num1) {              //Updating lower band if squared average is lower than num1
                        lower = avg;}
                }
                System.out.println("The square root of " + num1 + " to " + num2 + " decimal places is: ");
                java.text.DecimalFormat formatter = new java.text.DecimalFormat(decStr); //Sets formatter to String decimal places
                System.out.println(formatter.format(avg));                               //Prints square root to user formatted decimals
            }
            else {
                System.out.println("You didn't enter a number between 1-7");             //Prompts user to input between 1-7, restarts loop
            }
        }
    }

    /* TEST PLAN - checkDigitGenerator
     * INPUTS           EXPECTED OUTPUTS
     * 5 Digit int      odd position  | even position | add two totals | if mod = 0 check | else 10 - remainder | append
     *                  digits * 7    | digits * 3    | and mod 10     | digit is 0       |                     |
     *
     * 12345            (1+3+5)*7= 63 | (2+4)*3= 18   | (63+18)%10= 1  | false            | 10-1= 9             | 123459
     * 38379            (3+3+9)*7= 105| (8+7)*3= 45   | (105+45)%10= 0 | 0                | false               | 383790
     *
     *
     * ACTUAL TEST OUTPUTS
     * 34777 -> 347778  (3+7+7)*7= 119| (4+7)*3= 33   | (119+33)%10= 2 | false            | 10-2= 8             | 347778
     * 48ff -> "You may have entered something that isn't an integer, Please enter a 5-digit number excluding 0 to generate the final code:"
     * 8888888 -> "Incorrect length input, please enter a 5-digit number:"
     * 98765 -> 987651  (9+7+5)*7= 147| (8+6)*3= 42   | (147+42)%10= 9 | false            | 10-9= 1             | 987651
     * 38379 -> 383790  (3+3+9)*7= 105| (8+7)*3= 45   | (105+45)%10= 0 | 0                | false               | 383790
     */ //Copy of test plan for Check-Digit Generator
    public static void checkDigitGenerator()
    {
        /* TEST PLAN - checkDigitGenerator
         * INPUTS           EXPECTED OUTPUTS
         * 5 Digit int      odd position  | even position | add two totals | if mod = 0 check | else 10 - remainder | append
         *                  digits * 7    | digits * 3    | and mod 10     | digit is 0       |                     |
         *
         * 12345            (1+3+5)*7= 63 | (2+4)*3= 18   | (63+18)%10= 1  | false            | 10-1= 9             | 123459
         * 38379            (3+3+9)*7= 105| (8+7)*3= 45   | (105+45)%10= 0 | 0                | false               | 383790
         *
         *
         * ACTUAL TEST OUTPUTS
         * 34777 -> 347778  (3+7+7)*7= 119| (4+7)*3= 33   | (119+33)%10= 2 | false            | 10-2= 8             | 347778
         * 48ff -> "You may have entered something that isn't an integer, Please enter a 5-digit number excluding 0 to generate the final code:"
         * 8888888 -> "Incorrect length input, please enter a 5-digit number:"
         * 98765 -> 987651  (9+7+5)*7= 147| (8+6)*3= 42   | (147+42)%10= 9 | false            | 10-9= 1             | 987651
         * 38379 -> 383790  (3+3+9)*7= 105| (8+7)*3= 45   | (105+45)%10= 0 | 0                | false               | 383790
         */

        System.out.println( //Intro text
                """
                                                
                        Check-Digit Calculator
                        ----------------------
                        This calculator will take a 5-digit number and generate a trailing 6th check digit.""");

        boolean correctInput = false;                                //Creating a boolean to run loop until input is correct
        while (!correctInput)
        {
            System.out.println("Please enter a 5-digit number excluding 0 to generate the final code:");
            try                                                      //Taking inputs in a try catch to eliminate the -
            {                                                        //-user inputting unwanted letters or characters
                int userNum = scanner.nextInt();
                userNum = lengthChecker(userNum, 5);    //Running the user number through the lengthChecker function

                if(!String.valueOf(userNum).contains("0")) {         //If the user number doesn't contain a 0
                    int checkDigit = checkDigitFunctions(userNum);   //Calling checkDigitFunctions method to apply the maths to user input
                    int finalNumber = (userNum * 10) + checkDigit;   //appending check digit to end of user number
                    System.out.println("The 6-digit final number is: " + finalNumber); //printing final output to user
                    correctInput = true;}                            //Input is correct and loop can be exited
                else {
                    System.out.println("You didn't enter a 5 digit number or it included a 0");}
            } catch (Exception e) {                                  //User entered an invalid character, restart loop
                System.out.println("You may have entered something that isn't an integer");
                scanner.nextLine();
            }
        }
    }

    public static void checkDigitChecker()
    {
        /* TEST PLAN - checkDigitChecker
         * INPUTS           EXPECTED OUTPUTS
         * 6 Digit int      Check digit at end | 5 digit num          | Confirm 5 digit check digit | compare check digits
         *
         * 123459           123459%10= 9       | (123459-9)/10= 12345 | checkDigitFunctions= 9      | 9 == 9 Valid
         * 383795           383795%10= 5       | (383795-5)/10= 38379 | checkDigitFunctions= 0      | 0 != 5 Invalid
         */

        System.out.println(                                             //Intro text and user input
                """
                                                
                        Check-Digit Checker
                        ----------------------
                        Please enter a 6-digit number to check the Check-Digit:""");

        int userNum = scanner.nextInt();
        lengthChecker(userNum, 6);                          //Calling lengthChecker method to ensure 6-digit number is entered by user
        int checkDigit = userNum % 10;                                  //Retrieving the single digit check digit from user input
        int fiveDigitNum = (userNum-checkDigit)/10;                     //Separating the check digit from the user input to have the 5-digit number

        if (checkDigitFunctions(fiveDigitNum) == checkDigit) {          //Calling checkDigitFunctions to pass the 5-digit number through
            System.out.println("The Check-Digit number is valid");}     //If the check digit from function is equal to the one from the 6-digit it's valid
        else {
            System.out.println("The Check-Digit number is invalid");}   //If check digits do not match then the number is invalid
    }
}


