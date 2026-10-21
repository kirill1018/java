package core;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map
.Entry;
enum Status { PASSED, FAILED,
	SKIPPED }
record TestResult(String name, Status status,
		long durationMs) {}
public class TestLogParser {
	public static void main(String[] args) {
		List<String> fileText = readFile();
		System.out.println(parse(fileText));
		//дополнительное задание
		SimpleStringBuilder simpleStringBuilder = new SimpleStringBuilder();
		simpleStringBuilder.append("hello");
		System.out.println(simpleStringBuilder
				.toString());
		simpleStringBuilder.append(", ");
		System.out.println(simpleStringBuilder
				.toString());
		simpleStringBuilder.append("world");
		System.out.println(simpleStringBuilder
				.toString());
		simpleStringBuilder.undo();
		System.out.println(simpleStringBuilder
				.toString());
		simpleStringBuilder.append("aqa");
		System.out.println(simpleStringBuilder
				.toString());
	}
	static List<String> readFile() {
		char[] buf = new char[256];
		try (FileReader fileReader = new FileReader("run.log")) {
			int c;
			while ((c = fileReader.read(buf)) > 0) if (c < 256) buf = Arrays.copyOf(buf, c);
		}
		catch (IOException ioException) {}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(buf);
		return Arrays.asList(stringBuilder.toString()
				.split("\n"));
	}
	static void appName(StringBuilder builder, String name) {
		builder.append(name);
		builder.append(" - ");
	}
	static void appTime(StringBuilder builder, TestResult result) {
		builder.append(" (");
		builder.append(result.durationMs());
		builder.append(" ms)");
	}
	static String parse(List<String> lines) {
		List<TestResult> rightTests = new ArrayList<TestResult>();
		Map<String, String> errors = new HashMap<String, String>();
		for (String line : lines) {
			String[] lineParts = line.split("[|]");
			boolean isRight = lineParts.length == 3 ? true : false;
			if (isRight) {
				if (lineParts[1].equals("PASSED") || lineParts[1].equals("FAILED") ||
						lineParts[1].equals("SKIPPED")) {
					try {
						if (Long.parseLong(lineParts[2].trim()) >= 0) {
							Status status = Status.FAILED;
							if (lineParts[1].equals("PASSED")) status = Status.PASSED;
							else if (lineParts[1].equals("SKIPPED")) status = Status.SKIPPED;
							TestResult testResult = new TestResult(lineParts[0], status,
									Long.parseLong(lineParts[2].trim()));
							rightTests.add(testResult);
							}
						else errors.put(line.trim(), "отрицательная длительность");
						}
					catch (NumberFormatException numberFormatException) { errors.put(line.trim(), "время является выражением, отличающимся от числа"); }
					}
				else errors.put(line.trim(), "опечатка в статусе");
				}
			else errors.put(line.trim(), "отсутствует разделитель");
			}
		List<Integer> indToRem = new ArrayList<Integer>();
		for (int i = 0; i < rightTests.size(); i++) for (int x = 0; x < i; x++) if (rightTests.get(i)
				.name().equals(rightTests.get(x)
						.name())) indToRem.add(x);//повторы отсеиваются сравнением метода name() рекорд-класса testResult с помощью equals, так как сравниваемые значения принадлежат типу string
		for (int index : indToRem) rightTests.remove(index);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("результаты:\n");
		for (TestResult testResult : rightTests) {
			stringBuilder.append("     ");
			appName(stringBuilder, testResult.name());
			String status = "FAILED";
			if (testResult.status() == Status.PASSED) status = "PASSED";
			else if (testResult.status() == Status.SKIPPED) status = "SKIPPED";
			stringBuilder.append(status);
			appTime(stringBuilder, testResult);
			stringBuilder.append("\n");
		}
		stringBuilder.append("битые строки: ");
		for (Entry<String, String> entry : errors.entrySet().stream()
				.toList().reversed()) {
			appName(stringBuilder, entry.getKey());
			stringBuilder.append(entry.getValue());
			stringBuilder.append("\n");
		}
		stringBuilder.append("первый упавший: ");
		if (firstFailed(rightTests).isEmpty()) stringBuilder.append("падения отсутствуют");
		else {
			appName(stringBuilder, firstFailed(rightTests).get().name());
			appTime(stringBuilder, firstFailed(rightTests).get());
		}
		return stringBuilder.toString();
	}
	static Optional<TestResult> firstFailed(List<TestResult> results) { return results.stream().filter(result -> result
			.status() == Status.FAILED).findFirst(); }
}
//дополнительное задание
class SimpleStringBuilder {
	Stack<String> stack = new Stack<String>();
	StringBuilder stringBuilder = new StringBuilder();
	public void append(String s) {
		this.stack.push(this
				.stringBuilder.toString());
		this.stringBuilder.append(s);
		}
	public void undo() {
		this.stringBuilder = new StringBuilder();
		this.stringBuilder.append(this
				.stack.peek());
		this.stack.pop();
	}
	public String toString() { return this.stringBuilder.toString(); }
}