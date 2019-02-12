/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.api.pap;

import java.util.Set;

import com.att.research.xacml.std.pap.StdPDPStatus;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/*
 * The following allows us to use Jackson to convert sub-types of this type into JSON and back to objects.
 */
@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "PDPStatusType")  
@JsonSubTypes({  
	    @Type(value = StdPDPStatus.class, name = "StdPDPStatus") }) 
public interface PDPStatus {
	
	public enum Status {
		UP_TO_DATE,
		OUT_OF_SYNCH,
		LOAD_ERRORS,
		UPDATING_CONFIGURATION,
		LAST_UPDATE_FAILED,
		UNKNOWN,
		NO_SUCH_HOST,
		CANNOT_CONNECT
	}
	
	public Status				getStatus();
	
	public Set<String>			getLoadErrors();
	
	public Set<String>			getLoadWarnings();
	
	public Set<PDPPolicy>		getLoadedPolicies();
	
	public Set<PDPPolicy>		getLoadedRootPolicies();
	
	public Set<PDPPolicy>		getFailedPolicies();
	
	public boolean				policiesOK();
	
	public Set<PDPPIPConfig>	getLoadedPipConfigs();
	
	public Set<PDPPIPConfig>	getFailedPipConfigs();
	
	public boolean				pipConfigOK();
	
	public boolean				isOk();

}
