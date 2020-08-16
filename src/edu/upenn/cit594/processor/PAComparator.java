package edu.upenn.cit594.processor;

public class PAComparator implements StateComparator {

    @Override
    public boolean equals(String state) {
        return state.equals("PA");
    }

}
