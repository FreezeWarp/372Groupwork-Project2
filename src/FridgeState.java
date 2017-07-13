/**
 * Super class for all fridge states.
 * 
 *
 */
public abstract class FridgeState {
	protected static FridgeContext context;
	protected static Display display;

	/**
	 * Initialzies the context and display
	 */
	protected FridgeState() {
		context = FridgeContext.instance();
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
