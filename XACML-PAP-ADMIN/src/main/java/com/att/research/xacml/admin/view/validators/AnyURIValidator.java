/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.validators;

import java.net.URI;
import java.net.URISyntaxException;

import com.vaadin.data.Validator;

public class AnyURIValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public AnyURIValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof String) {
			try {
				new URI((String) value);
			} catch (URISyntaxException e) {
				throw new InvalidValueException(e.getLocalizedMessage());
			}
		} else
			throw new InvalidValueException("Unrecognized URI");
	}
}
