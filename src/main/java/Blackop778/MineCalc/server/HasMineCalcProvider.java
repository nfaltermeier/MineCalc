package Blackop778.MineCalc.server;

import Blackop778.MineCalc.MineCalc;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class HasMineCalcProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(HasMineCalc.class)
    public static final Capability<HasMineCalc> HMC_CAP = null;
    public static final ResourceLocation HMC_RL = new ResourceLocation(MineCalc.MODID, "HMC");

    private HasMineCalc instance = null;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
	return capability == HMC_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
	return capability == HMC_CAP ? HMC_CAP.<T>cast(getInstance()) : null;
    }

    @Override
    public NBTBase serializeNBT() {
	return HMC_CAP.getStorage().writeNBT(HMC_CAP, getInstance(), null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
	HMC_CAP.getStorage().readNBT(HMC_CAP, getInstance(), null, nbt);
    }

    private HasMineCalc getInstance() {
	if (instance == null)
	    instance = HMC_CAP.getDefaultInstance();
	return instance;
    }
}
