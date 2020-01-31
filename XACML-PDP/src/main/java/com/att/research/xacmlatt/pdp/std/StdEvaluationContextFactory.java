/*
 *
 *          Copyright (c) 2013,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacmlatt.pdp.std;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.att.research.xacml.api.Request;
import com.att.research.xacml.api.pip.PIPFinder;
import com.att.research.xacml.api.pip.PIPFinderFactory;
import com.att.research.xacml.api.trace.TraceEngine;
import com.att.research.xacml.api.trace.TraceEngineFactory;
import com.att.research.xacmlatt.pdp.eval.EvaluationContext;
import com.att.research.xacmlatt.pdp.eval.EvaluationContextFactory;
import com.att.research.xacmlatt.pdp.policy.PolicyFinder;
import com.att.research.xacmlatt.pdp.policy.PolicyFinderFactory;

/**
 * StdEvaluationContextFactory extends {@link com.att.research.xacmlatt.pdp.eval.EvaluationContextFactory} to implement
 * the <code>getEvaluationContext</code> method with a standard {@link com.att.research.xacmlatt.pdp.eval.EvaluationContext}.
 * 
 * @author car
 * @version $Revision: 1.1 $
 */
public class StdEvaluationContextFactory extends EvaluationContextFactory {
	private Log logger					= LogFactory.getLog(this.getClass());
	private PolicyFinder policyFinder;
	private PIPFinder pipFinder;
	private TraceEngine traceEngine;
	
	/**
	 * Should this properties file be passed onward when instantiating the PolicyFinder 
	 * and the PIPFinder?
	 * 
	 * If yes, then we are assuming that the given properties were not just meant to
	 * configure the evaluation context, but all the other engines that get created.
	 * 
	 * If no, then we are assuming the given properties were only meant for the evaluation
	 * context. But this implementation as of 7/14 does not even need the properties for
	 * configuring itseof.
	 * 
	 * The problem is, the caller does not have the ability to instantiate the PIPFinder
	 * and PolicyFinder engines. This is done internally by the evaluation context. So how
	 * can they have the ability to customize PIP/Policy factories with their own properties 
	 * object if the properties file isn't passed on?
	 * 
	 * Thus, this class will pass on the properties file if given in the constructor.
	 * 
	 */
	protected Properties properties = null;

	protected PolicyFinder getPolicyFinder() {
		if (this.policyFinder == null) {
			synchronized(this) {
				if (this.policyFinder == null) {
					try {
						if (this.properties == null) {
							if (this.logger.isDebugEnabled()) {
								this.logger.debug("getting Policy finder using default properties");
							}
							PolicyFinderFactory policyFinderFactory	= PolicyFinderFactory.newInstance();
							this.policyFinder	= policyFinderFactory.getPolicyFinder();
						} else {
							if (this.logger.isDebugEnabled()) {
								this.logger.debug("getting Policy finder using properties: " + this.properties);
							}
							PolicyFinderFactory policyFinderFactory	= PolicyFinderFactory.newInstance(this.properties);
							this.policyFinder	= policyFinderFactory.getPolicyFinder(this.properties);
						}
					} catch (Exception ex) {
						this.logger.error("Exception getting PolicyFinder: " + ex.getMessage(), ex);
					}
				}
			}
		}
		return this.policyFinder;
	}
	
	protected PIPFinder getPIPFinder() {
		if (this.pipFinder == null) {
			synchronized(this) {
				if (this.pipFinder == null) {
					try {
						if (this.properties == null) {
							if (this.logger.isDebugEnabled()) {
								this.logger.debug("getting PIP finder using default properties");
							}
							PIPFinderFactory pipFinderFactory	= PIPFinderFactory.newInstance();
							this.pipFinder						= pipFinderFactory.getFinder();
						} else {
							if (this.logger.isDebugEnabled()) {
								this.logger.debug("getting PIP finder using properties: " + this.properties);
							}
							PIPFinderFactory pipFinderFactory	= PIPFinderFactory.newInstance(this.properties);
							this.pipFinder						= pipFinderFactory.getFinder(this.properties);
						}
					} catch (Exception ex) {
						this.logger.error("Exception getting PIPFinder: " + ex.toString(), ex);
					}
				}
			}
		}
		return this.pipFinder;
	}
	
	protected TraceEngine getTraceEngine() {
		if (this.traceEngine == null) {
			synchronized(this) {
				if (this.traceEngine == null) {
					try {
						if (this.properties == null) {
							TraceEngineFactory traceEngineFactory	= TraceEngineFactory.newInstance();
							this.traceEngine	= traceEngineFactory.getTraceEngine();
						} else {
							TraceEngineFactory traceEngineFactory	= TraceEngineFactory.newInstance(this.properties);
							this.traceEngine	= traceEngineFactory.getTraceEngine(this.properties);
						}
					} catch (Exception ex) {
						this.logger.error("Exception getting TraceEngine: " + ex.toString(), ex);
					}
				}
			}
		}
		return this.traceEngine;
	}
	
	public StdEvaluationContextFactory() {
	}

	public StdEvaluationContextFactory(Properties properties) {
		this.properties = properties;
	}

	@Override
	public EvaluationContext getEvaluationContext(Request request) {
		if (this.properties == null) {
			return new StdEvaluationContext(request, this.getPolicyFinder(), this.getPIPFinder(), this.getTraceEngine());
		} else {
			return new StdEvaluationContext(request, this.getPolicyFinder(), this.getPIPFinder(), this.getTraceEngine(), this.properties);
		}
	}

	@Override
	public void setPolicyFinder(PolicyFinder policyFinderIn) {
		this.policyFinder	= policyFinderIn;
	}

	@Override
	public void setPIPFinder(PIPFinder pipFinderIn) {
		this.pipFinder		= pipFinderIn;
	}

}
