package Blackop778.MineCalc.common;

import java.util.Comparator;

public class Argument implements Comparator<Argument>
{
	private int index;
	private Double importance;
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
		return o1.getImportance().compareTo(o2.getImportance());
	}

	public int getIndex()
	{
		return index;
	}

	public Double getImportance()
	{
		return importance;
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
