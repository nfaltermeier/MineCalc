package Blackop778.MineCalc;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class MCConfig
{
	private static Configuration config;
	private static File configFile;

	public static void loadConfig(File location)

	{
		configFile = new File(location + "/MineCalc.cfg");
		if(!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
			}
			catch(Exception e)
			{
				MineCalc.Logger.warn("Couldn't create a new config file. Reason:");
				MineCalc.Logger.warn(e.getLocalizedMessage());
			}
		}
		config = new Configuration(configFile);
		config.load();

		returnInput = config.get("Options", "Prepend Input to Output", true).getBoolean(true);
		rootTimes = config.get("Options", "Times to estimate roots", 15, "More times makes estimates more accurate")
				.getInt();
		fancyRemainders = config.get("Options", "Display remainders with a fancy output", true,
				"Looks like: 5 % 2 = 2R1 versus 5 % 2 = 1").getBoolean(true);
		zeroMultWarns = config
				.get("Options", "Display warnings when multiplying by 0", true, "Also applied to power of 0")
				.getBoolean(true);

		if(config.hasChanged())
		{
			config.save();
		}
	}

	public static boolean returnInput;
	public static int rootTimes;
	public static boolean fancyRemainders;
	public static boolean zeroMultWarns;

	public static Configuration getConfig()
	{
		return config;
	}

	public static File getConfigFile()
	{
		return configFile;
	}
}
