package blackop778.mineCalc.client.config;

import blackop778.mineCalc.MineCalc;
import blackop778.mineCalc.common.MCConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiConfigMineCalc extends GuiConfig {
    // http://jabelarminecraft.blogspot.com/p/minecraft-modding-configuration-guis.html
    public GuiConfigMineCalc(GuiScreen parent) {
	super(parent,
		new ConfigElement(MCConfig.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
		MineCalc.MODID, false, false, "Calculate All the Things",
		MCConfig.getConfig().getConfigFile().getAbsolutePath());
    }

    @Override
    public void initGui() {
	// You can add buttons and initialize fields here
	super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	// You can do things like create animations, draw additional elements,
	// etc. here
	super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
	// You can process any additional buttons you may have added here
	super.actionPerformed(button);
    }
}