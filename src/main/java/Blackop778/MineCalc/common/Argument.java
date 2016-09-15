package Blackop778.MineCalc.common;

 public class Argument implements Comparator<double>
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
	 
	 public int compare(double o1, double o2)
	 {
		 return o1.compareTo(o2);
	 }
	 
	 public int getIndex()
	 {
		 return index;
	 }
	 
	 public double getImportance()
	 {
		 return importance;
	 }
	 
	 public String getContents()
	 {
		 return contents;
	 }
	 
	 public Argument changeImportance(double importance)
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