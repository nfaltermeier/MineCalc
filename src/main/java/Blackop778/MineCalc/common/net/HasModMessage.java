package Blackop778.MineCalc.common.net;

import Blackop778.MineCalc.MineCalc;
import Blackop778.MineCalc.common.Calculate;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HasModMessage implements IMessage {

    private String name;

    public HasModMessage(String name) {
	this.name = name;
    }

    public HasModMessage() {

    }

    public static class HasModMessageHandler implements IMessageHandler<HasModMessage, IMessage> {

	@Override
	public IMessage onMessage(HasModMessage message, MessageContext ctx) {
	    MineCalc.Logger.info("Got Has Mod Message");
	    MineCalc.Logger.info(message.name);
	    Calculate.hasMod.add(message.name);
	    return null;
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

}
