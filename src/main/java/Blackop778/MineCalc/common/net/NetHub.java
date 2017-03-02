package Blackop778.MineCalc.common.net;

import Blackop778.MineCalc.MineCalc;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetHub {

    public static final SimpleNetworkWrapper NETWORKWRAPPER = NetworkRegistry.INSTANCE
	    .newSimpleChannel(MineCalc.MODNAME);
    public static int packetDiscriminator = 0;

    // In case we do need it
    public void onPlayerJoinedServer(EntityJoinWorldEvent event) {
	if (event.getEntity() instanceof EntityPlayer) {
	    if (event.getEntity().equals(Minecraft.getMinecraft().thePlayer)) {
		NETWORKWRAPPER.sendToServer(new HasModMessage());
		MineCalc.LOGGER.info("Joined a server");
		// MinecraftForge.EVENT_BUS.unregister(this);
	    }
	}
    }

    @SubscribeEvent
    public void onPlayerJoinedServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
	Minecraft.getMinecraft().addScheduledTask(new Runnable() {
	    @Override
	    public void run() {
		NETWORKWRAPPER.sendToServer(new HasModMessage());
		MineCalc.LOGGER.info("Joined a server");
	    }
	});
    }
}
