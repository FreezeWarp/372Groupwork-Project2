/**
 * Created by joseph on 23/07/17.
 */
public abstract class CoolerState {
    protected CoolerContext coolerContext;

    protected int coolTime = 0;
    protected int lossTime = 0;


    /**
     * Initializes the state
     */
    public abstract void run();

    /**
     * Handles an event
     *
     * @param event
     *            event to be processed
     */
    public abstract void handle(Object event);


    public void processTimerTick() {
        lossTime++;
        coolTime++;

        boolean coolingActive = false;

        // Cooling Status
        if (coolerContext.getCoolerTemp() > coolerContext.getDesiredCoolerTemp() + 1) {
            coolingActive = true; // TODO
            coolerContext.getDisplay().turnCoolingOn(coolerContext); // TODO?
        }
        else {
            coolingActive = false;
            coolerContext.getDisplay().turnCoolingOff(coolerContext); // TODO?
        }

        // Cooling
        if (coolTime >= coolerContext.getCoolerCoolRate()) {
            if (coolingActive) {
                coolerContext.setCoolerTemp(coolerContext.getCoolerTemp() - 1);
                coolTime = 0;
            }
        }

        // Loss
        if (lossTime >= coolerContext.getCoolerLossRateClose()) {
            coolerContext.setCoolerTemp(coolerContext.getCoolerTemp() + 1);
            lossTime=0;
        }

        coolerContext.getDisplay().displayTemp(coolerContext);
    }
}
