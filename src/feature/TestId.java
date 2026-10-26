package feature;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)//если поставить class или source, информация об аннотациях перестанет читаться при рефлексии
@Target(ElementType.METHOD)
public @interface TestId {
	String value();
	int order();
}