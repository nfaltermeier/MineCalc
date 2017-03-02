package Blackop778.MineCalc.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerEventHandlers {
    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent.Entity event) {
	if (!(event.getEntity() instanceof EntityPlayer))
	    return;
	event.addCapability(LastNumberProvider.LN_RL, new LastNumberProvider());
	event.addCapability(HasMineCalcProvider.HMC_RL, new HasMineCalcProvider());
    }
}
