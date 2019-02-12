/*
 *
 *          Copyright (c) 2013,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.converters;

import java.util.Locale;

import com.att.research.xacml.admin.jpa.Category;
import com.att.research.xacml.api.Identifier;
import com.vaadin.data.util.converter.Converter;

public class CategoryConverter implements Converter<Object, Category> {
	private static final long serialVersionUID = 1L;

	@Override
	public Category convertToModel(Object value,
			Class<? extends Category> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		Category category = new Category();
		if (value == null) {
			return category;
		}
		if (value instanceof Identifier) {
			category.setXacmlId(((Identifier)value).stringValue());
		} else {
			category.setXacmlId(value.toString());
		}
		return category;
	}

	@Override
	public Object convertToPresentation(Category value,
			Class<? extends Object> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (targetType.getName().equals(String.class.getName())) {
			return value.getXacmlId();
		}
		if (targetType.getName().equals(Identifier.class.getName())) {
			return value.getIdentifer();
		}
		return value.getIdentifer();
	}

	@Override
	public Class<Category> getModelType() {
		return Category.class;
	}

	@Override
	public Class<Object> getPresentationType() {
		return Object.class;
	}

}
