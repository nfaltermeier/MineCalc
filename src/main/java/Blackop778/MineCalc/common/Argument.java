package Blackop778.MineCalc.common;

import java.util.Comparator;

public class Argument implements Comparator<Argument> {
    public final int index;
    public final double importance;
    private String contents;

    protected Argument(int index, double importance, String contents) {
	this.index = index;
	this.importance = importance;
	this.contents = contents;
    }

    @Override
    public int compare(Argument o1, Argument o2) {
	int importanceOrder = new Double(o1.importance).compareTo(o2.importance);
	if (importanceOrder != 0)
	    return importanceOrder;
	return -(new Integer(o1.index).compareTo(o2.index));

    }

    public String getContents() {
	return contents;
    }

    public Argument changeContents(String contents) {
	this.contents = contents;

	return this;
    }
}
