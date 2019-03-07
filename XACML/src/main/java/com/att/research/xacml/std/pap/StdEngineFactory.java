/*
 *
 *          Copyright (c) 2013,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.std.pap;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.att.research.xacml.api.pap.PAPEngine;
import com.att.research.xacml.api.pap.PAPEngineFactory;
import com.att.research.xacml.api.pap.PAPException;
import com.att.research.xacml.util.FactoryException;

public class StdEngineFactory extends PAPEngineFactory {
	private static final Logger	logger	= LoggerFactory.getLogger(StdEngineFactory.class);
	
	@Override
	public PAPEngine newEngine() throws FactoryException, PAPException {
		try {
			return new StdEngine();
		} catch (IOException e) {
			logger.error("Failed to create engine: ", e);
			return null;
		}
	}

	@Override
	public PAPEngine newEngine(Properties properties) throws FactoryException,
			PAPException {
		try {
			return new StdEngine(properties);
		} catch (IOException e) {
			logger.error("Failed to create engine: ", e);
			return null;
		}
	}

}
