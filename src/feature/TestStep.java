package feature;

public record TestStep(String id, String methodName,
		int order) implements Comparable<TestStep> {
	public int compareTo(TestStep testStep) { return Integer.compare(this.order, testStep
			.order); }
}