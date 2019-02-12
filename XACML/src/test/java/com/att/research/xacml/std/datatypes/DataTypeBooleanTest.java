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

public class DataTypeBooleanTest {

  @Test
  public void test() throws DataTypeException {
    DataTypeBoolean b = DataTypeBoolean.newInstance();
    assertNotNull(b);
    Boolean bool = b.convert("true");
    assertTrue(bool.booleanValue() == Boolean.TRUE);
    bool = b.convert("false");
    assertTrue(bool.booleanValue() == Boolean.FALSE);
    assertNull(b.convert(null));
    bool = null;
    assertNull(b.convert(bool));
    bool = b.convert(1);
    assertTrue(bool.booleanValue() == Boolean.TRUE);
    bool = b.convert(0);
    assertTrue(bool.booleanValue() == Boolean.FALSE);
    bool = b.convert("1");
    assertTrue(bool.booleanValue() == Boolean.TRUE);
    bool = b.convert("0");
    assertTrue(bool.booleanValue() == Boolean.FALSE);
    DataTypeTestObject obj = new DataTypeTestObject();
    assertNull(b.convert(obj));
    bool = b.convert(Boolean.FALSE);
    assertTrue(bool.booleanValue() == Boolean.FALSE);
  }
  
  @Test(expected = DataTypeException.class)
  public void test2() throws DataTypeException {
    DataTypeBoolean b = DataTypeBoolean.newInstance();
    b.convert(10);
  }

  @Test(expected = DataTypeException.class)
  public void test3() throws DataTypeException {
    DataTypeBoolean b = DataTypeBoolean.newInstance();
    b.convert("null");
  }
}
