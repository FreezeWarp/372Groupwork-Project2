/**
 * Created by Joseph on 27/07/2017.
 */
public class CoolerDoorClosedActiveState extends CoolerDoorClosedState {
    public CoolerDoorClosedActiveState(CoolerContext coolerContext) {
        this.coolerContext = coolerContext;
    }

    public void handle(Object arg) {
        super.handle(arg);

        if (arg.equals(ObservableCoolingStrategy.Events.COOLING_DEACTIVATED)) {
            coolerContext.changeCurrentState(coolerContext.getDoorClosedIdleState());
        }
    }

    public void run() {
        super.run();

        setChanged();
        notifyObservers(Events.COMPRESSOR_ACTIVATED);
    }
}
