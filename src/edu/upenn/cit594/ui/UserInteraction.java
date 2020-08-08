package edu.upenn.cit594.ui;

import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.datamanagement.*;

import java.util.Scanner;
import java.util.TreeMap;

public class UserInteraction {
    public void initUI (TreeMap<Integer, ZipCode> zipCodeTreeMap){
        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("Welcome User!\nEnter and option between 1-6, or 0 to Exit program\n");
        int userOpt;
        while ((userOpt = sc.nextInt()) != 0){
            if(userOpt == 1){
                TotalPopulation pop = new TotalPopulation();
                int totalPop = pop.getTotalPop(zipCodeTreeMap);
                System.out.println("Total Population for all Zip Codes: " + totalPop);
            }else if (userOpt==2){

            } else if (userOpt==3){
                AverageMarketValue avgMarketValue = new AverageMarketValue();
                System.out.println("Average market value for ZipCode: " + avgMarketValue.averageMarketValue(19143,zipCodeTreeMap));
            }else if (userOpt==4){
                Context context = new Context(new AverageLiveableArea());
                System.out.println("Average Livable Area: " + context.executeStrategy(19143,zipCodeTreeMap));
            }else if (userOpt==5){
                TotalValuePC valuePC = new TotalValuePC();
                System.out.println("Total Residential Market Value Per Capita: " +  valuePC.getTotalValuePC(19143,zipCodeTreeMap));
            }else if (userOpt==6){

            }

            System.out.println("\nEnter and option between 1-6, or 0 to Exit program");
        }
    }
}
