/**
 * Created by joseph on 23/07/17.
 */
public abstract class CoolerState {
    protected CoolerContext coolerContext;

    /**
     * How long the cooler has been cooling (how long the compressor has been active) since the last change in the cooler's temperature.
     * This is reset once the cooler temperature drops, which happens once the this number reaches the cooling rate.
     */
    protected int coolTime = 0;

    /**
     * How long the cooler has been running (on or off) since the last temperature change.
     * This is reset once the cooler temperature increases, which happens once this number reaches the loss rate.
     */
    protected int lossTime = 0;

    /**
     *
     */
    private boolean currentlyCooling;


    /**
     * Initializes the state
     */
    public abstract void run();


    public abstract int getCoolerLossRate();


    /**
     * Handle events
     */
    public void handle(Object event) {
        if (event.equals(Timer.Events.CLOCK_TICKED_EVENT)) {
            processTimerTick();
        }
    }


    public void processTimerTick() {
        System.out.println(getClass());
        lossTime++;


        // Start/Stop Cooling When Needed
        if (coolerContext.getCoolerTemp() >= coolerContext.getDesiredCoolerTemp() + coolerContext.getCompressorStartDiff()) { // Start the cooler once the temperature exceeds our desired temperature plus the compressor start diff.
            startCooling();
        }
        else if (coolerContext.getCoolerTemp() <= coolerContext.getDesiredCoolerTemp() - coolerContext.getCompressorStartDiff()) {
            stopCooling();
        }


        // Process Temperature Change from Cooling
        if (isCooling()) {
            coolTime++;
            while (coolTime >= coolerContext.getCoolerCoolRate()) {
                coolerContext.setCoolerTemp(coolerContext.getCoolerTemp() - 1);
                coolTime -= coolerContext.getCoolerCoolRate();
            }
        }


        // Process Temperature Change from Natural Loss
        while (lossTime >= getCoolerLossRate()) {
            coolerContext.setCoolerTemp(coolerContext.getCoolerTemp() + 1);
            lossTime -= getCoolerLossRate();
        }


        // Display Current Temperature
        coolerContext.getDisplay().displayTemp(coolerContext);
    }



    public void startCooling() {
        currentlyCooling = true;
        coolerContext.getDisplay().turnCoolingOn(coolerContext);
    }

    public void stopCooling() {
        currentlyCooling = false;
        coolerContext.getDisplay().turnCoolingOff(coolerContext);
    }

    public boolean isCooling() {
        return currentlyCooling;
    }



    public void processDoorOpen() {
        coolerContext.changeCurrentState(coolerContext.getDoorOpenedState());
    }
    public void processDoorClose() {
        coolerContext.changeCurrentState(coolerContext.getDoorClosedState());
    }
}
