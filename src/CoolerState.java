import java.util.Observable;

/**
 * A state the cooler can be in.
 */
public abstract class CoolerState extends Observable {
    /**
     * Events that can be fired from CoolerState,
     */
    public enum Events {
        /**
         * When the door is opened. This event is reflexive: the state changes to an open door state because of some other, external event, and then DOOR_OPENED is fired in acknowledgement.
         */
        DOOR_OPENED,

        /**
         * When the door is closed. This event is reflexive: the state changes to a closed door state because of some other, external event, and then DOOR_CLOSED is fired in acknowledgement.
         */
        DOOR_CLOSED,

        /**
         * When the compressor is activate. This event is reflexive: the state changes to a compressor active state because of some other, external event (typically from CoolingStrategy), and then COMPRESSOR_ACTIVATED is fired in acknowledgement.
         */
        COMPRESSOR_ACTIVATED,

        /**
         * When the compressor is deactivated. This event is reflexive: the state changes to a compressor idle state because of some other, external event (typically from CoolingStrategy), and then COMPRESSOR_DEACTIVATED is fired in acknowledgement.
         */
        COMPRESSOR_DEACTIVATED
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
    public boolean isLightOn() {
        return lightOn;
    }

    /**
     * Process events.
     *
     * @param arg Event to process.
     */
    public abstract void handle(Object arg);
}
