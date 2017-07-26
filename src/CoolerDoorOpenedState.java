/**
 * Created by joseph on 23/07/17.
 */
public class CoolerDoorOpenedState extends CoolerState {
    public CoolerDoorOpenedState(CoolerContext coolerContext) {
        this.coolerContext = coolerContext;
    }

    public int getCoolerLossRate() {
        return coolerContext.getCoolerLossRateOpen();
    }

    public void handle(Object event) {
        super.handle(event);

        if (event.equals(CoolerContext.Events.DOOR_CLOSED_EVENT) || event.equals(CoolerContext.Events.DOOR_TOGGLE_EVENT)) {
            processDoorClose();
        }
    }

    /**
     * Initialize the state
     */
    @Override
    public void run() {
        coolerContext.getDisplay().coolerOpened(coolerContext);
    }
}
