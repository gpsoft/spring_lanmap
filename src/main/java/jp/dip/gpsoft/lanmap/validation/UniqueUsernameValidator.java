package jp.dip.gpsoft.lanmap.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dip.gpsoft.lanmap.form.UserForm;
import jp.dip.gpsoft.lanmap.service.UserService;
import jp.dip.gpsoft.lanmap.utils.Utils;

@Component
public class UniqueUsernameValidator
		implements
			ConstraintValidator<UniqueUsername, UserForm> {

	private String message;

	@Autowired
	private UserService userService;

	@Override
	public void initialize(UniqueUsername annotation) {
		message = annotation.message();
	}

	@Override
	public boolean isValid(UserForm value, ConstraintValidatorContext ctx) {
		if (isValid(value)) {
			return true;
		}
		// フィールドへ適用するバリデータなら、単純にfalseを返せばよい。
		// initialize()の中でmessageを退避する必要もない。
		// 一方、クラスへ適用するバリデータの場合は、ちょっと手間がかかる。
		ctx.disableDefaultConstraintViolation();
		ctx.buildConstraintViolationWithTemplate(message)
				.addPropertyNode("name").addConstraintViolation();
		return false;
	}

	private boolean isValid(UserForm value) {
		if (Utils.empty(value.getName())) {
			return false;
		}
		return userService.isUniqueName(value.getId(), value.getName());
	}

}
