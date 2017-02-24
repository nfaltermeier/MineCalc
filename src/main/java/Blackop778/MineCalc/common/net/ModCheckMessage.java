package Blackop778.MineCalc.common.net;

import Blackop778.MineCalc.MineCalc;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ModCheckMessage implements IMessage {

    private String name;

    public ModCheckMessage(String name) {
	this.name = name;
    }

    public static class ModCheckMessageHandler implements IMessageHandler<ModCheckMessage, HasModMessage> {

	@Override
	public HasModMessage onMessage(ModCheckMessage message, MessageContext ctx) {
	    MineCalc.Logger.error("ModCheckMessage recieved");
	    return new HasModMessage("");
	}

    }

    @Override
    public void fromBytes(ByteBuf buf) {
	name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
	ByteBufUtils.writeUTF8String(buf, name);
    }

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
