package stream;
import java.util.*;
import java.util.stream
.Collectors;
import java.util.Map
.Entry;
public class CustStream {
	public static void main(String[] args) {
		execute();
		//дополнительное задание
		ShiftSched.test();
		}
	static void execute() {
		List<TestResult> run = List.of(new TestResult("LoginTest", Status.PASSED,
				120), new TestResult("CreateTaskTest", Status.PASSED,
						940),
				new TestResult("UpdateStatusTest", Status.FAILED,
						2100), new TestResult("CommentTest", Status.PASSED,
								380),
				new TestResult("DeleteTaskTest", Status.FAILED,
						760), new TestResult("StatsTest", Status.SKIPPED,
								0),
				new TestResult("LogoutTest", Status.PASSED,
						90));
		printSummary(run);
	}
	static void printSummary(List<TestResult> run) {
		Map<Status, List<String>> tests = run.stream()
				.collect(Collectors.groupingBy(TestResult::status, Collectors.mapping(TestResult::name, Collectors
						.toList())));
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("=== сводка прогона ===\n");
		List<Entry<Status, List<String>>> entries = tests.entrySet().stream()
				.sorted(new Comparator<Entry<Status, List<String>>>() {
					@Override
					public int compare(Entry<Status, List<String>> a, Entry<Status, List<String>> b) { return b.getValue().size() - a
							.getValue().size(); }
					})
				.toList();
		entries.stream()
		.peek(entry -> {
			String rank = "PASSED";
			Status status = entry.getKey();
			if (status == Status.FAILED) rank = "FAILED";
			else if (status == Status.SKIPPED) rank = "SKIPPED";
			stringBuilder.append(rank);
			stringBuilder.append(" (");
			List<String> value = entry.getValue();
			int size = value.size();
			stringBuilder.append(size);
			stringBuilder.append(")");
			stringBuilder.append(": ");
			value.stream()
			.peek(testName -> {
				stringBuilder.append(testName);
				if (value.indexOf(testName) < size - 1) stringBuilder.append(", ");
				})
			.toList();
			if (entries.indexOf(entry) < entries.size() - 1) stringBuilder
			.append("\n");
			})
		.toList();
		stringBuilder.append("\n");
		stringBuilder.append("топ-3 долгих теста: ");
		List<TestResult> sortRun = run.stream()
				.sorted()
				.limit(3)
				.toList();
		sortRun.stream()
		.peek(test -> {
			stringBuilder.append(test.name());
			if (sortRun.indexOf(test) < sortRun.size() - 1) stringBuilder
			.append(", ");
		})
		.toList();
		stringBuilder.append("\n");
		stringBuilder.append("среднее время прошедших тестов: ");
		double avTime = run.stream()
				.filter(testResult -> testResult.status() == Status.PASSED)
				.mapToDouble(testResult -> testResult.durationMs())
				.summaryStatistics()
				.getAverage();
		stringBuilder.append(avTime);
		stringBuilder.append(" ms");
		System.out.println(stringBuilder
				.toString());
	}
}