package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.logging.Logger;

public class PropertiesReader extends SuperReader {


    int code = 0;
    double livableArea = 0;
    double marketValue = 0;
    String buildingCode =null;

    int zipCodePos = -1;
    int totalLivableAreaPos = -1;
    int marketValuePos = -1;
    int buildingCodePos = -1;
    
    public PropertiesReader(String filename, Logger logger) {
        super(filename, logger);
    }

    public void processProperties(TreeMap<Integer, ZipCode> zipCodeTreeMap) throws IOException {
        //Delimiter used in CSV file
        String DELIMITER = "(,)(?=(?:[^\"]|\"[^\"]*\")*$)";
        String row;
        BufferedReader br = null;
        try {
            // open file for reading
            br = new BufferedReader(new FileReader(filename));
            
            // log opening of file
            logOpenFile();
            
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist or was not found");
        }

        if(br != null) {
            String header = br.readLine();

            // Read header line and pass tokens to columnPositions to set index for each value
            if (header != null) {
                String[] columns = header.split(",");
                columnPositions(columns);
            }

            // Iterate through the rest of the document
            while ((row = br.readLine()) != null) {
                //Get all tokens available in line
                String[] tokens = row.split(DELIMITER);
                // Set values using the column positions
                parseTokens(tokens);
                // create new property object with the variables
                Property prop = new Property(code, livableArea, marketValue, buildingCode);
                // Print new property object
                // System.out.println("ZipCode: " + prop.getZipCode() + " Market Value: $" + prop.getMarketValue() + " Livable Area: " + prop.getLivableArea() + " Building Code: " + prop.getBuildingCode());

                // Get ZipCode object and check whether it has a properties ArrayList, if not create one
                if (zipCodeTreeMap.containsKey(code)) {
                    // add property to corresponding ZipCode array of properties
                    zipCodeTreeMap.get(code).addProperty(prop);
                }else{
                    // If the ZipCode doesn't exist add property to unknown ZipCode (-1)
                    zipCodeTreeMap.get(-1).addProperty(prop);
                }
            }
        }
    }

    // Method that takes in tokens from the header and assigns the index to each variable
    private void columnPositions (String[] columns){
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equals("zip_code")){
                zipCodePos = i;
            }else if (columns[i].equals("total_livable_area")){
                totalLivableAreaPos = i;
            }else if (columns[i].equals("market_value")){
                marketValuePos = i;
            }else if (columns[i].equals("building_code")){
                buildingCodePos = i;
            }
        }
    }

    // Gets tokens from row and processes them to the right data type and format
    private void parseTokens (String[]tokens) {
        //make ZipCode 5 digits
        String codeToTrim = tokens[zipCodePos];
        String[] trim = codeToTrim.split("(?<=\\G.....)");
        try {
            code = Integer.parseInt(trim[0]);
            livableArea = Double.parseDouble(tokens[totalLivableAreaPos]);
            marketValue = Double.parseDouble(tokens[marketValuePos]);
            buildingCode = tokens[buildingCodePos];
        } catch (NumberFormatException ignored) { }
    }
}
