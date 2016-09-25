package Blackop778.MineCalc.common;

import java.util.Comparator;

public class Argument implements Comparator<Argument>
{
	private int index;
	private double importance;
	private String contents;

	public Argument(int index, double importance, String contents)
	{
		this.index = index;
		this.importance = importance;
		this.contents = contents;
	}

	@Override
	public int compare(Argument o1, Argument o2)
	{
		int importanceOrder = o1.getImportance().compareTo(o2.getImportance());
		if(importanceOrder != 0)
			return importanceOrder;
		return -(o1.getIndex().compareTo(o2.getIndex()));

	}

	public Integer getIndex()
	{
		return new Integer(index);
	}

	public Double getImportance()
	{
		return new Double(importance);
	}

	public String getContents()
	{
		return contents;
	}

	public Argument changeImportance(Double importance)
	{
		this.importance = importance;

		return this;
	}

	public Argument changeContents(String contents)
	{
		this.contents = contents;

		return this;
	}
}
