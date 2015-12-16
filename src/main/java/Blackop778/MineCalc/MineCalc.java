package Blackop778.MineCalc;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;

@Mod(modid=MineCalc.MODID, name=MineCalc.MODNAME, version=MineCalc.MODVER)
public class MineCalc {
	public static final String MODID = "minecraftcalculator778";
	public static final String MODNAME = "MineCalc";
	public static final String MODVER = "0.0.2.0"; //According to https://mcforge.readthedocs.org/en/latest/conventions/versioning/
	
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
    
    //Called when a server is started, both solo and multiplayer
    @EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
    	MinecraftServer server = MinecraftServer.getServer();
    	ICommandManager command = server.getCommandManager();
    	ServerCommandManager manager = (ServerCommandManager) command;
    	manager.registerCommand(new Calculate());
    }
}
