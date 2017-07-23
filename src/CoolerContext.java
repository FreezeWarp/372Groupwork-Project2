import java.util.Observable;
import java.util.Observer;

/**
 * Created by joseph on 23/07/17.
 */
public class CoolerContext implements Observer {
    public static enum Events {
        DOOR_CLOSED_EVENT, DOOR_OPENED_EVENT
    };

    private static Display coolerDisplay;
    private static int coolerTemp;
    private static int desiredCoolerTemp;
    private static int coolerLossRateOpen;
    private static int coolerLossRateClose;
    private static int coolerCoolRate;
    private CoolerState currentState;
    private static CoolerContext instance;
    static {
        instance = new CoolerContext();
        coolerDisplay = Display.instance();
    }

    /**
     * Make it a singleton
     */
    private CoolerContext() {
    }

    /**
     * Return the instance
     *
     * @return the object
     */
    public static CoolerContext instance() {
        if (instance == null) {
            instance = new CoolerContext();
        }
        return instance;
    }

    /**
     * lets door closed state be the starting state adds the object as an
     * observable for clock
     */
    public void initialize() {
        instance.changeCurrentState(CoolerDoorClosedState.instance());
        Timer.instance().addObserver(instance);
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
    public static void setCoolerTemp(int temp) {
        coolerTemp = temp;
    }

    public int getDesiredCoolerTemp() {
        return desiredCoolerTemp;
    }

    public static void setDesiredCoolerTemp(int temp) {
        desiredCoolerTemp = temp;
    }

    public int getCoolerLossRateOpen() {
        return coolerLossRateOpen;
    }
    public static void setCoolerLossRateOpen(int rate) {
        coolerLossRateOpen = rate;
    }

    public int getCoolerLossRateClose() {
        return coolerLossRateClose;
    }
    public static void setCoolerLossRateClose(int rate) {
        coolerLossRateClose = rate;
    }

    public int getCoolerCoolRate() {
        return coolerCoolRate;
    }
    public static void setCoolerCoolRate(int rate) {
        coolerCoolRate = rate;
    }
}
