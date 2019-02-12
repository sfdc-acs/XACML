/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.converters;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.att.research.xacml.admin.jpa.ConstraintValue;
import com.vaadin.data.util.converter.Converter;

public class ConstraintValueConverter implements Converter<Object, ConstraintValue> {
	private static final long serialVersionUID = 1L;
	private static final Log logger	= LogFactory.getLog(ConstraintValueConverter.class);
	
	@Override
	public ConstraintValue convertToModel(Object value,
			Class<? extends ConstraintValue> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (logger.isTraceEnabled()) {
			logger.trace("convertToModel:" + value + " target " + targetType);
		}
		ConstraintValue newValue = new ConstraintValue();
		if (value == null) {
			return newValue;
		}
		// PLD TODO?
		return newValue;
	}

	@Override
	public Object convertToPresentation(ConstraintValue value,
			Class<? extends Object> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (logger.isTraceEnabled()) {
			logger.trace("convertToPresentation:" + value + " target " + targetType);
		}
		if (value == null) {
			return null;
		}
		return value.getProperty();
	}

	@Override
	public Class<ConstraintValue> getModelType() {
		return ConstraintValue.class;
	}

	@Override
	public Class<Object> getPresentationType() {
		return Object.class;
	}

}
