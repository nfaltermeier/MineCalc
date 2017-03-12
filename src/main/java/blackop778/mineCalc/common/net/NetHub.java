package blackop778.mineCalc.common.net;

import blackop778.mineCalc.MineCalc;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetHub {

    public static final SimpleNetworkWrapper NETWORKWRAPPER = NetworkRegistry.INSTANCE
	    .newSimpleChannel(MineCalc.MODNAME);
    public static int packetDiscriminator = 0;
}
