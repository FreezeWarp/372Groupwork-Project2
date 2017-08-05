import javafx.beans.property.*;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by joseph on 23/07/17.
 */
public class CoolerContext implements Observer {
    /**
     * Events fired from CoolerContext.
     */
    public enum Events {
        /**
         * The door closes.
         */
        DOOR_CLOSED_EVENT,

        /**
         * The door opens.
         */
        DOOR_OPENED_EVENT,

        /**
         * The door's state flips from its current.
         */
        DOOR_TOGGLE_EVENT
    };

    /**
     * The room this cooler is located in.
     */
    private RoomContext roomContext;

    /**
     * The current state of the cooler.
     */
    private ObjectProperty<CoolerState> currentState = new SimpleObjectProperty<CoolerState>();

    /**
     * The cooler's door-closed, idle state.
     */
    private CoolerDoorClosedIdleState doorClosedIdleState;

    /**
     * The cooler's door-closed, active state.
     */
    private CoolerDoorClosedActiveState doorClosedActiveState;

    /**
     * The cooler's door-opened state.
     */
    private CoolerDoorOpenedState doorOpenedState;

    /**
     * The cooler's current temperature.
     */
    private final DoubleProperty coolerTemp = new SimpleDoubleProperty();

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

    /**
     * The strategy used for cooling.
     */
    private ObservableCoolingStrategy coolingStrategy;


    /**
     * Create a CoolerContext
     *
     * @param roomContext The RoomContext the CoolerContext is within.
     */
    public CoolerContext(RoomContext roomContext) {
        this.roomContext = roomContext;

        this.doorClosedIdleState = new CoolerDoorClosedIdleState(this);
        this.doorClosedActiveState = new CoolerDoorClosedActiveState(this);
        this.doorOpenedState = new CoolerDoorOpenedState(this);

        this.coolingStrategy = new DefaultCoolingStrategy(this);

        this.changeCurrentState(doorClosedIdleState);

        coolingStrategy.addObserver(this); // Get informed of compressor events.
        Timer.instance().addObserver(this); // Get informed of ticks.

        // (we get informed of state events in changeState())
    }


    /**
     * Create a CoolerContext
     *
     * @param roomContext The RoomContext the CoolerContext is within.
     * @param initialTemp The temperature the cooler should start at.
     * @param targetTemp The temperature the cooler should target.
     * @param compressorStartDiff The distance from the target temp before the cooler activates.
     * @param coolRate The rate of cooling.
     * @param lossRateOpen The rate of loss when the cooler's door is open.
     * @param lossRateClosed The rate of loss when the cooler's door is closed.
     */
    public CoolerContext(RoomContext roomContext, int initialTemp, int targetTemp, int compressorStartDiff, int coolRate, int lossRateOpen, int lossRateClosed) {
        this(roomContext);

        this.setCoolerTemp(initialTemp);
        this.setDesiredCoolerTemp(targetTemp);
        this.setCompressorStartDiff(compressorStartDiff);
        this.setCoolerCoolRate(coolRate);
        this.setCoolerLossRateOpen(lossRateOpen);
        this.setCoolerLossRateClose(lossRateClosed);
    }


    /**
     * Called whenever an observable sends an event. We simply forward our events to {@link CoolerContext#processEvent(Object)}.
     *
     * @param observable The object whose event has been sent.
     * @param arg The event itself.
     */
    @Override
    public void update(Observable observable, Object arg) {
        processEvent(arg);
    }

    /**
     * Invoke an event on CoolerContext.
     * In-fact, we delegate all events we receive to the *current* cooling strategy and the *current* state.
     * (The alternative is to have them directly observing, but this would require additional set-up whenever a state or cooling strategy changes, and would arguably increasing coupling to an undesirable degree.)
     *
     * @param event The event from the GUI
     */
    public void processEvent(Object event) {
        getCurrentState().handle(event); // Notify the current state of all events we receive, except events it sent.
        getCoolingStrategy().handle(event); // Notify the current cooling strategy of all events we receive, except events it sent.
    }

    /**
     * Change our current state to a new one.
     * This is typically invoked by the states themselves, after they receive an event (from the GUI or from the cooling strategy) to open or close a door, or start or stop the compressor.
     *
     * @param nextState The new state
     */
    public void changeCurrentState(CoolerState nextState) {
        if (currentState.get() != null) { // We may be null at start of execution.
            currentState.get().deleteObserver(this); // Stop being informed of events from the now-deactivated state. (It shouldn't be sending any anyway, but just in case.)
        }

        nextState.addObserver(this); // Get informed of all events from the new state.
        currentState.set(nextState); // Change the state to the new one.
        currentState.get().run(); // Run the new state.
    }


    /**
     * @return {@link CoolerContext#coolerTemp} as an integer
     */
    public double getCoolerTemp() {
        return coolerTemp.get();
    }

    /**
     * Sets integer value of {@link CoolerContext#coolerTemp}
     */
    public void setCoolerTemp(double temp) {
        coolerTemp.set(temp);
    }

    /**
     * @return {@link CoolerContext#coolerTemp} as a property
     */
    public DoubleProperty coolerTempProperty() {
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

    public ObservableCoolingStrategy getCoolingStrategy() {
        return coolingStrategy;
    }


    /**
     * @return {@link CoolerContext#currentState} as a CoolerState
     */
    public CoolerState getCurrentState() {
        return currentState.get();
    }

    /**
     * @return {@link CoolerContext#currentState} as an ObjectProperty<CoolerState>
     */
    public ObjectProperty<CoolerState> currentStateProperty() {
        return currentState;
    }

    /**
     * @return {@link CoolerContext#doorClosedIdleState}
     */
    public CoolerDoorClosedIdleState getDoorClosedIdleState() {
        return doorClosedIdleState;
    }

    /**
     * @return {@link CoolerContext#doorClosedActiveState}
     */
    public CoolerDoorClosedActiveState getDoorClosedActiveState() {
        return doorClosedActiveState;
    }

    /**
     * @return {@link CoolerContext#doorOpenedState}
     */
    public CoolerDoorOpenedState getDoorOpenedState() {
        return doorOpenedState;
    }
}
