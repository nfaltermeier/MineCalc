package Blackop778.MineCalc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;

@Mod(modid = MineCalc.MODID, name = MineCalc.MODNAME, version = MineCalc.MODVER, acceptableRemoteVersions = "*")
public class MineCalc
{
	public static final String MODID = "minecraftcalculator778";
	public static final String MODNAME = "MineCalc";
	public static final String MODVER = "1.0.3.0"; // According to
													// https://mcforge.readthedocs.org/en/latest/conventions/versioning/
	public static final Logger Logger = LogManager.getLogger(MODID);

	public MineCalc()
	{
		Logger.info("Everytime you divide by zero a computer cries");
	}

	@Instance(value = MineCalc.MODID)
	public static MineCalc instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		MCConfig.loadConfig(event.getModConfigurationDirectory());
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	// Called when a server is started, both solo and multiplayer
	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{
		MinecraftServer server = MinecraftServer.getServer();
		ICommandManager command = server.getCommandManager();
		ServerCommandManager manager = (ServerCommandManager) command;
		manager.registerCommand(new Calculate());
	}
}
