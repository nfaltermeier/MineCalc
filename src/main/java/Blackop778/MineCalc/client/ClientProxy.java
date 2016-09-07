package Blackop778.MineCalc.client;

import Blackop778.MineCalc.common.CommonProxy;
import Blackop778.MineCalc.common.MCConfig;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		FMLCommonHandler.instance().bus().register(new MCConfig());
	}

	@Override
	public void load(FMLInitializationEvent event)
	{
		super.load(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event)
	{
		super.serverStarting(event);
	}
}
