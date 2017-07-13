/**
 * Super class for all freezer states.
 * 
 *
 */
public abstract class FreezerState {
	protected static FreezerContext context;
	protected static Display display;

	/**
	 * Initialzies the context and display
	 */
	protected FreezerState() {
		context = FreezerContext.instance();
		display = context.getDisplay();
	}

	/**
	 * Initializes the state
	 */
	public abstract void run();

	/**
	 * Handles an event
	 * 
	 * @param event
	 *            event to be processed
	 */
	public abstract void handle(Object event);
}
