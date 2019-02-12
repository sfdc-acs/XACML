/*
 *
 *          Copyright (c) 2018 AT&T Knowledge Ventures
 *              Unpublished and Not for Publication
 *                     All Rights Reserved
 */
package com.att.research.xacml.std;

import static org.junit.Assert.*;
import java.net.URI;
import org.junit.Test;
import com.att.research.xacml.api.Identifier;

public class IdentifierImplTest {

  @Test
  public void testHashCode() {
    Identifier id = new IdentifierImpl("com:test");
    assertTrue(id.hashCode() != 0);
  }

  public void testIdentifierImplURI() {
    URI uri = URI.create("com:test");
    Identifier id = new IdentifierImpl(uri);
    assertEquals(id.getUri(), uri);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIdentifierImplURINull() {
    URI uri = null;
    new IdentifierImpl(uri);
  }

  @Test
  public void testIdentifierImplString() {
    URI uri = URI.create("com:test");
    Identifier id = new IdentifierImpl(uri.toString());
    assertEquals(uri.toString(), id.toString());
  }

  @Test
  public void testIdentifierImplIdentifierString() {
    URI uri = URI.create("com:test");
    Identifier id1 = new IdentifierImpl(uri);
    Identifier id = new IdentifierImpl(id1, "junit");
    assertEquals("com:test:junit", id.toString());
  }

  @Test
  public void testGensymString() {
    Identifier id = IdentifierImpl.gensym("test");
    assertTrue(id.toString().startsWith("test"));
  }

  @Test
  public void testGensym() {
    assertNotNull(IdentifierImpl.gensym());
  }

  @Test
  public void testGetUri() {
    URI uri = URI.create("com:test");
    Identifier id1 = new IdentifierImpl(uri);
    assertEquals(id1.getUri(), uri);
  }

  @Test
  public void testToString() {
    URI uri = URI.create("com:test");
    Identifier id1 = new IdentifierImpl(uri);
    assertTrue(id1.toString().startsWith("com:test"));
  }

  @Test
  public void testEqualsObject() {
    URI uri = URI.create("com:test");
    Identifier id1 = new IdentifierImpl(uri);
    Identifier id2 = new IdentifierImpl("com:test2");
    assertTrue(id1.equals(id1));
    assertFalse(id1.equals(id2));
    assertFalse(id1.equals(new Object()));
    Object foo = null;
    assertFalse(id1.equals(foo));
  }
}
