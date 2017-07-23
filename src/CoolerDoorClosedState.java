/**
 * Created by joseph on 23/07/17.
 */
public class CoolerDoorClosedState extends CoolerState {
    private static CoolerDoorClosedState instance;
    private static int coolTime=0;
    private static int lossTime=0;
    static {
        instance = new CoolerDoorClosedState();
    }

    /**
     * returns the instance
     *
     * @return this object
     */
    public static CoolerDoorClosedState instance() {
        return instance;
    }

    /**
     * Handle cook request and door open events
     *
     */
    @Override
    public void handle(Object event) {
        if (event.equals(CoolerContext.Events.DOOR_OPENED_EVENT)) {
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
        context.changeCurrentState(CoolerDoorOpenedState.instance());
    }

    public void processTimerTick() {
        lossTime++;
        coolTime++;
        boolean coolingActive=false;

        //Cooling Status
        if (context.getCoolerTemp()>context.getDesiredCoolerTemp()+1) { //TODO not use hard coded value
            coolingActive=true;
            display.turnCoolerCoolingOn();
        } else {
            coolingActive=false;
            display.turnCoolerCoolingOff();
        }

        //Cooling
        if (coolTime>=context.getCoolerCoolRate()) {
            if (coolingActive) {
                CoolerContext.setCoolerTemp(context.getCoolerTemp() - 1);
                coolTime=0;
            }
        }

        //Loss
        if (lossTime>=context.getCoolerLossRateClose()) {
            CoolerContext.setCoolerTemp(context.getCoolerTemp() + 1);
            lossTime=0;
        }
        display.displayCoolerTemp(context.getCoolerTemp());
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
        display.turnCoolerLightOff();
        //context.setTimeRemaining(0);
    }
}
