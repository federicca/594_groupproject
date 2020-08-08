package edu.upenn.cit594.ui;

import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.datamanagement.*;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.InputMismatchException;

public class UserInteraction {
    public void initUI (TreeMap<Integer, ZipCode> zipCodeTreeMap){
        try{
            Scanner sc= new Scanner(System.in);    //System.in is a standard input stream
            System.out.print("Welcome User!\nEnter and option between 1-6, or 0 to Exit program\n");
            int userOpt;
            int zipCode;
            while ((userOpt = sc.nextInt()) != 0) {
                //1. Total Population for All ZIP Codes
                if (userOpt == 1) {
                    TotalPopulation pop = new TotalPopulation();
                    int totalPop = pop.getTotalPop(zipCodeTreeMap);
                    System.out.println("Total Population for all Zip Codes: " + totalPop);

                    // 2. Total Fines Per Capita
                } else if (userOpt == 2) {

                    // 3. Average Market Value
                } else if (userOpt == 3) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        AverageMarketValue avgMarketValue = new AverageMarketValue();
                        System.out.println("Average market value for ZipCode: " + avgMarketValue.averageMarketValue(zipCode, zipCodeTreeMap));
                    }
                    // 4. Average Total Livable Area
                } else if (userOpt == 4) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        Context context = new Context(new AverageLiveableArea());
                        System.out.println("Average Livable Area: " + context.executeStrategy(zipCode, zipCodeTreeMap));
                    }

                    // Total Residential Market Value Per Capita
                } else if (userOpt == 5) {
                    System.out.println("Enter a 5-digit ZipCode or 0 to go back to options");
                    if ((zipCode = sc.nextInt()) != 0) {
                        TotalValuePC valuePC = new TotalValuePC();
                        System.out.println("Total Residential Market Value Per Capita: " + valuePC.getTotalValuePC(zipCode, zipCodeTreeMap));
                    }

                    // 6. Additional Feature
                } else if (userOpt == 6) {

                } else {
                    System.out.println("That's not a valid option. Please try again.");
                }
                System.out.println("\nEnter and option between 1-6, or 0 to Exit program");
            }

        } catch (InputMismatchException exception) {
            // Output unexpected InputMismatchExceptions.
            System.out.println("Invalid Input. Program will exit.");
        }

    }
}
