package blackop778.mineCalc.client;

import blackop778.mineCalc.client.ClientEventHandlers.SubEvent;
import blackop778.mineCalc.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ClientProxy extends CommonProxy {

    private static boolean isClientSide = false;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
	super.preInit(event);
	isClientSide = true;
	MinecraftForge.EVENT_BUS.register(new ClientEventHandlers());
	MinecraftForge.EVENT_BUS.register(new SubEvent());
    }

    @Override
    public void load(FMLInitializationEvent event) {
	super.load(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
	super.postInit(event);
    }

    @Override
    public void serverStart(FMLServerStartingEvent event) {
	super.serverStart(event);
    }

    public static boolean isClientSide() {
	return isClientSide;
    }
}
