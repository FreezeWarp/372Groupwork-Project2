/**
 * Created by joseph on 23/07/17.
 */
public class CoolerDoorClosedState extends CoolerState {
    protected boolean lightOn = false;

    public int getCoolerLossRate() {
        return coolerContext.getCoolerLossRateClose();
    }

    public void handle(Object arg) {
        if (arg.equals(CoolerContext.Events.DOOR_OPENED_EVENT) || arg.equals(CoolerContext.Events.DOOR_TOGGLE_EVENT)) {
            coolerContext.changeCurrentState(coolerContext.getDoorOpenedState());
        }
    }

    public boolean isLightOn() {
        return false;
    }

    public void run() {
        setChanged();
        notifyObservers(CoolerState.Events.DOOR_CLOSED);
    }
}
