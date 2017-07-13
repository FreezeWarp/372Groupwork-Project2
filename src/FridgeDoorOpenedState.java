public class FridgeDoorOpenedState extends FridgeState {
	private static FridgeDoorOpenedState instance;
	private int lossTime=0;
	private int coolTime=0;
	static {
		instance = new FridgeDoorOpenedState();
	}

	/**
	 * For the singleton pattern
	 * 
	 * @return the object
	 */
	public static FridgeDoorOpenedState instance() {
		return instance;
	}

	/**
	 * Handle door closed event
	 */
	@Override
	public void handle(Object event) {
		if (event.equals(FridgeContext.Events.DOOR_CLOSED_EVENT)) {
			processDoorClose();
		} else if (event.equals(Timer.Events.CLOCK_TICKED_EVENT)) {
			processTimerTick();
		}
	}

	/**
	 * Process door closed event
	 */
	public void processDoorClose() {
		context.changeCurrentState(FridgeDoorClosedState.instance());
	}
	
	/**
	 * Process clock ticks Generates the timer runs out event
	 */
	public void processTimerTick() {
		lossTime++;
		coolTime++;
		boolean coolingActive=false;
		
		//Cooling Status
		if (context.getFridgeTemp()>context.getDesiredFridgeTemp()+2) { //TODO not use hard coded value
			coolingActive=true;
			display.turnFridgeCoolingOn();
		} else {
			coolingActive=false;
			display.turnFridgeCoolingOff();
		}
		
		//Cooling
		if (coolTime>=context.getFridgeCoolRate()) {
			if (coolingActive) {
				FridgeContext.setFridgeTemp(context.getFridgeTemp() - 1);
				coolTime=0;
			}
		}
		
		//Loss
		if (lossTime>=context.getFridgeLossRateOpen()) {
			FridgeContext.setFridgeTemp(context.getFridgeTemp() + 1);
			lossTime=0;
		}
		display.displayFridgeTemp(context.getFridgeTemp());
	} 

	/**
	 * Initialize the state
	 */
	@Override
	public void run() {
		display.turnFridgeLightOn();
	}
}