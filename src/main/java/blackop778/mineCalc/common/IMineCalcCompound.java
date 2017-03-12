package blackop778.mineCalc.common;

import net.minecraftforge.common.IExtendedEntityProperties;

public interface IMineCalcCompound extends IExtendedEntityProperties {

    public boolean getHasMineCalc();

    public void setHasMineCalc(boolean HasMineCalc);

    public double getLastNumber();

    public void setLastNumber(double num);
}
