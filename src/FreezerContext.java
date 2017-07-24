import java.util.Observable;
import java.util.Observer;

/**
 * The context is an obserer for the clock and stores the context info for
 * states
 *
 */
public class FreezerContext implements Observer {
	public static enum Events {
		DOOR_CLOSED_EVENT, DOOR_OPENED_EVENT
	};

	private static Display freezerDisplay;
	private static int freezerTemp;
	private static int desiredFreezerTemp;
	private static int freezerLossRateOpen;
	private static int freezerLossRateClose;
	private static int freezerCoolRate;
	private FreezerState currentState;
	private static FreezerContext instance;
	static {
		instance = new FreezerContext();
		freezerDisplay = Display.instance();
	}

	/**
	 * Make it a singleton
	 */
	private FreezerContext() {
	}

	/**
	 * Return the instance
	 * 
	 * @return the object
	 */
	public static FreezerContext instance() {
		if (instance == null) {
			instance = new FreezerContext();
		}
		return instance;
	}

	/**
	 * Lets door closed state be the starting state adds the object as an
	 * observable for clock
	 */
	public void initialize() {
		instance.changeCurrentState(FreezerDoorClosedState.instance());
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
	 * Handle one of the several other events such as door close
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
	public void changeCurrentState(FreezerState nextState) {
		currentState = nextState;
		nextState.run();
	}

	/**
	 * Gets the display
	 * 
	 * @return the display
	 */
	public Display getDisplay() {
		return freezerDisplay;
	}

	/**
	 * Gets the temperature
	 * 
	 *  @return freezerTemp
	 */
	public int getFreezerTemp() {
		return freezerTemp;
	}
	
	/**
	 * Sets the temperature 
	 * 
	 * @param temperature
	 *            temp of the freezer
	 */
	public static void setFreezerTemp(int temp) {
		freezerTemp = temp;
	}
	
	public int getDesiredFreezerTemp() {
		return desiredFreezerTemp;
	}

	public static void setDesiredFreezerTemp(int temp) {
		desiredFreezerTemp = temp;
	}
	
	public int getFreezerLossRateOpen() {
		return freezerLossRateOpen;
	}
	public static void setFreezerLossRateOpen(int rate) {
		freezerLossRateOpen = rate;
	}
	
	public int getFreezerLossRateClose() {
		return freezerLossRateClose;
	}
	public static void setFreezerLossRateClose(int rate) {
		freezerLossRateClose = rate;
	}
	
	public int getFreezerCoolRate() {
		return freezerCoolRate;
	}
	public static void setFreezerCoolRate(int rate) {
		freezerCoolRate = rate;
	}
}