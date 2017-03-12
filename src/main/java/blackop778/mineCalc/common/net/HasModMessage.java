package blackop778.mineCalc.common.net;

import blackop778.mineCalc.client.ClientProxy;
import blackop778.mineCalc.common.MineCalcCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class HasModMessage implements IMessage {

    public HasModMessage() {

    }

    public static class HasModMessageHandler implements IMessageHandler<HasModMessage, IMessage> {

	@Override
	public IMessage onMessage(HasModMessage message, MessageContext ctx) {
	    if (!ClientProxy.isClientSide()) {
		MineCalcCompound.get(ctx.getServerHandler().playerEntity).setHasMineCalc(true);
	    }
	    return null;
	}

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

}
