/**
 * A state for a Cooler when its door is open.
 *
 * @author  Eric Fulwiler, Daniel Johnson, Joseph T. Parsons, Cory Stadther
 * @version 2.0
 * @since   2017-August-05
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
     * @return {@link CoolerState#isLightOn()}
     */
    public boolean isLightOn() {
        return true;
    }

    /**
     * @return {@link CoolerState#isCooling()}
     */
    public boolean isCooling() {
        return false;
    }

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