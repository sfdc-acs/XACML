/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.std.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface XACMLAttribute {
	String	category() default "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
	String	attributeId() default "urn:oasis:names:tc:xacml:1.0:subject:subject-id";
	String	datatype() default XACMLRequest.nullString;
	String	issuer() default XACMLRequest.nullString;
	String	id() default XACMLRequest.nullString;
	boolean includeInResults() default false;
}
