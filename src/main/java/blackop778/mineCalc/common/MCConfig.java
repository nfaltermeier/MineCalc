package blackop778.mineCalc.common;

import java.io.File;

import blackop778.mineCalc.MineCalc;
import net.minecraftforge.common.config.Configuration;

public class MCConfig {
    private static Configuration config;

    public static void loadConfig(File location) {
	File configFile = new File(location + "/MineCalc.cfg");
	if (!configFile.exists()) {
	    try {
		configFile.createNewFile();
	    } catch (Exception e) {
		MineCalc.LOGGER.error("Couldn't create a new config file. Reason:");
		MineCalc.LOGGER.error(e.getLocalizedMessage());
	    }
	}
	config = new Configuration(configFile);
	config.load();

	syncConfig();
    }

    public static void syncConfig() {
	returnInput = config.getBoolean("Prepend Input to Output", Configuration.CATEGORY_GENERAL, true,
		"Displays the arguments before the result");
	fancyRemainders = config.getBoolean("Display remainders with a fancy output", Configuration.CATEGORY_GENERAL,
		true, "Looks like: 5 % 2 = 2R1 versus 5 % 2 = 1");

	config.save();
    }

    public static boolean returnInput;
    public static boolean fancyRemainders;

    public static Configuration getConfig() {
	return config;
    }
}