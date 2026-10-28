package collection;
import java.util.Map;
import java.util.HashMap;
//дополнительное задание
public class RateAn {
	static <T> Map<T, Long> frequency(T[] items) {
		Map<T, Long> result = new HashMap<T, Long>();
		for (T t : items) result.merge(t, (long)1,
				(value1, value2) -> value1 + 1);
		return result;
	}
}