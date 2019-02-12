/*
 *
 *          Copyright (c) 2018-2019 AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */

package com.att.research.xacml.std.datatypes;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import com.att.research.xacml.api.DataTypeException;

public class DataTypeDateTest {

	@Test
	public void test() throws DataTypeException {
		DataTypeDate dateType = DataTypeDate.newInstance();
		Calendar calendar = Calendar.getInstance();
		ISO8601Date iso8601 = ISO8601Date.fromCalendar(calendar);

		assertEquals(iso8601, dateType.convert(iso8601));
		assertEquals(iso8601, dateType.convert(calendar));
		assertEquals(2007, dateType.convert("2007-05-04").getYear());
	}

	@Test(expected = DataTypeException.class)
	public void testParse() throws DataTypeException {
		DataTypeDate.newInstance().convert("888888");
	}
}
