/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.validators;

import org.apache.commons.codec.binary.Base64;

import com.vaadin.data.Validator;

public class Base64BinaryValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public Base64BinaryValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof String) {
			// try {
			new Base64().decode((String) value);
		// Base64().decode(String) inherited from 
		// org.apache.commons.codec.binary.BaseNCodec
		// does not throw DecoderException in version 1.10
		// of org.apache.commons.codec (the version we are
		// using).  This may need to be uncommented in later
		// versions.
		//
		// TODO - Since this does not throw an exception under
		//        any circumstance, I question whether it is
		//        a valid method of validating the input value.
		//
		// } catch (DecoderException e) {
		// 	throw new InvalidValueException(e.getLocalizedMessage());
		// }
		} else
			throw new InvalidValueException("Unrecognized Base64 Binary");
	}
}
