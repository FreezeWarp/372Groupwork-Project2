public class FreezerDoorClosedState extends FreezerState {
	private static FreezerDoorClosedState instance;
	private static int coolTime=0;
	private static int lossTime=0;
	static {
		instance = new FreezerDoorClosedState();
	}

	/**
	 * returns the instance
	 * 
	 * @return this object
	 */
	public static FreezerDoorClosedState instance() {
		return instance;
	}

	/**
	 * Handle cook request and door open events
	 * 
	 */
	@Override
	public void handle(Object event) {
		if (event.equals(FreezerContext.Events.DOOR_OPENED_EVENT)) {
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
		context.changeCurrentState(FreezerDoorOpenedState.instance());
	}
	
	public void processTimerTick() {
		lossTime++;
		coolTime++;
		boolean coolingActive=false;
		
		//Cooling Status
		if (context.getFreezerTemp()>context.getDesiredFreezerTemp()+1) { //TODO not use hard coded value
			coolingActive=true;
			display.turnFreezerCoolingOn();
		} else {
			coolingActive=false;
			display.turnFreezerCoolingOff();
		}
		
		//Cooling
		if (coolTime>=context.getFreezerCoolRate()) {
			if (coolingActive) {
				FreezerContext.setFreezerTemp(context.getFreezerTemp() - 1);
				coolTime=0;
			}
		}
		
		//Loss
		if (lossTime>=context.getFreezerLossRateClose()) {
			FreezerContext.setFreezerTemp(context.getFreezerTemp() + 1);
			lossTime=0;
		}
		display.displayFreezerTemp(context.getFreezerTemp());
	} 

	/**
	 * handle cook request
	 * 
	 */
	public void processCookRequest() {
		//context.changeCurrentState(CookingState.instance());
	}

	/**
	 * initialize the state
	 * 
	 */
	@Override
	public void run() {
		display.turnFreezerLightOff();
		//context.setTimeRemaining(0);
	}
}