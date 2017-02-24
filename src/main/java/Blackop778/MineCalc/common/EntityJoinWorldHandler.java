package Blackop778.MineCalc.common;

import Blackop778.MineCalc.MineCalc;
import Blackop778.MineCalc.common.net.ModCheckMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinWorldHandler {
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
	if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer
		&& !event.getEntity().worldObj.isRemote) {
	    MineCalc.Logger.error("Player joined");
	    MineCalc.NETWORKWRAPPER.sendTo(new ModCheckMessage(event.getEntity().getName()),
		    (EntityPlayerMP) event.getEntity());
	}
    }
}
