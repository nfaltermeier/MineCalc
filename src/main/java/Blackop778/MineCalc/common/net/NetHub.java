package Blackop778.MineCalc.common.net;

import Blackop778.MineCalc.MineCalc;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetHub {

    public static final SimpleNetworkWrapper NETWORKWRAPPER = NetworkRegistry.INSTANCE
	    .newSimpleChannel(MineCalc.MODVER);
    public static int packetDiscriminator = 0;

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
	MineCalc.Logger.info("Player joined");
	NETWORKWRAPPER.sendTo(new ModCheckMessage(event.player.getName()), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerLoggedOutEvent event) {

    }
}
