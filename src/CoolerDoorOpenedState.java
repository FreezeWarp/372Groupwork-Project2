/**
 * A state for a Cooler when its door is open.
 */
public class CoolerDoorOpenedState extends CoolerState {
    /**
     * Create a new CoolerDoorOpenedState.
     *
     * @param coolerContext The coolerContext that created this instance.
     */
    public CoolerDoorOpenedState(CoolerContext coolerContext) {
        this.coolerContext = coolerContext;
    }

    /**
     * {@link CoolerState#lightOn}
     */
    protected boolean lightOn = false;

    /**
     * {@link CoolerState#getCoolerLossRate()}
     */
    public int getCoolerLossRate() {
        return coolerContext.getCoolerLossRateOpen();
    }

    /**
     * Handle events.
     * @param arg Event to process.
     */
    public void handle(Object arg) {
        if (arg.equals(CoolerContext.Events.DOOR_CLOSED_EVENT) || arg.equals(CoolerContext.Events.DOOR_TOGGLE_EVENT)) {
            coolerContext.changeCurrentState(coolerContext.getDoorClosedIdleState());
        }
    }

    /**
     * Initialize the state
     */
    public void run() {
        setChanged();
        notifyObservers(Events.COMPRESSOR_DEACTIVATED);

        setChanged();
        notifyObservers(Events.DOOR_OPENED);
    }
}