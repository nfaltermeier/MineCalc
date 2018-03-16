package blackop778.mineCalc;

import blackop778.mineCalc.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Nonnull;

@Mod(modid = MineCalc.MODID, name = MineCalc.MODNAME, version = MineCalc.MODVER, acceptableRemoteVersions = "*", guiFactory = "blackop778.mineCalc.client.config.GuiFactoryMineCalc", updateJSON = MineCalc.UPDATEJSONURL)
public class MineCalc {
    public static final String MODID = "minecraftcalculator778";
    public static final String MODNAME = "MineCalc";
    public static final String MODVER = "4.0.0";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final String UPDATEJSONURL = "https://raw.githubusercontent.com/Blackop778/ModUpdateJsons/master/MineCalc.json";

    @SidedProxy(clientSide = "blackop778.mineCalc.client.ClientProxy", serverSide = "blackop778.mineCalc.server.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(@Nonnull FMLPreInitializationEvent event) {
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
    public void serverStart(@Nonnull FMLServerStartingEvent event) {
        proxy.serverStart(event);
    }
}
