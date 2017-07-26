/**
 * Created by joseph on 23/07/17.
 */
public class CoolerDoorClosedState extends CoolerState {
    public CoolerDoorClosedState(CoolerContext coolerContext) {
        this.coolerContext = coolerContext;
    }

    /**
     * Handle cook request and door open events
     *
     */
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
        coolerContext.changeCurrentState(coolerContext.getDoorOpenedState());
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
    public void run() {
        coolerContext.getDisplay().turnLightOff(coolerContext);
        //context.setTimeRemaining(0);
    }
}