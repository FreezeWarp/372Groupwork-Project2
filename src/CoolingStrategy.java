/**
 * Any cooling strategy that updates every so often and can receive timer events.
 *
 * @author  Eric Fulwiler, Daniel Johnson, Joseph T. Parsons, Cory Stadther
 * @version 2.0
 * @since   2017-August-05
 */
public interface CoolingStrategy {
    public void handle(Object arg);
}
