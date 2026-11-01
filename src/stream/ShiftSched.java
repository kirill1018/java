package stream;
import java.util.List;
import java.util.ArrayList;
//дополнительное задание
public class ShiftSched {
	public static void test() {
		var denSheet = new WorkSheet("денис", List.of(DayOfWeek.TUESDAY, DayOfWeek
				.THURSDAY,
				DayOfWeek.SUNDAY));
		var benSheet = new WorkSheet("бен", List.of(DayOfWeek.MONDAY, DayOfWeek
				.WEDNESDAY,
				DayOfWeek.SATURDAY));
		var lisSheet = new WorkSheet("лиза", List.of(DayOfWeek.THURSDAY, DayOfWeek
				.WEDNESDAY,
				DayOfWeek.FRIDAY));
		var sheets = List.of(denSheet, benSheet,
				lisSheet);
		List<DayOfWeek> weekDays = new ArrayList<DayOfWeek>();
		int size = sheets.size();
		for (int i = 0; i < size; i++) {
			int iterator = i;
			sheets.get(i).workDays()
			.stream()
			.peek(dayOfWeek -> { for (int x = iterator + 1; x < size; x++) if (sheets.get(x).workDays()
					.stream()
					.anyMatch(day -> day == dayOfWeek)) {
				weekDays.remove(dayOfWeek);
				weekDays.add(dayOfWeek);
				}
			})
			.toList();
		}
		weekDays.forEach(System.out::println);
	}
}