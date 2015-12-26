package jp.dip.gpsoft.lanmap.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

/**
 * 二重チェックフィールドのバリデーション用アノテーション。対象はObject(UserFormに特化しない)。
 *
 */
@Component
public class DoubleCheckPasswordValidator implements ConstraintValidator<DoubleCheckPassword, Object> {

	private String field;
	private String fieldRepeated;
	private String message;

	@Override
	public void initialize(DoubleCheckPassword annotation) {
		field = annotation.field();
		fieldRepeated = annotation.fieldRepeated();
		message = annotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {
		// 対象がObjectなので、情報を取り出すのに、ひと手間かかる。
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		Object subject = beanWrapper.getPropertyValue(field);
		Object repeated = beanWrapper.getPropertyValue(fieldRepeated);
		if (Objects.equals(subject, repeated)) {
			return true;
		}
		ctx.disableDefaultConstraintViolation();
		ctx.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldRepeated).addConstraintViolation();
		return false;
	}

}
