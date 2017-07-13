public class FreezerDoorOpenedState extends FreezerState {
	private static FreezerDoorOpenedState instance;
	private static int coolTime=0;
	private static int lossTime=0;
	static {
		instance = new FreezerDoorOpenedState();
	}

	/**
	 * For the singleton pattern
	 * 
	 * @return the object
	 */
	public static FreezerDoorOpenedState instance() {
		return instance;
	}

	/**
	 * Handle door closed event
	 */
	@Override
	public void handle(Object event) {
		if (event.equals(FreezerContext.Events.DOOR_CLOSED_EVENT)) {
			processDoorClose();
		} else if (event.equals(Timer.Events.CLOCK_TICKED_EVENT)) {
			processTimerTick();
		}
	}

	/**
	 * Process door closed event
	 */
	public void processDoorClose() {
		context.changeCurrentState(FreezerDoorClosedState.instance());
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
		if (lossTime>=context.getFreezerLossRateOpen()) {
			FreezerContext.setFreezerTemp(context.getFreezerTemp() + 1);
			lossTime=0;
		}
		display.displayFreezerTemp(context.getFreezerTemp());
	} 

	/**
	 * Initialize the state
	 */
	@Override
	public void run() {
		display.turnFreezerLightOn();
	}
}