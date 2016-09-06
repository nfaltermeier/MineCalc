package Blackop778.MineCalc.common;

public class ArgumentManager
{
	private String[] args;
    private int inIndex;
    private int outIndex;

    public ArgumentManager()
    {
        args = new String[0];
        inIndex = 0;
    }

    public @Nullable String getAnswer()
    {
    	for(int i = 0; i < args.length; i++)
    	{
    		if(!args[i].contains("$#"))
    		{
    			
    		}
    	}
    }
    
    public void addArg(String toAdd)
    {
    	args = extendArray(args);
    	args[inIndex] = toAdd;
    	inIndex++;
    }

    public static String insertArrayReference(int i)
    {
        return "$#" + String.valueOf(i) + "#";
    }
    
    public static <T>[] extendArray(<T>[] array)
    {
    	return extendArray(array, 1);
    }
    
    public static <T>[] extendArray(<T>[] array, int lengthToExtend)
    {
    	<T>[] newArray = new <T>[array.length + lengthToExtend];
    	for(int i = 0; i < array.length; i++)
    	{
    		newArray[i] = array[i];
    	}
    	
    	return newArray;
    }
}