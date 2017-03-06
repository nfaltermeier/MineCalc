package blackop778.mineCalc.client;

import blackop778.mineCalc.MineCalc;
import blackop778.mineCalc.common.CommonEventHandlers;
import blackop778.mineCalc.common.MCConfig;
import blackop778.mineCalc.common.net.HasModMessage;
import blackop778.mineCalc.common.net.NetHub;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class ClientEventHandlers extends CommonEventHandlers {

    public static class SubEvent {
	@SubscribeEvent
	public void onPlayerJoinedServer(EntityJoinWorldEvent event) {
	    if (event.getEntity() instanceof EntityPlayer) {
		if (event.getEntity().equals(Minecraft.getMinecraft().thePlayer)) {
		    NetHub.NETWORKWRAPPER.sendToServer(new HasModMessage());
		    MineCalc.LOGGER.info("Joined a server");
		    MinecraftForge.EVENT_BUS.unregister(this);
		}
	    }
	}
    }

    @SubscribeEvent
    public void onPlayerLEftServer(ClientDisconnectionFromServerEvent event) {
	Minecraft.getMinecraft().addScheduledTask(new Runnable() {
	    @Override
	    public void run() {
		MinecraftForge.EVENT_BUS.register(new SubEvent());
	    }
	});
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event) {
	if (event.getModID().equalsIgnoreCase(MineCalc.MODID)) {
	    MCConfig.syncConfig();
	}
    }
}
