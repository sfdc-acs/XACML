/*
 *
 *          Copyright (c) 2018-2019 AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */

package com.att.research.xacml.std.datatypes;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;

public class ISO8601DateTest {

	@Test
	public void test() throws ParseException {
		//
		// ISO8601 WITHOUT the time element
		//
		Calendar calendar = Calendar.getInstance();
		ISO8601Date iso8601 = ISO8601Date.fromCalendar(calendar);
		assertEquals(calendar.getTimeZone(), iso8601.getCalendar().getTimeZone());
		assertEquals(calendar.getWeekYear(), iso8601.getCalendar().getWeekYear());
		assertTrue(iso8601.hashCode() != 0);
		assertTrue(iso8601.stringValue(true).startsWith(Integer.toString(calendar.get(Calendar.YEAR))));

		//
		// String test
		//
		iso8601 = ISO8601Date.fromISO8601DateString("2007-04-05Z");
		assertEquals(2007, iso8601.getYear());
		assertEquals(04, iso8601.getMonth());
		assertEquals(05, iso8601.getDay());
		assertEquals("Z", iso8601.getTimeZone());
		assertTrue(iso8601.getHasTimeZone());
		
		ISO8601TimeZone zone = ISO8601TimeZone.fromString("Z");
		assertEquals(iso8601, new ISO8601Date(zone, 2007, 04, 05));
		
		iso8601 = ISO8601Date.fromISO8601DateString("2007-04-05-05:00");
		assertEquals(2007, iso8601.getYear());
		assertEquals(04, iso8601.getMonth());
		assertEquals(05, iso8601.getDay());
		assertTrue(iso8601.getHasTimeZone());

		iso8601 = ISO8601Date.fromISO8601DateString("2007-04-05");
		assertFalse(iso8601.getHasTimeZone());
		
		assertEquals(iso8601, iso8601);
		assertNotEquals(iso8601, new String());
		//
		// Constructor coverage
		//
		assertTrue(iso8601.stringValue(false).startsWith("2007-04-05"));
		assertTrue(iso8601.stringValue().startsWith("2007-04-05"));
		assertTrue(iso8601.toString().startsWith("2007-04-05"));
		assertEquals(iso8601, new ISO8601Date(iso8601.getYear(), iso8601.getMonth(), iso8601.getDay()));
		zone = null;
		assertEquals(iso8601, new ISO8601Date(zone, 2007, 04, 05));
		
		//
		// Duration
		//
		ISO8601Date add = iso8601.add(ISO8601Duration.newInstance("P3Y"));
		assertEquals(2010, add.getYear());
		assertTrue(iso8601.compareTo(add) < 0);
		ISO8601Date sub = iso8601.sub(ISO8601Duration.newInstance("P3Y"));
		assertEquals(2004, sub.getYear());
		assertTrue(iso8601.compareTo(sub) > 0);
	}
	
	@Test(expected = ParseException.class)
	public void testParse() throws ParseException {
		ISO8601Date.fromISO8601DateString("2007-04-05-05:0");
	}

	@Test(expected = ParseException.class)
	public void testParse2() throws ParseException {
		ISO8601Date.fromISO8601DateString("2007-04-05 05:00");
	}

	@Test(expected = ParseException.class)
	public void testParse3() throws ParseException {
		ISO8601Date.fromISO8601DateString("A007-04-05 05:00");
	}

	@Test(expected = ParseException.class)
	public void testParse4() throws ParseException {
		ISO8601Date.fromISO8601DateString("2007-32-05");
	}

	@Test(expected = ParseException.class)
	public void testParse5() throws ParseException {
		ISO8601Date.fromISO8601DateString("2007-04-66");
	}

	@Test(expected = ParseException.class)
	public void testParse6() throws ParseException {
		ISO8601Date.fromISO8601DateString("207-04-66");
	}

	@Test(expected = ParseException.class)
	public void testParse7() throws ParseException {
		ISO8601Date.fromISO8601DateString("2007-00-05");
	}

	@Test(expected = ParseException.class)
	public void testParse8() throws ParseException {
		ISO8601Date.fromISO8601DateString("2007-04-00");
	}

}
