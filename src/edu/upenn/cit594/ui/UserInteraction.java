package edu.upenn.cit594.ui;

import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.processor.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;

public class UserInteraction {
    public void initUI (TreeMap<Integer, ZipCode> zipCodeTreeMap){
        String promptOpt = "\nEnter and option between 1-6, or 0 to Exit program\n" +
                "1. Total Population for All ZIP Codes\n" +
                "2. Total Fines Per Capita\n" +
                "3. Average Market Value\n" +
                "4. Average Total Livable Area\n" +
                "5. Total Residential Market Value Per Capita\n" +
                "6. Custom Feature\n";
        try{
            Scanner sc= new Scanner(System.in);
            System.out.print("Welcome User!" + promptOpt);
            int userOpt;
            int zipCode;
            while ((userOpt = sc.nextInt()) != 0) {
                //1. Total Population for All ZIP Codes
                if (userOpt == 1) {
                    TotalPopulation pop = new TotalPopulation();
                    int totalPop = pop.getTotalPop(zipCodeTreeMap);
                    System.out.println(totalPop);

                    // 2. Total Fines Per Capita
                } else if (userOpt == 2) {

                    // 3. Average Market Value
                } else if (userOpt == 3) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        Context context = new Context(new AverageMarketValue());
                        System.out.println(context.executeStrategy(zipCode, zipCodeTreeMap));
                    }
                    // 4. Average Total Livable Area
                } else if (userOpt == 4) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        Context context = new Context(new AverageLiveableArea());
                        System.out.println(context.executeStrategy(zipCode, zipCodeTreeMap));
                    }

                    // Total Residential Market Value Per Capita
                } else if (userOpt == 5) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        TotalValuePC valuePC = new TotalValuePC();
                        System.out.println(valuePC.getTotalValuePC(zipCode, zipCodeTreeMap));
                    }

                    // 6. Additional Feature
                } else if (userOpt == 6) {

                } else {
                    System.out.println("That's not a valid option. Please try again.");
                }
                System.out.println(promptOpt);
            }

        } catch (InputMismatchException exception) {
            // Output unexpected InputMismatchExceptions.
            System.out.println("Invalid Input. Program will exit.");
        }

    }
}
