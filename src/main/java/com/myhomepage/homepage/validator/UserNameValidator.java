package com.myhomepage.homepage.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<UserName, String>
{
	private String userNameRegex = "[\\w_-]+";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null || value.matches(userNameRegex);
	}
}
