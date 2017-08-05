/**
 * A state for a Cooler when its door is closed.
 */
public abstract class CoolerDoorClosedState extends CoolerState {
    /**
     * {@link CoolerState#lightOn}
     */
    protected boolean lightOn;

    /**
     * {@link CoolerState#getCoolerLossRate()}
     */
    public int getCoolerLossRate() {
        return coolerContext.getCoolerLossRateClose();
    }

    /**
     * Handle events.
     * @param arg Event to process.
     */
    public void handle(Object arg) {
        if (arg.equals(CoolerContext.Events.DOOR_OPENED_EVENT) || arg.equals(CoolerContext.Events.DOOR_TOGGLE_EVENT)) {
            coolerContext.changeCurrentState(coolerContext.getDoorOpenedState());
        }
    }

    /**
     * Initialize the state
     */
    public void run() {
        setChanged();
        notifyObservers(CoolerState.Events.DOOR_CLOSED);
    }
}
