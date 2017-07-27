import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by joseph on 26/07/17.
 */
public class DefaultCoolingStrategy implements CoolingStrategy, Observer {
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
    private BooleanProperty isCooling = new SimpleBooleanProperty();


    /**
     * The cooler this cooling strategy is applied to.
     */
    CoolerContext coolerContext;


    public DefaultCoolingStrategy(CoolerContext coolerContext, Timer timer) {
        this.coolerContext = coolerContext;

        timer.addObserver(this);
    }

    public void update(Observable observable, Object arg) {
        if (arg.equals(Timer.Events.CLOCK_TICKED_EVENT)) {
            processTimerTick();
        }
    }

    public void processTimerTick() {
        // Increment the loss time from normal running, unless the fridge is at the room temperature.
        // (Notably, this is still imperfect: the fridge would approach room temperature asymptotically, not linearly.)
        if (coolerContext.getCoolerTemp() < coolerContext.getRoomContext().getRoomTemp()) {
            lossTime++;
        }


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
        while (lossTime >= coolerContext.getCurrentState().getCoolerLossRate()) {
            coolerContext.setCoolerTemp(coolerContext.getCoolerTemp() + 1);
            lossTime -= coolerContext.getCurrentState().getCoolerLossRate();
        }
    }



    public void startCooling() {
        isCooling.set(true);
    }

    public void stopCooling() {
        isCooling.set(false);
    }

    public boolean isCooling() {
        return isCooling.get();
    }

    public BooleanProperty isCoolingProperty() {
        return isCooling;
    }
}
