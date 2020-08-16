package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.TreeMap;

import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.logging.Logger;

public class PopulationReader extends SuperReader {

    TreeMap<Integer, ZipCode> zipCodeTreeMap = new TreeMap<>();
    
    public PopulationReader(String filename) {
        super(filename);
    }

    public TreeMap<Integer, ZipCode> processPop(Logger logger) throws IOException, ParseException {
        //Delimiter used in CSV file
        String DELIMITER = " ";
        String row;
        BufferedReader br = null;
        try {
            // open file for reading
            br = new BufferedReader(new FileReader(filename));
            
            // log opening of file
            logOpenFile(logger);
            
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist or was not found");
        }
        if(br != null) {
            while ((row = br.readLine()) != null) {
                //Get all tokens available in line
                String[] tokens = row.split(DELIMITER);
                if (tokens.length == 2) {
                    ZipCode zipCode = new ZipCode(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                    zipCodeTreeMap.put(Integer.parseInt(tokens[0]),zipCode);
                } else {
                    System.out.println("Incompatible CSV format: wrong number of tokens");
                }
            }
        }
        //Create unknown ZipCode object and add to tree map
        ZipCode unknownZC = new ZipCode(0,0);
        zipCodeTreeMap.put(-1,unknownZC);
        //Return TreeMap
        return zipCodeTreeMap;
    }
}
