package blackop778.mineCalc.client;

import blackop778.mineCalc.MineCalc;
import blackop778.mineCalc.common.CommonEventHandlers;
import blackop778.mineCalc.common.MCConfig;
import blackop778.mineCalc.common.net.HasModMessage;
import blackop778.mineCalc.common.net.NetHub;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ClientEventHandlers extends CommonEventHandlers {

    @SubscribeEvent
    public void onPlayerJoinedServer(EntityJoinWorldEvent event) {
	if (event.entity instanceof EntityPlayer) {
	    if (event.entity.equals(Minecraft.getMinecraft().thePlayer)) {
		NetHub.NETWORKWRAPPER.sendToServer(new HasModMessage());
	    }
	}
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event) {
	if (event.modID.equalsIgnoreCase(MineCalc.MODID)) {
	    MCConfig.syncConfig();
	}
    }
}