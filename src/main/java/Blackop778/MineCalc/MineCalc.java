package Blackop778.MineCalc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Blackop778.MineCalc.common.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = MineCalc.MODID, name = MineCalc.MODNAME, version = MineCalc.MODVER, acceptableRemoteVersions = "*", guiFactory = "Blackop778.MineCalc.client.config.GuiFactoryMineCalc")
public class MineCalc
{
	public static final String MODID = "minecraftcalculator778";
	public static final String MODNAME = "MineCalc";
	public static final String MODVER = "3.1.2";
	public static final Logger Logger = LogManager.getLogger(MODID);

	@SidedProxy(clientSide = "Blackop778.MineCalc.client.ClientProxy", serverSide = "Blackop778.MineCalc.common.CommonProxy")
	public static CommonProxy proxy;

	public MineCalc()
	{
		Logger.info("Everytime you divide by zero a computer cries");
	}

	@Instance(value = MineCalc.MODID)
	public static MineCalc instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		proxy.load(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		proxy.serverStarting(event);
	}
}
