import java.util.Observable;

/**
 * Created by joseph on 23/07/17.
 */
public abstract class CoolerState extends Observable {
    public enum Events {
        DOOR_OPENED, DOOR_CLOSED,
        COMPRESSOR_ACTIVATED, COMPRESSOR_DEACTIVATED
    };

    /**
     * The {@link CoolerContext} that created this state.
     */
    protected CoolerContext coolerContext;

    /**
     * True if the Cooler light should be turned on in this state, false otherwise.
     */
    protected boolean lightOn;


    /**
     * Initializes the state.
     */
    public abstract void run();

    /**
     * @return How quickly the cooler warms in this state.
     */
    public abstract int getCoolerLossRate();

    /**
     * @return {@link CoolerState#lightOn}
     */
    public abstract boolean isLightOn();

    /**
     * Process events.
     *
     * @param arg Event to process.
     */
    public abstract void handle(Object arg);
}
