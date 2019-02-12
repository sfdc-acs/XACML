/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.events;

import java.util.ArrayList;
import java.util.Collection;

import com.att.research.xacml.admin.jpa.Attribute;

public interface AttributeChangedEventNotifier {

	public boolean	addListener(AttributeChangedEventListener listener);
	
	public boolean removeListener(AttributeChangedEventListener listener);

	public void	fireAttributeChanged(Attribute attribute);
	
	public void commit();
	
	public Attribute	getAttribute();
	
	public class BasicNotifier implements AttributeChangedEventNotifier {
		Collection<AttributeChangedEventListener>	listeners = null;

		@Override
		public boolean addListener(AttributeChangedEventListener listener) {
			if (this.listeners == null) {
				this.listeners = new ArrayList<AttributeChangedEventListener>();
			}
			return this.listeners.add(listener);
		}

		@Override
		public boolean removeListener(AttributeChangedEventListener listener) {
			if (this.listeners == null) {
				return false;
			}
			return this.listeners.remove(listener);
		}

		@Override
		public void fireAttributeChanged(Attribute attribute) {
			if (this.listeners == null) {
				return;
			}
			for (AttributeChangedEventListener listener : this.listeners) {
				listener.attributeChanged(attribute);
			}
		}
		
		public void commit() {
			
		}

		public Attribute	getAttribute() {
			return null;
		}
		
	}
}
