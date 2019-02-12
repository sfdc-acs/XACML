/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.api.pap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.att.research.xacml.std.pap.StdPDPPolicy;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/*
 * The following allows us to use Jackson to convert sub-types of this type into JSON and back to objects.
 */
@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "PDPPolicyType")  
@JsonSubTypes({  
	    @Type(value = StdPDPPolicy.class, name = "StdPDPPolicy") })  
public interface PDPPolicy {
	
	public String 		getId();
	
	public String		getName();
	
	public String		getPolicyId();
	
	public String		getDescription();

	public String 		getVersion();
	public int[]		getVersionInts();
	
	public boolean		isRoot();
	
	public boolean		isValid();

	public InputStream 	getStream() throws PAPException, IOException;

	public URI			getLocation() throws PAPException, IOException;
}
