package Blackop778.MineCalc;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=MineCalc.MODID, name=MineCalc.MODNAME, version=MineCalc.MODVER)
public class MineCalc {
	public static final String MODID = "minecraftcalculator778";
	public static final String MODNAME = "MineCalc";
	public static final String MODVER = "0.0.1";
	
	@Instance(value = MineCalc.MODID)
	public static MineCalc instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	@EventHandler
    public void load(FMLInitializationEvent event)
    {

    }
        
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
