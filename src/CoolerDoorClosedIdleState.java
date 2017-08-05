/**
 * A state for a Cooler when its door is closed and its compressor is idle.
 */
public class CoolerDoorClosedIdleState extends CoolerDoorClosedState {
    /**
     * Create a new CoolerDoorClosedIdleState.
     *
     * @param coolerContext The coolerContext that created this instance.
     */
    public CoolerDoorClosedIdleState(CoolerContext coolerContext) {
        this.coolerContext = coolerContext;
    }


    /**
     * @return {@link CoolerState#isCooling()}
     */
    public boolean isCooling() {
        return false;
    }


    /**
     * Handle events.
     * @param arg Event to process.
     */
    public void handle(Object arg) {
        super.handle(arg);

        if (arg.equals(ObservableCoolingStrategy.Events.COOLING_ACTIVATED)) {
            coolerContext.changeCurrentState(coolerContext.getDoorClosedActiveState());
        }
    }

    /**
     * Initialize the state
     */
    public void run() {
        super.run();

        setChanged();
        notifyObservers(Events.COMPRESSOR_DEACTIVATED);
    }
}
