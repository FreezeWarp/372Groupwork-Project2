import java.util.Observable;
import java.util.Observer;

/**
 * The context is an obserer for the clock and stores the context info for
 * states
 *
 */
public class FridgeContext implements Observer {
	public static enum Events {
		DOOR_CLOSED_EVENT, DOOR_OPENED_EVENT
	};

	private static Display fridgeDisplay;
	private static int fridgeTemp;
	private static int desiredFridgeTemp;
	private static int fridgeLossRateOpen;
	private static int fridgeLossRateClose;
	private static int fridgeCoolRate;
	private FridgeState currentState;
	private static FridgeContext instance;
	static {
		instance = new FridgeContext();
		fridgeDisplay = Display.instance();
	}

	/**
	 * Make it a singleton
	 */
	private FridgeContext() {
	}

	/**
	 * Return the instance
	 * 
	 * @return the object
	 */
	public static FridgeContext instance() {
		if (instance == null) {
			instance = new FridgeContext();
		}
		return instance;
	}

	/**
	 * lets door closed state be the starting state adds the object as an
	 * observable for clock
	 */
	public void initialize() {
		instance.changeCurrentState(FridgeDoorClosedState.instance());
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
	public void changeCurrentState(FridgeState nextState) {
		currentState = nextState;
		nextState.run();
	}

	/**
	 * Gets the display
	 * 
	 * @return the display
	 */
	public Display getDisplay() {
		return fridgeDisplay;
	}

	/**
	 * Gets the temperature 
	 * 
	 * @return fridgeTemp
	 */
	public int getFridgeTemp() {
		return fridgeTemp;
	}
	
	/**
	 * Sets the temperature 
	 * 
	 * @param temperature
	 *            temp of the fridge
	 */
	public static void setFridgeTemp(int temp) {
		fridgeTemp = temp;
	}
	
	public int getDesiredFridgeTemp() {
		return desiredFridgeTemp;
	}
	
	public static void setDesiredFridgeTemp(int temp) {
		desiredFridgeTemp = temp;
	}
	
	public int getFridgeLossRateOpen() {
		return fridgeLossRateOpen;
	}
	public static void setFridgeLossRateOpen(int rate) {
		fridgeLossRateOpen = rate;
	}
	
	public int getFridgeLossRateClose() {
		return fridgeLossRateClose;
	}
	public static void setFridgeLossRateClose(int rate) {
		fridgeLossRateClose = rate;
	}
	
	public int getFridgeCoolRate() {
		return fridgeCoolRate;
	}
	public static void setFridgeCoolRate(int rate) {
		fridgeCoolRate = rate;
	}
}