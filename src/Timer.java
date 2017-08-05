import java.util.Observable;

/**
 * Implements a clock as a Runnable. Extends Observable to ease communication
 */
public class Timer extends Observable implements Runnable {
	/**
	 * A thread for our timer.
	 */
	private Thread thread = new Thread(this);

	/**
	 * The Timer singleton instance.
	 */
	private static Timer instance;

	/**
	 * The number of milliseconds between ticks.
	 */
	private static final int tickInterval = 50;


	/**
	 * Events fired from Timer.
	 */
	public enum Events {
		/**
		 * An tick interval occurs.
		 */
		CLOCK_TICKED_EVENT
	};


	/**
	 * Private constructor for Singleton. Starts Timer.
	 */
	private Timer() {
		thread.start();
	}


	/**
	 * @return {@link Timer#instance}
	 */
	public static Timer instance() {
		if (instance == null) {
			instance = new Timer();
		}
		return instance;
	}


	/**
	 * A loop that sends a CLOCK_TICKED_EVENT every tickInterval milliseconds.
	 */
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(tickInterval);

				setChanged();
				notifyObservers(Events.CLOCK_TICKED_EVENT);
			}
		} catch (InterruptedException ie) {
			System.err.println("The timer was interrupted.");
		}
	}
}
