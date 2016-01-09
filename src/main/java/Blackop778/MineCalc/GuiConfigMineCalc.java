package Blackop778.MineCalc;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiConfigMineCalc extends GuiConfig
{
	// http://jabelarminecraft.blogspot.com/p/minecraft-modding-configuration-guis.html
	public GuiConfigMineCalc(GuiScreen parent)
	{
		super(parent, new ConfigElement(MCConfig.getConfig().getCategory("Options")).getChildElements(), MineCalc.MODID,
				"Calculate All the Things", false, false, MCConfig.getConfigFile().getAbsolutePath());
	}
}
