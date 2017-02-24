package Blackop778.MineCalc.server;

import Blackop778.MineCalc.common.CommonProxy;
import Blackop778.MineCalc.common.net.ModCheckMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ServerProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
	super.preInit(event);
	MinecraftForge.EVENT_BUS.register(new ModCheckMessage());
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
