/**
 * Created by joseph on 23/07/17.
 */
public class CoolerDoorOpenState extends CoolerState {
    private static CoolerDoorOpenedState instance;
    private static int coolTime=0;
    private static int lossTime=0;
    static {
        instance = new CoolerDoorOpenedState();
    }

    /**
     * For the singleton pattern
     *
     * @return the object
     */
    public static CoolerDoorOpenedState instance() {
        return instance;
    }

    /**
     * Handle door closed event
     */
    @Override
    public void handle(Object event) {
        if (event.equals(CoolerContext.Events.DOOR_CLOSED_EVENT)) {
            processDoorClose();
        } else if (event.equals(Timer.Events.CLOCK_TICKED_EVENT)) {
            processTimerTick();
        }
    }

    /**
     * Process door closed event
     */
    public void processDoorClose() {
        context.changeCurrentState(CoolerDoorClosedState.instance());
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
        if (lossTime>=context.getCoolerLossRateOpen()) {
            CoolerContext.setCoolerTemp(context.getCoolerTemp() + 1);
            lossTime=0;
        }
        display.displayCoolerTemp(context.getCoolerTemp());
    }

    /**
     * Initialize the state
     */
    @Override
    public void run() {
        display.turnCoolerLightOn();
    }
}
