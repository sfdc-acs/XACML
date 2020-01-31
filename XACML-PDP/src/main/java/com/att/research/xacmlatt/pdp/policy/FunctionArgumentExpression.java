/*
 *
 *          Copyright (c) 2013,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacmlatt.pdp.policy;

import com.att.research.xacml.api.AttributeValue;
import com.att.research.xacml.api.Status;
import com.att.research.xacml.std.StdStatus;
import com.att.research.xacml.std.StdStatusCode;
import com.att.research.xacmlatt.pdp.eval.EvaluationContext;
import com.att.research.xacmlatt.pdp.eval.EvaluationException;

/**
 * FunctionArgumentExpression implements the {@link com.att.research.xacmlatt.pdp.policy.FunctionArgument} interface for
 * unevaluated {@link com.att.research.xacmlatt.pdp.policy.Expression}s.
 * 
 * @author car
 * @version $Revision: 1.3 $
 */
public class FunctionArgumentExpression implements FunctionArgument {
	private static final Status STATUS_NULL_EXPRESSION_RESULT	= new StdStatus(StdStatusCode.STATUS_CODE_PROCESSING_ERROR, "Null expression result");
	
	private Expression expression;
	private EvaluationContext evaluationContext;
	private ExpressionResult expressionResult;
	private PolicyDefaults policyDefaults;
	
	protected ExpressionResult evaluateExpression() {
		if (this.getExpression() != null && this.getEvaluationContext() != null) {
			try {
				this.expressionResult	= this.getExpression().evaluate(this.getEvaluationContext(), this.getPolicyDefaults());
			} catch (EvaluationException ex) {
				this.expressionResult	= ExpressionResult.newError(new StdStatus(StdStatusCode.STATUS_CODE_PROCESSING_ERROR, ex.getMessage()));
			}
		}
		return this.expressionResult;
	}
	
	public FunctionArgumentExpression() {
	}
	
	public FunctionArgumentExpression(Expression expressionIn, EvaluationContext evaluationContextIn, PolicyDefaults policyDefaultsIn) {
		this.expression			= expressionIn;
		this.evaluationContext	= evaluationContextIn;
		this.policyDefaults		= policyDefaultsIn;
	}
	
	protected ExpressionResult getExpressionResult() {
		return this.expressionResult;
	}
	
	protected Expression getExpression() {
		return this.expression;
	}
	
	protected EvaluationContext getEvaluationContext() {
		return this.evaluationContext;
	}
	
	protected PolicyDefaults getPolicyDefaults() {
		
		return this.policyDefaults;
	}

	@Override
	public Status getStatus() {
		ExpressionResult thisExpressionResult	= this.getExpressionResult();
		if (thisExpressionResult == null) {
			thisExpressionResult	= this.evaluateExpression();
		}
		return (thisExpressionResult == null ? STATUS_NULL_EXPRESSION_RESULT : thisExpressionResult.getStatus());
	}
	
	@Override
	public boolean isOk() {
		Status thisStatus	= this.getStatus();
		return (thisStatus == null ? true : thisStatus.isOk());
	}

	@Override
	public boolean isBag() {
		ExpressionResult thisExpressionResult	= this.getExpressionResult();
		if (thisExpressionResult == null) {
			thisExpressionResult	= this.evaluateExpression();
		}
		return (thisExpressionResult == null ? false : thisExpressionResult.isBag());
	}

	@Override
	public AttributeValue<?> getValue() {
		ExpressionResult thisExpressionResult	= this.getExpressionResult();
		if (thisExpressionResult == null) {
			thisExpressionResult	= this.evaluateExpression();
		}
		return (thisExpressionResult == null ? null : thisExpressionResult.getValue());
	}

	@Override
	public Bag getBag() {
		ExpressionResult thisExpressionResult	= this.getExpressionResult();
		if (thisExpressionResult == null) {
			thisExpressionResult	= this.evaluateExpression();
		}
		return (thisExpressionResult == null ? null : thisExpressionResult.getBag());
	}
}
