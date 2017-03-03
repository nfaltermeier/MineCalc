package Blackop778.MineCalc.common;

import Blackop778.MineCalc.common.MineCalcCompound.MineCalcCompoundStorage;
import Blackop778.MineCalc.common.net.HasModMessage;
import Blackop778.MineCalc.common.net.HasModMessage.HasModMessageHandler;
import Blackop778.MineCalc.core.Operations;
import Blackop778.MineCalc.common.net.NetHub;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
	Operations.addOperations();
	CapabilityManager.INSTANCE.register(IMineCalcCompound.class, new MineCalcCompoundStorage(),
		MineCalcCompound.class);
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
