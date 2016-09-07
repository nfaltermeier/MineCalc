package Blackop778.MineCalc.client.config;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiFactoryMineCalc implements IModGuiFactory
{

	@Override
	public void initialize(Minecraft minecraftInstance)
	{
		// Called when Minecraft finishes loading

	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		// Needs a return that extends GuiConfig, which will be the main Gui
		// Class
		return GuiConfigMineCalc.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
