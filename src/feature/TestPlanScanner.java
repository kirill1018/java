package feature;
import java.util.*;
import java.lang.reflect
.Method;
import java.lang.annotation
.Annotation;
public class TestPlanScanner {
	public static void main(String[] args) { System.out.println(buildPlan(Checklist
			.class)); }
	static String buildPlan(Class<?> clazz) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("план прогона для ");
		stringBuilder.append(clazz.getSimpleName());
		stringBuilder.append(":\n");
		List<TestStep> testSteps = new ArrayList<TestStep>();
		for (Method method : clazz.getDeclaredMethods()) {
			Class<? extends Annotation> test = TestId.class;
			if (method.isAnnotationPresent(test)) {
				TestId testId = (TestId)method.getAnnotation(test);
				TestStep testStep = new TestStep(testId.value(), method.getName(),
						testId.order());
				testSteps.add(testStep);
				}
			}
		if (testSteps.size() == 0) {
			stringBuilder.append("кейсы отсутствуют");
			return stringBuilder.toString();
		}
		Collections.sort(testSteps);
		for (TestStep testStep : testSteps) {
			stringBuilder.append("     ");
			stringBuilder.append(testStep.order());
			stringBuilder.append(". ");
			stringBuilder.append(testStep.id());
			stringBuilder.append(" → ");
			stringBuilder.append(testStep.methodName());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
		}
	}