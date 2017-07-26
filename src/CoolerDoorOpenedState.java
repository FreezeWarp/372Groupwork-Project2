/**
 * Created by joseph on 23/07/17.
 */
public class CoolerDoorOpenedState extends CoolerState {
    public CoolerDoorOpenedState(CoolerContext coolerContext) {
        this.coolerContext = coolerContext;
    }

    /**
     * Handle door closed event
     */
    public void handle(Object event) {
        if (event.equals(CoolerContext.Events.DOOR_CLOSED_EVENT)) {
            processDoorClose();
        } else if (event.equals(Timer.Events.CLOCK_TICKED_EVENT)) {

            processTimerTick();
        }
    }

    /**
     * Initialize the state
     */
    @Override
    public void run() {coolerContext.getDisplay().turnLightOn(coolerContext);
    }
}
