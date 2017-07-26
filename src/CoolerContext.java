import java.util.Observable;
import java.util.Observer;

/**
 * Created by joseph on 23/07/17.
 */
public class CoolerContext implements Observer {
    public enum Events {
        DOOR_CLOSED_EVENT, DOOR_OPENED_EVENT
    };

    private Display coolerDisplay;
    private RoomContext roomContext;

    private CoolerState currentState;
    private CoolerDoorClosedState doorClosedState;
    private CoolerDoorOpenedState doorOpenedState;

    private int coolerTemp;

    private int compressorStartDiff;
    private int desiredCoolerTemp;

    private int coolerLossRateOpen;
    private int coolerLossRateClose;
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
     * Gets the display
     *
     * @return the display
     */
    public Display getDisplay() {
        return coolerDisplay;
    }

    /**
     * Gets the temperature
     *
     *  @return coolerTemp
     */
    public int getCoolerTemp() {
        return coolerTemp;
    }

    /**
     * Sets the temperature 
     *
     * @param temp
     *            temp of the cooler
     */
    public void setCoolerTemp(int temp) {
        coolerTemp = temp;
    }

    public int getDesiredCoolerTemp() {
        return desiredCoolerTemp;
    }

    public void setDesiredCoolerTemp(int temp) {
        desiredCoolerTemp = temp;
    }

    public int getCoolerLossRateOpen() {
        return coolerLossRateOpen;
    }
    public void setCoolerLossRateOpen(int rate) {
        coolerLossRateOpen = rate;
    }

    public int getCoolerLossRateClose() {
        return coolerLossRateClose;
    }
    public void setCoolerLossRateClose(int rate) {
        coolerLossRateClose = rate;
    }

    public int getCoolerCoolRate() {
        return coolerCoolRate;
    }
    public void setCoolerCoolRate(int rate) {
        coolerCoolRate = rate;
    }

    public int getCompressorStartDiff() { return compressorStartDiff; }
    public void setCompressorStartDiff(int compressorStartDiff) { this.compressorStartDiff = compressorStartDiff; }

    public CoolerDoorOpenedState getDoorOpenedState() {
        return doorOpenedState;
    }

    public CoolerDoorClosedState getDoorClosedState() {
        return doorClosedState;
    }
}
