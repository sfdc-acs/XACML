/*
 *
 *          Copyright (c) 2018 AT&T Knowledge Ventures
 *              Unpublished and Not for Publication
 *                     All Rights Reserved
 */

package com.att.research.xacml.std.datatypes;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class ISO8601TimeZoneTest {

	@Test(expected = NullPointerException.class)
	public void test() throws ParseException {
		ISO8601TimeZone zone = ISO8601TimeZone.fromString(null);
	}

	@Test(expected = ParseException.class)
	public void testParse00() throws ParseException {
		ISO8601TimeZone zone = ISO8601TimeZone.fromString("");
	}

}
