package Blackop778.MineCalc.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HasMineCalc {
    private boolean HasMineCalc;

    public boolean getHasMineCalc() {
	return HasMineCalc;
    }

    public void setHasMineCalc(boolean HasMineCalc) {
	this.HasMineCalc = HasMineCalc;
    }

    public static class HasMineCalcStorage implements IStorage<HasMineCalc> {

	@Override
	public NBTBase writeNBT(Capability<HasMineCalc> capability, HasMineCalc instance, EnumFacing side) {
	    return new NBTTagShort((short) (instance.getHasMineCalc() ? 1 : 0));
	}

	@Override
	public void readNBT(Capability<HasMineCalc> capability, HasMineCalc instance, EnumFacing side, NBTBase nbt) {
	    if (nbt instanceof NBTPrimitive) {
		NBTPrimitive prim = (NBTPrimitive) nbt;
		instance.setHasMineCalc(prim.getInt() == 1);
	    } else
		instance.setHasMineCalc(false);
	}

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
