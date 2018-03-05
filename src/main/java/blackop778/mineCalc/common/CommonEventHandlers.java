package blackop778.mineCalc.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandlers {
    @SuppressWarnings("deprecation")
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
            IMineCalcCompound oldMCC = old.getCapability(MineCalcCompoundProvider.MCC_CAP, null);
            IMineCalcCompound newMCC = new_.getCapability(MineCalcCompoundProvider.MCC_CAP, null);
            newMCC.setHasMineCalc(oldMCC.getHasMineCalc());
            newMCC.setLastNumber(oldMCC.getLastNumber());
        }
    }
}
