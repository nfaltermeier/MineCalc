package Blackop778.MineCalc.common;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		MCConfig.loadConfig(event.getModConfigurationDirectory());
	}

	public void load(FMLInitializationEvent event)
	{

	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}

	public void serverStart(FMLServerStartingEvent event)
	{
		MinecraftServer server = event.getServer();
		ServerCommandManager commandManager = (ServerCommandManager) server.getCommandManager();
		commandManager.registerCommand(new Calculate());
	}
}
