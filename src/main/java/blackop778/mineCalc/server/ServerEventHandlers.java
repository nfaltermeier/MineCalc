package blackop778.mineCalc.server;

import blackop778.mineCalc.common.CommonEventHandlers;
import blackop778.mineCalc.common.MineCalcCompoundProvider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class ServerEventHandlers extends CommonEventHandlers {
    @SubscribeEvent
    public void onPlayerLeave(PlayerLoggedOutEvent event) {
	event.player.getCapability(MineCalcCompoundProvider.MCC_CAP, null).setHasMineCalc(false);
    }
}
