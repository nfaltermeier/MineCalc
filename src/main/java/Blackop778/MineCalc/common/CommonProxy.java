package Blackop778.MineCalc.common;

import Blackop778.MineCalc.MineCalc;
import Blackop778.MineCalc.common.net.HasModMessage;
import Blackop778.MineCalc.common.net.HasModMessage.HasModMessageHandler;
import Blackop778.MineCalc.common.net.ModCheckMessage;
import Blackop778.MineCalc.common.net.ModCheckMessage.ModCheckMessageHandler;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
	MCConfig.loadConfig(event.getModConfigurationDirectory());
	Operations.addOperations();
	MineCalc.NETWORKWRAPPER.registerMessage(ModCheckMessageHandler.class, ModCheckMessage.class,
		MineCalc.packetDiscriminator++, Side.CLIENT);
	MineCalc.NETWORKWRAPPER.registerMessage(HasModMessageHandler.class, HasModMessage.class,
		MineCalc.packetDiscriminator++, Side.SERVER);
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
