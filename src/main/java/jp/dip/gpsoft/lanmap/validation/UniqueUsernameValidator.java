package jp.dip.gpsoft.lanmap.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.dip.gpsoft.lanmap.service.UserService;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	private UserService userService;

	@Override
	public void initialize(UniqueUsername annotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null) {
            return true;
        }
        return userService.isUniqueName(value);
	}

}
