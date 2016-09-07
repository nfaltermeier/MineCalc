package Blackop778.MineCalc.client;

import Blackop778.MineCalc.common.CommonProxy;
import Blackop778.MineCalc.common.MCConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new MCConfig());
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

	// Called when a server is started, both solo and multiplayer
	@Override
	public void serverStart(FMLServerStartingEvent event)
	{
		super.serverStart(event);
	}
}
