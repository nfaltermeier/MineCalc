package Blackop778.MineCalc.server;

import Blackop778.MineCalc.MineCalc;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class LastNumberProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(HasMineCalc.class)
    public static final Capability<LastNumber> LN_CAP = null;
    public static final ResourceLocation LN_RL = new ResourceLocation(MineCalc.MODID, "HMC");

    private LastNumber instance = null;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
	return capability == LN_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
	return capability == LN_CAP ? LN_CAP.<T>cast(getInstance()) : null;
    }

    @Override
    public NBTBase serializeNBT() {
	return LN_CAP.getStorage().writeNBT(LN_CAP, getInstance(), null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
	LN_CAP.getStorage().readNBT(LN_CAP, getInstance(), null, nbt);
    }

    private LastNumber getInstance() {
	if (instance == null)
	    instance = LN_CAP.getDefaultInstance();
	return instance;
    }
}
