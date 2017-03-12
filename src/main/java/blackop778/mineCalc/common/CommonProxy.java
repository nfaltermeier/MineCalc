package blackop778.mineCalc.common;

import blackop778.mineCalc.common.MineCalcCompound.MineCalcCompoundStorage;
import blackop778.mineCalc.common.net.HasModMessage;
import blackop778.mineCalc.common.net.HasModMessage.HasModMessageHandler;
import blackop778.mineCalc.common.net.NetHub;
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