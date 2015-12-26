package jp.dip.gpsoft.lanmap.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleCheckPasswordValidator.class)
public @interface DoubleCheckPassword {
	String message() default "{DoubleCheckPassword.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String field(); // フィールド名。
	String fieldRepeated(); // 確認フィールド名。
}
