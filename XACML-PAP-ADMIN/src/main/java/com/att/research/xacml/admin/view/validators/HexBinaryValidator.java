/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.validators;

import org.apache.commons.codec.DecoderException;

import com.att.research.xacml.std.datatypes.HexBinary;
import com.vaadin.data.Validator;

public class HexBinaryValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public HexBinaryValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof String) {
			try {
				HexBinary.newInstance((String) value);
			} catch (DecoderException e) {
				throw new InvalidValueException(e.getLocalizedMessage());
			}
		} else
			throw new InvalidValueException("Unrecognized HexBinary");
	}
}
