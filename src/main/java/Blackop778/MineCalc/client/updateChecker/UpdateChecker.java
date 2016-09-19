package Blackop778.MineCalc.client.updateChecker;

import java.io.IOException;
import java.net.URL;

import javax.annotation.Nullable;

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
	 * @return the message to send to the client about an update or null if no
	 *         update
	 */
	public static @Nullable String checkForUpdate()
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
			if(modVersion.compareTo(clientVersion) > 0)
			{
				JsonObject modVersions = json.getAsJsonObject(ForgeVersion.mcVersion);
				String updateMessage = modVersions.getAsJsonPrimitive(currentVersion).getAsString();

				return updateMessage;
			}
		}
		catch(JsonParseException e)
		{
			MineCalc.Logger.warn("Failed to check for an update.");
		}
		return null;
	}
}
