package stream;

public record TestResult(String name, Status status,
		long durationMs) implements Comparable<TestResult> {
	public int compareTo(TestResult testResult) { return Long.compare(testResult.durationMs, this
			.durationMs); }
}