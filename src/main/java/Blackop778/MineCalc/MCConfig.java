package Blackop778.MineCalc;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class MCConfig
{
	public static void loadConfig(File location)
	{
		File configFile = new File(location + "/MineCalc.cfg");
		if (!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
			}
			catch (Exception e)
			{
				MineCalc.Logger.warn("Couldn't create a new config file. Reason:");
				MineCalc.Logger.warn(e.getLocalizedMessage());
			}
		}
		Configuration config = new Configuration(configFile);
		config.load();

		returnInput = config.get("Options", "Prepend Input to Output", true).getBoolean(true);

		if (config.hasChanged())
		{
			config.save();
		}
	}

	public static boolean returnInput;
}
