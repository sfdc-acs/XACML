/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.validators;

import com.vaadin.data.Validator;

public class DoubleValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public DoubleValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof Double) {
			return;
		}
		try {
			Double.parseDouble(value.toString());
		} catch (NumberFormatException e) {
			throw new InvalidValueException(e.getLocalizedMessage());
		}
	}

}
