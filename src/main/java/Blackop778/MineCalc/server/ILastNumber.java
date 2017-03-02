package Blackop778.MineCalc.server;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public interface ILastNumber {
    public abstract double getLastNumber();

    public abstract void setLastNumber(double num);

    public static class LastNumberStorage implements IStorage<ILastNumber> {

	@Override
	public NBTBase writeNBT(Capability<ILastNumber> capability, ILastNumber instance, EnumFacing side) {
	    return new NBTTagDouble(instance.getLastNumber());
	}

	@Override
	public void readNBT(Capability<ILastNumber> capability, ILastNumber instance, EnumFacing side, NBTBase nbt) {
	    if (nbt instanceof NBTPrimitive) {
		instance.setLastNumber(((NBTPrimitive) nbt).getDouble());
	    } else
		instance.setLastNumber(0);
	}

    }
}