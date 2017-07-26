/**
 * Created by joseph on 26/07/17.
 */
public class NoKeyException extends RuntimeException {
    public NoKeyException(Object key) {
        super("The key does not exist: " + key);
    }
}
