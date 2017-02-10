package Blackop778.MineCalc.common;

import java.util.Comparator;

public class Argument implements Comparator<Argument> {
    public static final int MAXLOOPS = 100;

    public final int index;
    public final int importance;
    public String contents;

    protected Argument(int index, int importance, String contents) {
	this.index = index;
	this.importance = importance;
	this.contents = contents;
    }

    public Argument(Argument old) {
	this.index = old.index;
	this.importance = old.importance;
	this.contents = old.contents;
    }

    @Override
    public int compare(Argument first, Argument second) {
	int importanceOrder = new Integer(first.importance).compareTo(second.importance);
	if (importanceOrder != 0)
	    return -importanceOrder;
	return new Integer(first.index).compareTo(second.index);
    }
}
