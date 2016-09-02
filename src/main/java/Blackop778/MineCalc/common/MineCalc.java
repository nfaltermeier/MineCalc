package Blackop778.MineCalc.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = MineCalc.MODID, name = MineCalc.MODNAME, version = MineCalc.MODVER, acceptableRemoteVersions = "*", guiFactory = "Blackop778.MineCalc.client.GuiFactoryMineCalc", updateJSON = "https://github.com/Blackop778/ModUpdateJsons/blob/master/MineCalc.json")
public class MineCalc
{
	public static final String MODID = "minecraftcalculator778";
	public static final String MODNAME = "MineCalc";
	public static final String MODVER = "3.0.0"; // According to
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
		MinecraftForge.EVENT_BUS.register(new MCConfig());
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		Style style = new Style();
		style = style.setColor(TextFormatting.RED);
		Calculate.redStyle = style.getFormattingCode();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	// Called when a server is started, both solo and multiplayer
	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{
		MinecraftServer server = event.getServer();
		ICommandManager command = server.getCommandManager();
		ServerCommandManager manager = (ServerCommandManager) command;
		manager.registerCommand(new Calculate());
	}
}
