package com.movie.catalogue.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImdbIdConstraintValidator implements ConstraintValidator<ImdbId, String>
{
	private String imdbRegex = "tt[0-9]{5,}";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null || value.matches(imdbRegex);
	}
}
