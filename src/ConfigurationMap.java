import java.util.HashMap;
import java.util.Map;

/**
 * Created by joseph on 26/07/17.
 */
public class ConfigurationMap<K, V> {
    Map<K, V> map = new HashMap();

    public ConfigurationMap(K[] defaultKeys, V[] defaultValues) {
        if (defaultKeys.length != defaultValues.length) {
            throw new RuntimeException("Configuration map requires that default keys and default values have the same number of entries.");
        }
        else {
            for (int i = 0; i < defaultKeys.length; i++) {
                map.put(defaultKeys[i], defaultValues[i]);
            }
        }
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            throw new NoKeyException(key);
        }
        else {
            return map.get(key);
        }
    }

    public void put(K key, V value) {
        if (!map.containsKey(key)) {
            throw new NoKeyException(key);
        }
        else {
            map.put(key, value);
        }
    }
}
