/*
 *
 *          Copyright (c) 2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.std.pip.engines.csv;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import com.att.research.xacml.util.FactoryException;
import com.att.research.xacmlatt.pdp.test.TestBase;
import com.att.research.xacmlatt.pdp.test.TestBase.HelpException;

public class HyperCSVEngineTest {

	@Test
	public void test() throws MalformedURLException, IOException, FactoryException, ParseException, HelpException {
		String [] args = new String[] {"-dir", "src/test/resources/testsets/pip/configurable-csv-hyper"};
		new TestBase(args).run();
	}

}
