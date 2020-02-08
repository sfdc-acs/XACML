/*
 *
 *          Copyright (c) 2018-2020 AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */

package com.att.research.xacml.std.datatypes;

import java.text.ParseException;
import org.junit.Test;

public class ISO8601TimeZoneTest {

	@Test(expected = NullPointerException.class)
	public void test() throws ParseException {
		ISO8601TimeZone.fromString(null);
	}

	@Test(expected = ParseException.class)
	public void testParse00() throws ParseException {
		ISO8601TimeZone.fromString("");
	}

}
