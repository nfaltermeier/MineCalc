package blackop778.mineCalc.common;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import javax.annotation.Nonnull;

public class MineCalcCompound implements IMineCalcCompound {

    private boolean hasMineCalc;
    private double lastNumber;
    private static final String lastNumberKey = "LastNumber";

    public MineCalcCompound() {
        lastNumber = Double.NaN;
        hasMineCalc = false;
    }

    @Override
    public boolean getHasMineCalc() {
        return hasMineCalc;
    }

    @Override
    public void setHasMineCalc(boolean HasMineCalc) {
        this.hasMineCalc = HasMineCalc;
    }

    @Override
    public double getLastNumber() {
        return lastNumber;
    }

    @Override
    public void setLastNumber(double num) {
        this.lastNumber = num;
    }

    public static class MineCalcCompoundStorage implements IStorage<IMineCalcCompound> {
        @Override
        public NBTBase writeNBT(Capability<IMineCalcCompound> capability, @Nonnull IMineCalcCompound instance, EnumFacing side) {
            NBTTagCompound comp = new NBTTagCompound();
            comp.setDouble(lastNumberKey, instance.getLastNumber());
            return comp;
        }

        @Override
        public void readNBT(Capability<IMineCalcCompound> capability, @Nonnull IMineCalcCompound instance, EnumFacing side,
                            NBTBase nbt) {
            if (nbt instanceof NBTTagCompound) {
                NBTTagCompound comp = (NBTTagCompound) nbt;
                instance.setLastNumber(comp.getDouble(lastNumberKey));
            } else {
                instance.setLastNumber(Double.NaN);
            }
            instance.setHasMineCalc(false);
        }
    }
}
