package Blackop778.MineCalc.common;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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

		syncConfig();
	}

	public static void syncConfig()
	{
		returnInput = config.getBoolean("Prepend Input to Output", Configuration.CATEGORY_GENERAL, true, null);
		fancyRemainders = config.getBoolean("Display remainders with a fancy output", Configuration.CATEGORY_GENERAL,
				true, "Looks like: 5 % 2 = 2R1 versus 5 % 2 = 1");
		zeroMultWarns = config.getBoolean("Display warnings when multiplying by 0", Configuration.CATEGORY_GENERAL,
				true, "Also applied to power of 0");

		config.save();
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event)
	{
		if(event.modID.equalsIgnoreCase(MineCalc.MODID))
		{
			syncConfig();
		}
	}

	public static boolean returnInput;
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
