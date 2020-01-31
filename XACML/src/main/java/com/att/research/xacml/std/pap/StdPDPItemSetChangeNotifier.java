/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.std.pap;

import java.util.Collection;
import java.util.LinkedList;

import com.att.research.xacml.api.pap.PDP;
import com.att.research.xacml.api.pap.PDPGroup;

public class StdPDPItemSetChangeNotifier {
	
	private Collection<StdItemSetChangeListener> listeners = null;
	
	public interface StdItemSetChangeListener {
		
		public void changed();
		
		public void groupChanged(PDPGroup group);
		
		public void pdpChanged(PDP pdp);
	}
	
	public void addItemSetChangeListener(StdItemSetChangeListener listener) {
		if (this.listeners == null) {
			this.listeners = new LinkedList<StdItemSetChangeListener>();
		}
		this.listeners.add(listener);
	}
	
	public void removeItemSetChangeListener(StdItemSetChangeListener listener) {
		if (this.listeners != null) {
			this.listeners.remove(listener);
		}
	}

	public void fireChanged() {
		if (this.listeners == null) {
			return;
		}
		for (StdItemSetChangeListener l : this.listeners) {
			l.changed();
		}		
	}

	public void firePDPGroupChanged(PDPGroup group) {
		if (this.listeners == null) {
			return;
		}
		for (StdItemSetChangeListener l : this.listeners) {
			l.groupChanged(group);
		}
	}

	public void firePDPChanged(PDP pdp) {
		if (this.listeners == null) {
			return;
		}
		for (StdItemSetChangeListener l : this.listeners) {
			l.pdpChanged(pdp);
		}
	}
}
