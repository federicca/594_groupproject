package edu.upenn.cit594;

import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInteraction;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if(args.length != 5) {
            System.out.println("Error in runtime arguments.");
            return;
        }
        String violationsFormat = args[0];
        String violationsFile = args[1];
        String propertyValuesFile = args[2];
        String populationFile = args[3];
        String logfile = args[4];

        // Create Processor object
        Processor processor = new Processor(violationsFormat);

        System.out.println("Please wait while we process the data...");

        // Initialize data
        processor.initializeData(violationsFile, propertyValuesFile, populationFile);

        UserInteraction ui = new UserInteraction(processor);
        ui.initUI();

    }
}
