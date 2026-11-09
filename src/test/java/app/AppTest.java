package app;
import java.util.stream
.Stream;
import org.junit.jupiter
.api.BeforeAll;
import org.junit.jupiter
.params.ParameterizedTest;
import org.junit.jupiter
.params.provider.MethodSource;
import org.junit.jupiter
.api.Assertions;
public class AppTest {
	static StringBuilder stringBuilder = new StringBuilder();
	static Stream<String> commName() { return Stream.of(App.name()); }
	static Stream<String> emptyString() { return Stream.of(new String()); }
	static Stream<String> missLine() { return Stream.of((String)null); }
	@BeforeAll
	public static void append() { stringBuilder.append("привет, "); }
	@ParameterizedTest
	@MethodSource("commName")
	public void usName(String name) {
		Assertions.assertEquals(stringBuilder.append(name)
				.toString(), App.greet(name));
		stringBuilder = new StringBuilder();
		append();
		}
	@ParameterizedTest
	@MethodSource("emptyString")
	public void checkWithEmptyString(String name) { Assertions.assertEquals(stringBuilder.append(new String())
			.toString(), App.greet(name)); }
	@ParameterizedTest
	@MethodSource("missLine")
	public void checkWithNull(String name) { Assertions.assertEquals(stringBuilder.append((String)null)
			.toString(), App.greet(name)); }
}