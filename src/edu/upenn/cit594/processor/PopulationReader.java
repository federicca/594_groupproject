package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ZipCode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class PopulationReader{
    TreeMap<Integer, ZipCode> zipCodeTreeMap = new TreeMap<>();

    public TreeMap<Integer, ZipCode> processPop(String filename) throws IOException, ParseException {
        //Delimiter used in CSV file
        String DELIMITER = " ";
        String row;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist or was not found");
        }
        if(br != null) {
            while ((row = br.readLine()) != null) {
                //Get all tokens available in line
                String[] tokens = row.split(DELIMITER);
                // process and set lat long
                if (tokens.length == 2) {
                    ZipCode zipCode = new ZipCode(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), null, null);
                    zipCodeTreeMap.put(Integer.parseInt(tokens[0]),zipCode);
                } else {
                    System.out.println("Incompatible CSV format: wrong number of tokens");
                }
            }
        }
        return zipCodeTreeMap;
    }
}
