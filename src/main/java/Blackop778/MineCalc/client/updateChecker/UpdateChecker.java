package Blackop778.MineCalc.client.updateChecker;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import Blackop778.MineCalc.MineCalc;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.versioning.ComparableVersion;

public class UpdateChecker
{
	/**
	 * @return if there is an update for MineCalc
	 */
	public static boolean isUpdateAvailable()
	{
		String jsonString;
		try
		{
			jsonString = IOUtils.toString(new URL(MineCalc.UPDATEJSONURL));
		}
		catch(IOException e)
		{
			MineCalc.Logger.warn("IOException occured. Stack trace:");
			e.printStackTrace();
			jsonString = "RIP";
		}
		try
		{
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(jsonString);
			JsonObject promos = json.getAsJsonObject("promos");
			String currentVersion = promos.getAsJsonPrimitive(ForgeVersion.mcVersion + "-recommended").getAsString();
			String modVersionString = currentVersion.split("-")[1];
			ComparableVersion modVersion = new ComparableVersion(modVersionString);
			ComparableVersion clientVersion = new ComparableVersion(MineCalc.MODVER);

			return modVersion.compareTo(clientVersion) > 0;
		}
		catch(JsonParseException e)
		{
			MineCalc.Logger.warn("Failed to check for an update.");
		}
		return false;
	}
}
