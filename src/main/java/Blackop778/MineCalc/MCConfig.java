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

		if(config.hasChanged())
		{
			config.save();
		}
	}

	public static boolean returnInput;
	public static int rootTimes;

	public static Configuration getConfig()
	{
		return config;
	}

	public static File getConfigFile()
	{
		return configFile;
	}
}
