package Blackop778.MineCalc.common;

import java.util.ArrayList;
import java.util.Comparator;

public class ArgumentManager {
    private ArrayList<Argument> arguments;

    public ArgumentManager() {
	arguments = new ArrayList<Argument>();
    }

    public void add(Argument arg) {
	arguments.add(arg);
    }

    public void sort(Comparator<? super Argument> c) {
	arguments.sort(c);
    }

    public Argument get(int index) {
	return new Argument(arguments.get(index));
    }

    public int size() {
	return arguments.size();
    }
}
