package blackop778.mineCalc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import blackop778.mineCalc.common.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = MineCalc.MODID, name = MineCalc.MODNAME, version = MineCalc.MODVER, acceptableRemoteVersions = "*", guiFactory = "blackop778.mineCalc.client.config.GuiFactoryMineCalc")
public class MineCalc {
    public static final String MODID = "minecraftcalculator778";
    public static final String MODNAME = "MineCalc";
    public static final String MODVER = "4.0.0";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "blackop778.mineCalc.client.ClientProxy", serverSide = "blackop778.mineCalc.server.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
	proxy.preInit(event);
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
	proxy.load(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
	proxy.postInit(event);
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
	proxy.serverStart(event);
    }
}
