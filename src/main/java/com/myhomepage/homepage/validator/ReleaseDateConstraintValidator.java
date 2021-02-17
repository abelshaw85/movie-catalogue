package com.myhomepage.homepage.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReleaseDateConstraintValidator implements ConstraintValidator<ReleaseDate, LocalDate>
{
	// Value passed into the annotation
	private LocalDate earliestDate;
	
	@Override
	public void initialize(ReleaseDate releaseDate) {
		this.earliestDate = LocalDate.parse(releaseDate.value());
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return value.isAfter(earliestDate) || value.isEqual(earliestDate);
	}
}
