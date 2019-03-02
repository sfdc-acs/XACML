/*
 *
 *          Copyright (c) 2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.std.json;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import com.att.research.xacml.api.Attribute;
import com.att.research.xacml.api.Request;
import com.att.research.xacml.api.RequestAttributes;
import com.att.research.xacml.api.RequestReference;
import com.att.research.xacml.api.XACML1;

public class JSONRequestTest {

	@Test
	public void test81() throws Exception {
		File file = new File("src/test/resources/Request-8.1.json");
		Request request = JSONRequest.load(file);
		
		Collection<RequestAttributes> requestAttributes = request.getRequestAttributes();
		for (RequestAttributes requestAttribute : requestAttributes) {
			System.out.println(requestAttribute);
		}
		
		Collection<RequestReference> multiRequests = request.getMultiRequests();
		assertNotNull(multiRequests);
		assertTrue(multiRequests.isEmpty());

		Iterator<RequestAttributes> iterator = request.getRequestAttributes(XACML1.ID_SUBJECT_CATEGORY_ACCESS_SUBJECT);
		assertNotNull(iterator);
		while (iterator.hasNext()) {
			RequestAttributes attributes = iterator.next();
			System.out.println(attributes);
			assertEquals(XACML1.ID_SUBJECT_CATEGORY_ACCESS_SUBJECT, attributes.getCategory());
			for (Attribute attribute : attributes.getAttributes()) {
				assertEquals(XACML1.ID_SUBJECT_CATEGORY_ACCESS_SUBJECT, attribute.getCategory());
				if (attribute.getAttributeId().stringValue().equals("subject-id")) {
					System.out.println("found subject-id");
				} else if (attribute.getAttributeId().stringValue().equals("location")) {
					System.out.println("found location");
				} else {
					fail("Did not parse valid subject attribute id: " + attribute.getAttributeId());
				}
			}
		}
		
		String json = JSONRequest.toString(request);
		
		System.out.println(json);
		
	}

	@Test
	public void test83() throws Exception {
		File file = new File("src/test/resources/Request-8.3.json");
		Request request = JSONRequest.load(file);
		
		Collection<RequestAttributes> requestAttributes = request.getRequestAttributes();
		for (RequestAttributes requestAttribute : requestAttributes) {
			System.out.println(requestAttribute);
		}
		
		Collection<RequestReference> multiRequests = request.getMultiRequests();
		assertNotNull(multiRequests);
		assertFalse(multiRequests.isEmpty());
		
		Iterator<RequestAttributes> iterator = request.getRequestAttributes(XACML1.ID_SUBJECT_CATEGORY_ACCESS_SUBJECT);
		assertNotNull(iterator);
		while (iterator.hasNext()) {
			RequestAttributes attributes = iterator.next();
			System.out.println(attributes);
			assertEquals(XACML1.ID_SUBJECT_CATEGORY_ACCESS_SUBJECT, attributes.getCategory());
			for (Attribute attribute : attributes.getAttributes()) {
				assertEquals(XACML1.ID_SUBJECT_CATEGORY_ACCESS_SUBJECT, attribute.getCategory());
				if (attribute.getAttributeId().stringValue().equals("com.acme.user.employeeId")) {
					System.out.println("found com.acme.user.employeeId");
				} else {
					fail("Did not parse valid subject attribute id: " + attribute.getAttributeId());
				}
			}
		}
		
		String json = JSONRequest.toString(request);
		
		System.out.println(json);
		
	}

}
