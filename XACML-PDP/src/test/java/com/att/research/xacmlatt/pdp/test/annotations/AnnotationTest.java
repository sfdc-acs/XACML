/*
 *
 *          Copyright (c) 2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacmlatt.pdp.test.annotations;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import com.att.research.xacml.util.FactoryException;
import com.att.research.xacml.test.TestBase.HelpException;

public class AnnotationTest {

	@Test
	public void testAnnotations() throws MalformedURLException, IOException, FactoryException, ParseException, HelpException {
		String [] args = new String[] {"-dir", "src/test/resources/testsets/annotation"};
		new TestAnnotation(args).run();
	}
	

}
