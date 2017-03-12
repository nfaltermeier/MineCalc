package blackop778.mineCalc.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CommonEventHandlers {
    @SubscribeEvent
    public void entityConstruct(EntityEvent.EntityConstructing e) {
	if (e.entity instanceof EntityPlayer) {
	    if (e.entity.getExtendedProperties(MineCalcCompound.PROP_NAME) == null) {
		e.entity.registerExtendedProperties(MineCalcCompound.PROP_NAME, new MineCalcCompound());
	    }
	}
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
	if (event.wasDeath) {
	    EntityPlayer old = event.original;
	    EntityPlayer new_ = event.entityPlayer;
	    IMineCalcCompound oldMCC = MineCalcCompound.get(old);
	    IMineCalcCompound newMCC = MineCalcCompound.get(new_);
	    newMCC.setHasMineCalc(oldMCC.getHasMineCalc());
	    newMCC.setLastNumber(oldMCC.getLastNumber());
	}
    }
}
