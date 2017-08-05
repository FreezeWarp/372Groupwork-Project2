/**
 * A basic exception that is fired when a dictionary lookup fails.
 */
public class NoKeyException extends RuntimeException {
    public NoKeyException(Object key) {
        super("The key does not exist: " + key);
    }
}
