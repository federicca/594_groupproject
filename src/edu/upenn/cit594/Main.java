package edu.upenn.cit594;

import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.processor.PopulationReader;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if(args.length == 5) {
            String violationsFormat = args[0];
            String violationsFile = args[1];
            String propertyValuesFile = args[2];
            String populationFile = args[3];
            String logfile = args[4];
        }else{
            System.out.println("Error in runtime arguments.");
        }
        //Call population reader
        PopulationReader pr = new PopulationReader();
        //Create TreeMap for ZipCodes
        TreeMap<Integer, ZipCode> zipCodeTreeMap = new TreeMap<>();
        //Call function that populates the TreeMap and assigns the population value
        zipCodeTreeMap = pr.processPop("src/sample_files/population.txt");
        //Print for testing
        for (Map.Entry<Integer, ZipCode> entry : zipCodeTreeMap.entrySet()) {
            System.out.println("ZipCode: " + entry.getKey() + ". Population: " + entry.getValue().getPopulation());
        }
    }
}
