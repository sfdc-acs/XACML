/*
 *
 *          Copyright (c) 2018 AT&T Knowledge Ventures
 *              Unpublished and Not for Publication
 *                     All Rights Reserved
 */

package com.att.research.xacml.std.datatypes;

import static org.junit.Assert.*;

import org.junit.Test;

import com.att.research.xacml.api.DataTypeException;

public class DataTypeHexBinaryTest {

	@Test
	public void test() throws DataTypeException {
		DataTypeHexBinary dtHex = DataTypeHexBinary.newInstance();
		assertNull(dtHex.convert(null));
		HexBinary hex = dtHex.convert(new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A});
		assertNotNull(hex);
		assertTrue(hex.getData()[10] == 0x0A);
		hex = dtHex.convert("000102030405060708090a");
		assertTrue(hex.getData()[10] == 0x0A);
	}

	@Test(expected = DataTypeException.class)
	public void testParse00() throws DataTypeException {
		DataTypeHexBinary.newInstance().convert("##$#$#$");
	}
}
