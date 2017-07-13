public class FridgeDoorClosedState extends FridgeState {
	private static FridgeDoorClosedState instance;
	private static int coolTime=0;
	private static int lossTime=0;
	static {
		instance = new FridgeDoorClosedState();
	}

	/**
	 * returns the instance
	 * 
	 * @return this object
	 */
	public static FridgeDoorClosedState instance() {
		return instance;
	}

	/**
	 * Handle cook request and door open events
	 * 
	 */
	@Override
	public void handle(Object event) {
		if (event.equals(FridgeContext.Events.DOOR_OPENED_EVENT)) {
			processDoorOpen();
		} else if (event.equals(Timer.Events.CLOCK_TICKED_EVENT)) {
			processTimerTick();
		}
	}

	/**
	 * handle door open event
	 * 
	 */
	public void processDoorOpen() {
		context.changeCurrentState(FridgeDoorOpenedState.instance());
	}

	public void processTimerTick() {
		lossTime++;
		coolTime++;
		//Cooling
		if (coolTime>=context.getFridgeCoolRate()) {
			if (context.getFridgeTemp()<RoomContext.getRoomTemp()) {
				if (context.getFridgeTemp()>context.getDesiredFridgeTemp()+2) { //TODO not use hard coded value
					FridgeContext.setFridgeTemp(context.getFridgeTemp() - 1);
					coolTime=0;
				}
			}
		}
		//Loss
		if (lossTime>=context.getFridgeLossRateClose()) {
			FridgeContext.setFridgeTemp(context.getFridgeTemp() + 1);
			lossTime=0;
		}
		display.displayFridgeTemp(context.getFridgeTemp());
	} 

	/**
	 * initialize the state
	 * 
	 */
	@Override
	public void run() {
		display.turnFridgeLightOff();
		//context.setTimeRemaining(0);
	}
}