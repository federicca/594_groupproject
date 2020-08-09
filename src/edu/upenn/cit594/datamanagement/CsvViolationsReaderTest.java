package edu.upenn.cit594.datamanagement;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.upenn.cit594.data.ParkingViolation;

/**
 * Make this inclusive of the JSON reader next time
 * @author seanb
 *
 */
class CsvViolationsReaderTest {
    
    String filename = "parking.csv";
    CsvViolationsReader cvr;

    @BeforeEach
    void setUp() {
        cvr = new CsvViolationsReader();
    }
    
    @Test
    void testGetAllViolations() {
        List<ParkingViolation> violations = cvr.getAllViolations(filename);
        ParkingViolation result0 = violations.get(0);
        ParkingViolation result31 = violations.get(31);
        ParkingViolation result25558 = violations.get(25558);
        
        /**
         * Test direct output
         * REVISE TIME STAMP UNIT TESTS***********
         */
        
        // result0 tests
        assertEquals("Incorrect time stamp for result0", "2013-04-03T15:15:00Z", result0.getTimeStamp().toString());
        assertEquals("Incorrect fine amount for result0", 36, result0.getFineDue());
        assertEquals("Incorrect description for result0", "METER EXPIRED CC", result0.getDescription());
        assertEquals("Incorrect car ID for result0", 1322731, result0.getCarID());
        assertEquals("Incorrect car state for result0", "PA", result0.getCarState());
        assertEquals("Incorrect violation ID for result0", 2905938, result0.getViolationID());
        assertEquals("Incorrect zip code for result0", 19104, result0.getZipCode());
        
        // result31 tests
        assertEquals("Incorrect time stamp for resul31", "2013-10-30T12:55:00Z", result31.getTimeStamp().toString());
        assertEquals("Incorrect fine amount for result31", 26, result31.getFineDue());
        assertEquals("Incorrect description for result31", "OVER TIME LIMIT", result31.getDescription());
        assertEquals("Incorrect car ID for result31", 1288203, result31.getCarID());
        assertEquals("Incorrect car state for result31", "PA", result31.getCarState());
        assertEquals("Incorrect violation ID for result31", 2905969, result31.getViolationID());
        assertEquals("Incorrect zip code for result31", 19146, result31.getZipCode());
       
        // result25558 tests
        assertEquals("Incorrect time stamp for result25558", "2013-12-09T10:10:00Z", result25558.getTimeStamp().toString());
        assertEquals("Incorrect fine amount for result25558", 26, result25558.getFineDue());
        assertEquals("Incorrect description for result25558", "METER EXPIRED CC", result25558.getDescription());
        assertEquals("Incorrect car ID for result25558", 204335, result25558.getCarID());
        assertEquals("Incorrect car state for result25558", "NJ", result25558.getCarState());
        assertEquals("Incorrect violation ID for result25558", 2931538, result25558.getViolationID());
        assertEquals("Incorrect zip code for result25558", 19147, result25558.getZipCode());
        
        /**
         * Check side effects
         */
        
        int size = violations.size();
        assertEquals("Incorrect size of list", 25559, size);

    }

}
