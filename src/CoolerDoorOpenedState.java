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

    public void handle(Object arg) {
        if (arg.equals(CoolerContext.Events.DOOR_CLOSED_EVENT) || arg.equals(CoolerContext.Events.DOOR_TOGGLE_EVENT)) {
            coolerContext.changeCurrentState(coolerContext.getDoorClosedIdleState());
        }
    }

    public boolean isLightOn() {
        return true;
    }

    /**
     * Initialize the state
     */
    @Override
    public void run() {
        setChanged();
        notifyObservers(Events.COMPRESSOR_DEACTIVATED);

        setChanged();
        notifyObservers(Events.DOOR_OPENED);
    }
}