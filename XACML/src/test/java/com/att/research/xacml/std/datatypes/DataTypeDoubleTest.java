/*
 *                        AT&T - PROPRIETARY
 *          THIS FILE CONTAINS PROPRIETARY INFORMATION OF
 *        AT&T AND IS NOT TO BE DISCLOSED OR USED EXCEPT IN
 *             ACCORDANCE WITH APPLICABLE AGREEMENTS.
 *
 *          Copyright (c) 2018 AT&T Knowledge Ventures
 *              Unpublished and Not for Publication
 *                     All Rights Reserved
 */

package com.att.research.xacml.std.datatypes;

import static org.junit.Assert.*;

import org.junit.Test;

import com.att.research.xacml.api.DataTypeException;

public class DataTypeDoubleTest {

	@Test
	public void test() throws DataTypeException {
		DataTypeDouble dbl = DataTypeDouble.newInstance();
		assertEquals(null, dbl.convert(null));
		Double myDouble = 3.14;
		assertEquals(myDouble, dbl.convert(myDouble));
		assertEquals(myDouble, dbl.convert(myDouble.toString()));
		assertTrue(dbl.convert("INF").isInfinite());
		assertTrue(dbl.convert("-INF").isInfinite());
	}

	@Test(expected = DataTypeException.class)
	public void testParse() throws DataTypeException {
		DataTypes.DT_DOUBLE.convert("abc");
	}
}
