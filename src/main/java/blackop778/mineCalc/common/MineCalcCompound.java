package blackop778.mineCalc.common;

import blackop778.mineCalc.MineCalc;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MineCalcCompound implements IMineCalcCompound {

    private boolean hasMineCalc;
    private double lastNumber;
    public static final String PROP_NAME = MineCalc.MODID + "_LastNumber";

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

    @Override
    public void saveNBTData(NBTTagCompound comp) {
	comp.setDouble(PROP_NAME, getLastNumber());
    }

    @Override
    public void loadNBTData(NBTTagCompound comp) {
	setLastNumber(comp.getDouble(PROP_NAME));
    }

    @Override
    public void init(Entity entity, World world) {
	loadNBTData(entity.getEntityData());

    }

    public static IMineCalcCompound get(Entity p) {
	return (MineCalcCompound) p.getExtendedProperties(PROP_NAME);
    }
}
