import java.util.Observable;

/**
 * A state the cooler can be in.
 *
 * @author  Eric Fulwiler, Daniel Johnson, Joseph T. Parsons, Cory Stadther
 * @version 2.0
 * @since   2017-August-05
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
     * @return How quickly the cooler warms in this state.
     */
    public abstract int getCoolerLossRate();

    /**
     * True if the light is on in the current state, false otherwise.
     */
    public abstract boolean isLightOn();

    /**
     * True if the compressor is cooling in the current state, false otherwise.
     */
    public abstract boolean isCooling();


    /**
     * Initializes the state.
     */
    public abstract void run();

    /**
     * Process events.
     *
     * @param arg Event to process.
     */
    public abstract void handle(Object arg);
}
