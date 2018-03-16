package blackop778.mineCalc.server;

import blackop778.mineCalc.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import javax.annotation.Nonnull;

public class ServerProxy extends CommonProxy {
    @Override
    public void preInit(@Nonnull FMLPreInitializationEvent event) {
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
    public void serverStart(@Nonnull FMLServerStartingEvent event) {
        super.serverStart(event);
    }
}
