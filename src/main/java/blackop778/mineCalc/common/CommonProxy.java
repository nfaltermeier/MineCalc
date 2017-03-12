package blackop778.mineCalc.common;

import blackop778.mineCalc.common.net.HasModMessage;
import blackop778.mineCalc.common.net.HasModMessage.HasModMessageHandler;
import blackop778.mineCalc.common.net.NetHub;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
	MCConfig.loadConfig(event.getModConfigurationDirectory());
	NetHub.NETWORKWRAPPER.registerMessage(HasModMessageHandler.class, HasModMessage.class,
		NetHub.packetDiscriminator++, Side.SERVER);
    }

    public void load(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStart(FMLServerStartingEvent event) {
	MinecraftServer server = event.getServer();
	ICommandManager command = server.getCommandManager();
	ServerCommandManager manager = (ServerCommandManager) command;
	manager.registerCommand(new Calculate());
    }
}
