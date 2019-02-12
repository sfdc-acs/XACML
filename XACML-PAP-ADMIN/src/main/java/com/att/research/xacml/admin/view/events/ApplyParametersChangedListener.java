/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.events;

import com.att.research.xacml.admin.jpa.FunctionArgument;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.ApplyType;

public interface ApplyParametersChangedListener {
	
	public void		applyParameterChanged(ApplyType apply, ApplyType parent, FunctionArgument argument, Object container);

}
