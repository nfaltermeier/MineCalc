package Blackop778.MineCalc.common;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
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
		Style style = new Style();
		style = style.setColor(TextFormatting.RED);
		Calculate.redStyle = style.getFormattingCode();
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}

	public void serverStart(FMLServerStartingEvent event)
	{
		MinecraftServer server = event.getServer();
		ICommandManager command = server.getCommandManager();
		ServerCommandManager manager = (ServerCommandManager) command;
		manager.registerCommand(new Calculate());
	}
}
