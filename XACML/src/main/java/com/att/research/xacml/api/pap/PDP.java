/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.api.pap;

import java.util.Set;

import com.att.research.xacml.std.pap.StdPDP;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/*
 * The following allows us to use Jackson to convert sub-types of this type into JSON and back to objects.
 */
@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "PDPType")  
@JsonSubTypes({  
	    @Type(value = StdPDP.class, name = "StdPDP") })  
public interface PDP {
	
	public String 						getId();
	
	public String						getName();
	
	public void							setName(String name);
	
	public String						getDescription();
	
	public void							setDescription(String description);
	
	public PDPStatus					getStatus();

	public Set<PDPPolicy>	 			getPolicies();

	public Set<PDPPIPConfig> 			getPipConfigs();
}
