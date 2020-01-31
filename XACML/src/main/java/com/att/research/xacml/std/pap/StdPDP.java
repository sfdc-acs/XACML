/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.std.pap;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.att.research.xacml.api.pap.PDP;
import com.att.research.xacml.api.pap.PDPPIPConfig;
import com.att.research.xacml.api.pap.PDPPolicy;
import com.att.research.xacml.api.pap.PDPStatus;

public class StdPDP extends StdPDPItemSetChangeNotifier implements PDP, Comparable<StdPDP>, Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger	logger	= LoggerFactory.getLogger(StdPDP.class);
	
	private String id;
	
	private String name;
	
	private String description;
	
	private PDPStatus status = new StdPDPStatus();
	
	private Set<PDPPolicy> policies = new HashSet<PDPPolicy>();
	
	private Set<PDPPIPConfig> pipConfigs = new HashSet<PDPPIPConfig>();
	
	public StdPDP() {
		
	}
	
	public StdPDP(String id) {
		this(id, null, null);
	}
	
	public StdPDP(String id, String name) {
		this(id, name, null);
	}
	
	public StdPDP(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public StdPDP(String id, Properties properties) {
		this(id);
		
		this.initialize(properties);
	}
	
	public void initialize(Properties properties) {
		for (Object key : properties.keySet()) {
			if (key.toString().startsWith(this.id + ".")) {
				if (logger.isDebugEnabled()) {
					logger.debug("Found: " + key);
				}
				if (key.toString().endsWith(".name")) {
					this.name = properties.getProperty(key.toString());
				} else if (key.toString().endsWith(".description")) {
					this.description = properties.getProperty(key.toString());
				}
			}
		}
	}

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id=id;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
		this.firePDPChanged(this);
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public void setDescription(String description) {
		this.description = description;
		this.firePDPChanged(this);
	}

	@Override
	public PDPStatus getStatus() {
		return this.status;
	}

	public void setStatus(PDPStatus status) {
		this.status = status;
	}
	
	@Override
	public Set<PDPPolicy> getPolicies() {
		return Collections.unmodifiableSet(this.policies);
	}
	
	public void setPolicies(Set<PDPPolicy> policies) {
		this.policies = policies;
	}

	@Override
	public Set<PDPPIPConfig> getPipConfigs() {
		return Collections.unmodifiableSet(this.pipConfigs);
	}
	
	public void setPipConfigs(Set<PDPPIPConfig> pipConfigs) {
		this.pipConfigs = pipConfigs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StdPDP other = (StdPDP) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StdPDP [id=" + id + ", name=" + name + ", description="
				+ description + ", status=" + status + ", policies=" + policies
				+ ", pipConfigs=" + pipConfigs + "]";
	}
	
	//
	// Comparable interface
	//
	@Override
	public int compareTo(StdPDP o) {
		if (o == null) {
			return -1;
		}
		if ( ! (o instanceof StdPDP)) {
			return -1;
		}
		if (((StdPDP)o).name == null) {
			return -1;
		}
		if (name == null) {
			return 1;
		}
		return name.compareTo(((StdPDP)o).name);
	}
	
}
