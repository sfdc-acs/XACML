/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.events;

import java.util.ArrayList;
import java.util.Collection;

public interface FormChangedEventNotifier {
	public boolean	addListener(FormChangedEventListener listener);
	public boolean	removeListener(FormChangedEventListener listener);
	public void		fireFormChangedEvent();
	
	public class BasicNotifier implements FormChangedEventNotifier {
		Collection<FormChangedEventListener> listeners = null;

		@Override
		public boolean addListener(FormChangedEventListener listener) {
			if (this.listeners == null) {
				this.listeners = new ArrayList<FormChangedEventListener>();
			}
			return this.listeners.add(listener);
		}

		@Override
		public boolean removeListener(FormChangedEventListener listener) {
			if (this.listeners == null) {
				this.listeners = new ArrayList<FormChangedEventListener>();
			}
			return this.listeners.remove(listener);
		}

		@Override
		public void fireFormChangedEvent() {
			if (this.listeners == null) {
				return;
			}
			for (FormChangedEventListener listener : this.listeners) {
				listener.onFormChanged();
			}
		}
		
	}
}
