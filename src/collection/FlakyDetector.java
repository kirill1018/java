package collection;
import java.util.*;
public class FlakyDetector {
	public static void main(String[] args) {
		System.out.println(execute());
		//дополнительное задание
		String[] words = { "up", "down",
				"up", "up",
				"left" };
		Integer[] numbers = { 3, 1,
				3, 3,
				7, 1 };
		System.out.println(RateAn.frequency(words));
		System.out.println(RateAn.frequency(numbers));
	}
	static String execute() {
		List<String> runThrough1 = List.of("LoginTest|PASSED", "CreateTaskTest|FAILED",
				"CommentTest|FAILED", "LogoutTest|PASSED"), runThrough2 = List.of("LoginTest|PASSED", "CreateTaskTest|PASSED",
						"CommentTest|FAILED", "StatsTest|PASSED");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("flaky-кандидаты: ");
		for (String test : flaky(runThrough1, runThrough2)) {
			stringBuilder.append("[");
			stringBuilder.append(test.split("[|]")[0]);
			stringBuilder.append("] ");
		}
		stringBuilder.append("\n");
		stringBuilder.append("прогон 2, сводка: ");
		stringBuilder.append(statusSummary(runThrough2));
		return stringBuilder.toString();
	}
	static Set<String> findStat(List<String> run, String status) {
		Set<String> result = new HashSet<String>();
		for (String test : run) if (test.split("[|]")[1].equals(status)) result
		.add(test);
		return result;
	}
	static Set<String> failed(List<String> run) { return findStat(run, "FAILED"); }
	static Set<String> passed(List<String> run) { return findStat(run, "PASSED"); }
	static List<String> flaky(List<String> run1, List<String> run2) {
		List<String> listOfFailTests = new ArrayList<String>();
		run1.stream()
		.filter(test -> test.split("[|]")[1].equals("FAILED"))
		.forEach(test -> listOfFailTests.add(test.split("[|]")[0]));
		List<String> result = run2.stream()
				.filter(test -> {
					String[] trial = test.split("[|]");
					if (listOfFailTests.contains(trial[0]) && trial[1].equals("PASSED")) return true;
					else return false;
					})
				.toList();
		return result;
	}
	static Map<String, Long> statusSummary(List<String> run) {
		Map<String, Long> result = new HashMap<String, Long>();
		result.put("PASSED", (long)passed(run).size());
		result.merge("FAILED", (long)failed(run).size(),
				(oldValue, newValue) -> oldValue + newValue);
		return result;
	}
}