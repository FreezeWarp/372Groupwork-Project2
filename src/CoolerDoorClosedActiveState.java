/**
 * A state for a Cooler when its door is closed and its compressor is active+.
 */
public class CoolerDoorClosedActiveState extends CoolerDoorClosedState {
    /**
     * Create a new CoolerDoorClosedActiveState.
     *
     * @param coolerContext The coolerContext that created this instance.
     */
    public CoolerDoorClosedActiveState(CoolerContext coolerContext) {
        this.coolerContext = coolerContext;
    }

    /**
     * Handle events.
     * @param arg Event to process.
     */
    public void handle(Object arg) {
        super.handle(arg);

        if (arg.equals(ObservableCoolingStrategy.Events.COOLING_DEACTIVATED)) {
            coolerContext.changeCurrentState(coolerContext.getDoorClosedIdleState());
        }
    }

    /**
     * Initialize the state
     */
    public void run() {
        super.run();

        setChanged();
        notifyObservers(Events.COMPRESSOR_ACTIVATED);
    }
}
