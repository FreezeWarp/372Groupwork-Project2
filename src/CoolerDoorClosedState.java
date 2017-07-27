/**
 * Created by joseph on 23/07/17.
 */
public class CoolerDoorClosedState extends CoolerState {
    protected boolean lightOn = false;

    public CoolerDoorClosedState(CoolerContext coolerContext) {
        this.coolerContext = coolerContext;
    }

    public int getCoolerLossRate() {
        return coolerContext.getCoolerLossRateClose();
    }

    public void handle(Object event) {
        super.handle(event);

        if (event.equals(CoolerContext.Events.DOOR_OPENED_EVENT) || event.equals(CoolerContext.Events.DOOR_TOGGLE_EVENT)) {
            processDoorOpen();
        }
    }

    public boolean isLightOn() {
        return false;
    }

    /**
     * initialize the state
     *
     */
    public void run() {
    }
}
