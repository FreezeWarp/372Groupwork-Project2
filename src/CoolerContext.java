import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by joseph on 23/07/17.
 */
public class CoolerContext implements Observer {
    public enum Events {
        DOOR_CLOSED_EVENT, DOOR_OPENED_EVENT,
        DOOR_TOGGLE_EVENT
    };

    /**
     * The display to send cooler updates to.
     */
    private Display coolerDisplay;

    /**
     * The room this cooler is located in.
     */
    private RoomContext roomContext;

    /**
     * The current state of the cooler.
     */
    private CoolerState currentState;

    /**
     * The cooler's door-closed state.
     */
    private CoolerDoorClosedState doorClosedState;

    /**
     * The cooler's door-opened state.
     */
    private CoolerDoorOpenedState doorOpenedState;

    /**
     * The cooler's current temperature.
     */
    private final IntegerProperty coolerTemp = new SimpleIntegerProperty();

    /**
     * The difference from the desired temperature, {@link CoolerContext#desiredCoolerTemp} at which the cooler will start/stop.
     */
    private int compressorStartDiff;

    /**
     * The temperature the cooler should try to reach.
     */
    private int desiredCoolerTemp;

    /**
     * The number of ticks it takes for the cooler to lose one temperature degree when it is in the door opened state, {@link CoolerDoorOpenedState}
     */
    private int coolerLossRateOpen;

    /**
     * The number of ticks it takes for the cooler to lose one temperature degree when it is in the door closed state, {@link CoolerDoorClosedState}
     */
    private int coolerLossRateClose;

    /**
     * The number of ticks it takes for the cooler to gain one degree when it is cooling.
     */
    private int coolerCoolRate;


    public CoolerContext(Display display, RoomContext roomContext) {
        this.coolerDisplay = display;
        this.roomContext = roomContext;

        this.doorClosedState = new CoolerDoorClosedState(this);
        this.doorOpenedState = new CoolerDoorOpenedState(this);

        this.changeCurrentState(doorClosedState);
        Timer.instance().addObserver(this);
    }

    public CoolerContext(Display display, RoomContext roomContext, int initialTemp, int targetTemp, int compressorStartDiff, int coolRate, int lossRateOpen, int lossRateClosed) {
        this(display, roomContext);

        this.setCoolerTemp(initialTemp);
        this.setDesiredCoolerTemp(targetTemp);
        this.setCompressorStartDiff(compressorStartDiff);
        this.setCoolerCoolRate(coolRate);
        this.setCoolerLossRateOpen(lossRateOpen);
        this.setCoolerLossRateClose(lossRateClosed);
    }


    /**
     * For observer
     *
     * @param observable
     *            will be the clock
     * @param arg
     *            the event that clock has ticked
     */
    @Override
    public void update(Observable observable, Object arg) {
        currentState.handle(arg);
    }

    /**
     * handle one of the several other events such as door close
     *
     * @param arg
     *            the event from the GUI
     */
    public void processEvent(Object arg) {
        currentState.handle(arg);
    }

    /**
     * Called from the states to change the current state
     *
     * @param nextState
     *            the next state
     */
    public void changeCurrentState(CoolerState nextState) {
        currentState = nextState;
        nextState.run();
    }

    /**
     * @return The display reference object with this cooler.
     */
    public Display getDisplay() {
        return coolerDisplay;
    }

    /**
     * @return {@link CoolerContext#coolerTemp} as an integer
     */
    public int getCoolerTemp() {
        return coolerTemp.get();
    }

    /**
     * Sets integer value of {@link CoolerContext#coolerTemp}
     */
    public void setCoolerTemp(int temp) {
        coolerTemp.set(temp);
    }

    /**
     * @return {@link CoolerContext#coolerTemp} as a property
     */
    public IntegerProperty coolerTempProperty() {
        return coolerTemp;
    }


    /**
     * @return {@link CoolerContext#desiredCoolerTemp}
     */
    public int getDesiredCoolerTemp() {
        return desiredCoolerTemp;
    }

    /**
     * Sets {@link CoolerContext#desiredCoolerTemp}
     */
    public void setDesiredCoolerTemp(int temp) {
        desiredCoolerTemp = temp;
    }

    /**
     * @return {@link CoolerContext#coolerLossRateOpen}
     */
    public int getCoolerLossRateOpen() {
        return coolerLossRateOpen;
    }

    /**
     * Sets {@link CoolerContext#coolerLossRateOpen}
     */
    public void setCoolerLossRateOpen(int rate) {
        coolerLossRateOpen = rate;
    }

    /**
     * @return {@link CoolerContext#coolerLossRateClose}
     */
    public int getCoolerLossRateClose() {
        return coolerLossRateClose;
    }

    /**
     * Sets {@link CoolerContext#coolerLossRateClose}
     */
    public void setCoolerLossRateClose(int rate) {
        coolerLossRateClose = rate;
    }

    /**
     * @return {@link CoolerContext#coolerCoolRate}
     */
    public int getCoolerCoolRate() {
        return coolerCoolRate;
    }

    /**
     * Sets {@link CoolerContext#coolerCoolRate}
     */
    public void setCoolerCoolRate(int rate) {
        coolerCoolRate = rate;
    }

    /**
     * @return {@link CoolerContext#compressorStartDiff}
     */
    public int getCompressorStartDiff() { return compressorStartDiff; }

    /**
     * Sets {@link CoolerContext#compressorStartDiff}
     */
    public void setCompressorStartDiff(int compressorStartDiff) { this.compressorStartDiff = compressorStartDiff; }

    /**
     * @return {@link CoolerContext#roomContext}
     */
    public RoomContext getRoomContext() { return roomContext; }

    /**
     * @return {@link CoolerContext#doorOpenedState}
     */
    public CoolerDoorOpenedState getDoorOpenedState() {
        return doorOpenedState;
    }

    /**
     * @return {@link CoolerContext#doorClosedState}
     */
    public CoolerDoorClosedState getDoorClosedState() {
        return doorClosedState;
    }
}
