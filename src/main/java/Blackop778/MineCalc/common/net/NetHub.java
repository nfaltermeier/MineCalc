package Blackop778.MineCalc.common.net;

import Blackop778.MineCalc.MineCalc;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetHub {

    public static final SimpleNetworkWrapper NETWORKWRAPPER = NetworkRegistry.INSTANCE
	    .newSimpleChannel(MineCalc.MODNAME);
    public static int packetDiscriminator = 0;
}
