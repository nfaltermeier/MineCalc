package Blackop778.MineCalc.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerEventHandlers {
    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent.Entity event) {
	if (!(event.getEntity() instanceof EntityPlayer))
	    return;
	event.addCapability(MineCalcCompoundProvider.MCC_RL, new MineCalcCompoundProvider());
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
	if (event.isWasDeath()) {
	    EntityPlayer old = event.getOriginal();
	    EntityPlayer new_ = event.getEntityPlayer();
	    boolean hasMC = old.getCapability(HasMineCalcProvider.HMC_CAP, null).getHasMineCalc();
	    new_.getCapability(HasMineCalcProvider.HMC_CAP, null).setHasMineCalc(hasMC);
	}
    }
}
