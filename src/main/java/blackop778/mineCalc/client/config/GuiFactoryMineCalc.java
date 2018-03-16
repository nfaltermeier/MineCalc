package blackop778.mineCalc.client.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Set;

public class GuiFactoryMineCalc implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
        // Called when Minecraft finishes loading

    }

    @Nonnull
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        // Needs a return that extends GuiConfig, which will be the main Gui
        // Class
        return GuiConfigMineCalc.class;
    }

    @Nullable
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        // TODO Auto-generated method stub
        return null;
    }

}
