package Blackop778.MineCalc.client.updateChecker;

import Blackop778.MineCalc.MineCalc;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler
{
	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof EntityPlayerSP)
		{
			String toSend = UpdateChecker.checkForUpdate();
			if(toSend != null)
			{
				MineCalc.Logger.info("Mod is outdated. Informing client...");
				EntityPlayer player = (EntityPlayer) event.getEntity();
				player.addChatMessage(new TextComponentString("Minecalc has an update available. " + toSend));
			}
			else
			{
				MineCalc.Logger.info("Mod is up to date.");
			}

			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
}
