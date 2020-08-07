package edu.upenn.cit594.datamanagement;


import edu.upenn.cit594.data.ZipCode;

import java.util.TreeMap;

public interface Strategy {
    int getAverage(int zipCode, TreeMap<Integer, ZipCode> zipCodeTreeMap);
}

