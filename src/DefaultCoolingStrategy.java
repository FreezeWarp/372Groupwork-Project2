/**
 * A basic (and not necessarily physics-accurate) cooling strategy that adjusts the cooler strategy and sends events to turn the compressor on or off.
 * A much simpler real-world CoolingStrategy would be one that simply observes a thermometer every so often.
 */
public class DefaultCoolingStrategy extends ObservableCoolingStrategy {
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
     * Whether or not we are currently cooling.
     */
    private boolean isCooling = false;

    /**
     * The cooler this cooling strategy is applied to.
     */
    private CoolerContext coolerContext;


    /**
     * Constructor.
     *
     * @param coolerContext {@link DefaultCoolingStrategy#coolerContext}
     */
    public DefaultCoolingStrategy(CoolerContext coolerContext) {
        this.coolerContext = coolerContext;
    }


    /**
     * Handle events sent from {@link CoolerContext} (or elsewhere).
     * Notes:
     ** Ignored events include all from ourself, as well as door close events from CoolerState.
     ** Processed events include Timer tickets and Compressor changes from CoolerState.
     ** Note that we fire "compressor" events, but nothing else; we rely on then receiving the same event back from CoolerState. If CoolerState ignores our event, we'll keep on doing what we were originally doing.
     *
     * @param arg The event that was sent.
     */
    public void handle(Object arg) {
        if (arg.equals(Timer.Events.CLOCK_TICKED_EVENT)) {
            processTimerTick();
        }
        else if (arg.equals(CoolerState.Events.COMPRESSOR_ACTIVATED)) {
            startCooling();
        }
        else if (arg.equals(CoolerState.Events.COMPRESSOR_DEACTIVATED)) {
            stopCooling();
        }
    }


    /**
     * Apply cooling for the period of a single tick.
     * This method is called when we receive a Timer.Events.CLOCK_TICKED_EVENT from {@link DefaultCoolingStrategy#handle(Object)}.
     */
    private void processTimerTick() {
        // Start/Stop Cooling When Needed
        if (coolerContext.getCoolerTemp() >= coolerContext.getDesiredCoolerTemp() + coolerContext.getCompressorStartDiff()) { // Start the cooler once the temperature exceeds our desired temperature plus the compressor start diff.
            setChanged();
            notifyObservers(Events.COOLING_ACTIVATED);
        }
        else if (coolerContext.getCoolerTemp() <= coolerContext.getDesiredCoolerTemp() - coolerContext.getCompressorStartDiff()) {
            setChanged();
            notifyObservers(Events.COOLING_DEACTIVATED);
        }


        // Process Temperature Change from Cooling
        if (isCooling()) {
            coolerContext.setCoolerTemp(coolerContext.getCoolerTemp() - (1 / (double) coolerContext.getCoolerCoolRate()));
        }


        // Process Temperature Change from Natural Loss
        coolerContext.setCoolerTemp(coolerContext.getCoolerTemp() + (
            ((double) coolerContext.getRoomContext().getRoomTemp() - coolerContext.getCoolerTemp()) / Math.pow(coolerContext.getCurrentState().getCoolerLossRate() * 4, 2) // A rough approximation for loss. Basically, the difference between the outside temperature and the inside temperature divided by (four times the loss rate) squared (four times to be better inline with the instructor-provided defaults; 1 and 2 are far too low for this to work). For instance, if it's 120 outside, and we're targeting 40, and our loss rate is 2, we lose 80/64 = 10/8 degrees every tick. If our loss rate is 1, we lose 80/16 = 10/2 degrees every tick.
        ));
    }


    /**
     * Turn on cooling for future iterations of {@link DefaultCoolingStrategy#processTimerTick()}.
     * This method is called when we receive a CoolerState.Events.COOLING_ACTIVATED from {@link DefaultCoolingStrategy#handle(Object)}, which will typically happen after we send a ObservableCoolingStrategy.Events.COOLING_ACTIVATED event.
     */
    private void startCooling() {
        isCooling = true;
    }


    /**
     * Turn off cooling for future iterations of {@link DefaultCoolingStrategy#processTimerTick()}.
     * This method is called when we receive a CoolerState.Events.COOLING_DEACTIVATED from {@link DefaultCoolingStrategy#handle(Object)}, which will typically happen after we send a ObservableCoolingStrategy.Events.COOLING_DEACTIVATED event.
     */
    private void stopCooling() {
        isCooling = false;
    }


    /**
     * @return {@link DefaultCoolingStrategy#isCooling} as a boolean.
     */
    private boolean isCooling() {
        return isCooling;
    }
}
