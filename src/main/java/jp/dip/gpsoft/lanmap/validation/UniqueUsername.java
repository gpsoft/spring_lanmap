package jp.dip.gpsoft.lanmap.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * クラスへ適用する、バリデーション用のアノテーション。UserForm専用。ユーザ名が一意かどうかをチェックする。
 * 本来なら、フィールドへ適用するアノテーションにしたかったが、IDフィールドも参照したいのでクラス用にした。
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
	String message() default "{UniqueUsername.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
