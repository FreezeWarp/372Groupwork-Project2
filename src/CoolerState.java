/**
 * Created by joseph on 23/07/17.
 */
public abstract class CoolerState {
    protected CoolerContext coolerContext;
    protected CoolingStrategy coolingStrategy;

    protected boolean lightOn;


    /**
     * Initializes the state
     */
    public abstract void run();


    public abstract int getCoolerLossRate();

    public abstract boolean isLightOn();


    /**
     * Handle events
     */
    public void handle(Object event) {
    }

    public void processDoorOpen() {
        coolerContext.changeCurrentState(coolerContext.getDoorOpenedState());
    }
    public void processDoorClose() {
        coolerContext.changeCurrentState(coolerContext.getDoorClosedState());
    }
}
