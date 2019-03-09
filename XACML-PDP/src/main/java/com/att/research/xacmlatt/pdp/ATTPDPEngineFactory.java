/*
 *
 *          Copyright (c) 2013,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacmlatt.pdp;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.att.research.xacml.api.pdp.PDPEngine;
import com.att.research.xacml.api.pdp.PDPEngineFactory;
import com.att.research.xacml.util.FactoryException;
import com.att.research.xacmlatt.pdp.eval.EvaluationContextFactory;

/**
 * ATTPDPEngineFactory extends {@link com.att.research.xacml.api.pdp.PDPEngineFactory} by implementing the abstract
 * <code>newEngine</code> method to create a {@link com.att.research.xacmlatt.pdp.ATTPDPEngine} instance and initialize it
 * with policies and PIP instances based on configuration information provided to the factory.
 * 
 * @author car
 * @version $Revision: 1.4 $
 */
public class ATTPDPEngineFactory extends PDPEngineFactory {
	private Log logger	= LogFactory.getLog(this.getClass());
	
	public ATTPDPEngineFactory() {
		super();
	}

	@Override
	public PDPEngine newEngine() throws FactoryException {
		EvaluationContextFactory evaluationContextFactory	= EvaluationContextFactory.newInstance();
		if (evaluationContextFactory == null) {
			this.logger.error("Null EvaluationContextFactory");
			throw new FactoryException("Null EvaluationContextFactory");
		}
		return new ATTPDPEngine(evaluationContextFactory, this.getDefaultBehavior(), this.getScopeResolver());
	}

	@Override
	public PDPEngine newEngine(Properties properties) throws FactoryException {
		EvaluationContextFactory evaluationContextFactory	= EvaluationContextFactory.newInstance(properties);
		if (evaluationContextFactory == null) {
			this.logger.error("Null EvaluationContextFactory");
			throw new FactoryException("Null EvaluationContextFactory");
		}
		return new ATTPDPEngine(evaluationContextFactory, this.getDefaultBehavior(), this.getScopeResolver(), properties);
	}	
}
