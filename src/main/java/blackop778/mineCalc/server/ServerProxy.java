package blackop778.mineCalc.server;

import blackop778.mineCalc.common.CommonProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
	super.preInit(event);
	MinecraftForge.EVENT_BUS.register(new ServerEventHandlers());
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
}
