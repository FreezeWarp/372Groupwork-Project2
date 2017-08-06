import java.util.Observable;

/**
 * A CoolingStrategy that is also observable and thus sends events.
 *
 * @author  Eric Fulwiler, Daniel Johnson, Joseph T. Parsons, Cory Stadther
 * @version 2.0
 * @since   2017-August-05
 */
public abstract class ObservableCoolingStrategy extends Observable implements CoolingStrategy {
    /**
     * Events sent from the cooling strategy.
     */
    public enum Events {
        /**
         * When cooling should be turned on.
         */
        COOLING_ACTIVATED,

        /**
         * When cooling should be turned off.
         */
        COOLING_DEACTIVATED
    };
}