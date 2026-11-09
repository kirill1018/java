package app;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
public class App {
	public static void main(String[] args) { System.out.println(greet(name())); }
	public static String name() {
		Properties props = new Properties();
		try (InputStream is = App.class.getResourceAsStream("/app.properties")) {
			if (is == null) return new String();
			props.load(is);
		}
		catch (IOException iOException) {}
		return props.getProperty("student.name");
	}
	public static String greet(String name) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("привет, ");
		stringBuilder.append(name);
		return stringBuilder.toString();
	}
}