/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.validators;

import javax.security.auth.x500.X500Principal;

import com.vaadin.data.Validator;

public class X500NameValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public X500NameValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof String) {
			try {
				new X500Principal((String) value);
			} catch (IllegalArgumentException e) {
				throw new InvalidValueException(e.getLocalizedMessage());
			}
		} else
			throw new InvalidValueException("Unrecognized X500 Name");
	}
}
