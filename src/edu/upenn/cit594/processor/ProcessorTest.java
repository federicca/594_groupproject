package edu.upenn.cit594.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessorTest {
    
    Processor p;
    String violationsFilename = "src\\sample_files\\parking_test.csv";
    String propertiesFilename = "src\\sample_files\\properties_small.csv";
    String populationFilename = "src\\sample_files\\population.txt";
    
    @BeforeEach
    void setUp() {
        p = new Processor("csv");
        p.initializeData(violationsFilename, propertiesFilename, populationFilename);
    }

    @Test
    void testGetTotalPop() {
        fail("Not yet implemented");
    }

    @Test
    void testGetTotalFinesPerCapita() {
        int zip0 = 19103;
        double finesPC_0 = p.getTotalFinesPerCapita(zip0);
        assertEquals("Incorrect fines per capita for ZC: " + zip0, 329.0/21908.0, finesPC_0, 0.0000001);
    }

    @Test
    void testGetAverageMarketValue() {
        fail("Not yet implemented");
    }

    @Test
    void testGetAverageLiveableArea() {
        fail("Not yet implemented");
    }

    @Test
    void testGetTotalValuePC() {
        fail("Not yet implemented");
    }

}
