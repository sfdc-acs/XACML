/*
 *
 *          Copyright (c) 2013,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacmlatt.pdp.std.functions;


import java.util.List;

import com.att.research.xacml.api.DataType;
import com.att.research.xacml.api.DataTypeException;
import com.att.research.xacml.api.Identifier;
import com.att.research.xacml.std.StdStatus;
import com.att.research.xacml.std.StdStatusCode;
import com.att.research.xacml.std.datatypes.IDateTime;
import com.att.research.xacml.std.datatypes.ISO8601Duration;
import com.att.research.xacmlatt.pdp.eval.EvaluationContext;
import com.att.research.xacmlatt.pdp.policy.ExpressionResult;
import com.att.research.xacmlatt.pdp.policy.FunctionArgument;

/**
 * FunctionDefinitionDateTimeArithmetic implements {@link com.att.research.xacmlatt.pdp.policy.FunctionDefinition} to
 * implement the XACML Date and Time Arithmetic predicates.
 * 
 * In the first implementation of XACML we had separate files for each XACML Function.
 * This release combines multiple Functions in fewer files to minimize code duplication.
 * This file supports the following XACML codes:
 * 		dateTime-add-dayTimeDuration
 * 		dateTime-add-yearMonthDuration
 * 		dateTime-subtract-dayTimeDuration
 * 		dateTime-subtract-yearMonthDuration
 * 		date-add-yearMonthDuration
 * 		date-subtract-yearMonthDuration
 * 
 * 
 * @author glenngriffin
 * @version $Revision: 1.1 $
 * 
 * @param <I> the java class for the data type of the function Input arguments;
 * 		SPECIAL CASE: this applies ONLY to the 2nd argument.
 * @param <O> the java class for the data type of the function Output;
 * 		SPECIAL CASE: this ALSO applies to the type of the 1st Input argument.
 */
public class FunctionDefinitionDateTimeArithmetic<O extends IDateTime<O>, I extends ISO8601Duration> extends FunctionDefinitionBase<O, I> {

	/**
	 * List of Date and Time Arithmetic operations.
	 * 
	 * @author glenngriffin
	 *
	 */
	public enum OPERATION {ADD, SUBTRACT};
	
	// operation to be used in this instance of the class
	private final OPERATION operation;

	
	
	/**
	 * Constructor - need dataTypeArgs input because of java Generic type-erasure during compilation.
	 * 
	 * @param idIn
	 * @param dataTypeArgsIn
	 */
	public FunctionDefinitionDateTimeArithmetic(Identifier idIn, DataType<O> dataTypeIn, DataType<I> dataTypeArgsIn, OPERATION op) {
		super(idIn, dataTypeIn, dataTypeArgsIn, false);
		this.operation = op;
	}

	@Override
	public ExpressionResult evaluate(EvaluationContext evaluationContext, List<FunctionArgument> arguments) {
		if (arguments == null ||  arguments.size() != 2) {
			return ExpressionResult.newError(new StdStatus(StdStatusCode.STATUS_CODE_PROCESSING_ERROR, this.getShortFunctionId() + 
					" Expected 2 arguments, got " + 
					((arguments == null) ? "null" : arguments.size()) ));
		}
		
		// first arg has same type as function output
		FunctionArgument functionArgument = arguments.get(0);
		ConvertedArgument<O> convertedArgument0 = new ConvertedArgument<O>(functionArgument, this.getDataType(), false);
		if ( ! convertedArgument0.isOk()) {
			return ExpressionResult.newError(getFunctionStatus(convertedArgument0.getStatus()));
		}
		O idateOrig	= convertedArgument0.getValue();
		
		// second argument is of input type
		functionArgument = arguments.get(1);
		ConvertedArgument<I> convertedArgument1 = new ConvertedArgument<I>(functionArgument, this.getDataTypeArgs(), false);
		if ( ! convertedArgument1.isOk()) {
			return ExpressionResult.newError(getFunctionStatus(convertedArgument1.getStatus()));
		}
		// get the Duration object from the argument which includes all fields, even if the incoming argument does not include them all
		ISO8601Duration duration = convertedArgument1.getValue();
		
		// add/subtract the duration to the input argument
		//
		O idateResult	= null;
		switch(this.operation) {
		case ADD:
			idateResult	= idateOrig.add(duration);
			break;
		case SUBTRACT:
			idateResult	= idateOrig.sub(duration);
			break;
		}
		ExpressionResult expressionResult	= null;
		try {
			expressionResult	= ExpressionResult.newSingle(this.getDataType().createAttributeValue(idateResult)); 
		} catch (DataTypeException e) {
			String message = e.getMessage();
			if (e.getCause() != null) {
				message = e.getCause().getMessage();
			}
			return ExpressionResult.newError(new StdStatus(StdStatusCode.STATUS_CODE_PROCESSING_ERROR, this.getShortFunctionId() + " " + message));			
		}
		return expressionResult; 
	}
	
}
