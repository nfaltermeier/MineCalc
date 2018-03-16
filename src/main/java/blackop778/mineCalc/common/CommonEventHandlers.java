package blackop778.mineCalc.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nonnull;

public class CommonEventHandlers {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public void attachCapability(@Nonnull AttachCapabilitiesEvent.Entity event) {
        if (!(event.getEntity() instanceof EntityPlayer))
            return;
        event.addCapability(MineCalcCompoundProvider.MCC_RL, new MineCalcCompoundProvider());
    }

    @SubscribeEvent
    public void onPlayerClone(@Nonnull PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            //noinspection ConstantConditions
            if(MineCalcCompoundProvider.MCC_CAP == null)
                return;

            EntityPlayer old = event.getOriginal();
            EntityPlayer new_ = event.getEntityPlayer();

            IMineCalcCompound oldMCC = old.getCapability(MineCalcCompoundProvider.MCC_CAP, null);
            IMineCalcCompound newMCC = new_.getCapability(MineCalcCompoundProvider.MCC_CAP, null);

            if(oldMCC == null || newMCC == null)
                return;

            newMCC.setHasMineCalc(oldMCC.getHasMineCalc());
            newMCC.setLastNumber(oldMCC.getLastNumber());
        }
    }
}
