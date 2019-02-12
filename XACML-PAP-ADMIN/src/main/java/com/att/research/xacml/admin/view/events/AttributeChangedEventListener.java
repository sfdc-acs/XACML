/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.events;

import com.att.research.xacml.admin.jpa.Attribute;

public interface AttributeChangedEventListener {
	public void	attributeChanged(Attribute attribute);
}
