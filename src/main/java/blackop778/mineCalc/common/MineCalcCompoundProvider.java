package blackop778.mineCalc.common;

import blackop778.mineCalc.MineCalc;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MineCalcCompoundProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IMineCalcCompound.class)
    public static final Capability<IMineCalcCompound> MCC_CAP = null;
    public static final ResourceLocation MCC_RL = new ResourceLocation(MineCalc.MODID, "HMC");

    private static IMineCalcCompound instance = null;

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == MCC_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == MCC_CAP ? MCC_CAP.<T>cast(getInstance()) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return MCC_CAP.writeNBT(getInstance(), null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        MCC_CAP.readNBT(getInstance(), null, nbt);
    }

    private static IMineCalcCompound getInstance() {
        if (instance == null)
            instance = MCC_CAP.getDefaultInstance();
        return instance;
    }
}
