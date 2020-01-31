/*
 *
 *          Copyright (c) 2013,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacmlatt.pdp.policy.dom;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.att.research.xacml.api.XACML3;
import com.att.research.xacml.std.dom.DOMUtil;

/**
 * DOMPolicyRepair is an application for reading a XACML Policy or PolicySet document and ensuring it has the required attributes and then writing
 * the repaired Policy or PolicySet to an output file.
 * 
 * @author car
 * @version $Revision: 1.1 $
 */
public class DOMPolicyRepair {
	private static final String	DEFAULT_VERSION	= "1.0";
	
	public static void main(String[] args) {
		InputStream	inputStream		= System.in;
		OutputStream outputStream	= System.out;
		
		for (int i = 0 ; i < args.length ; ) {
			if (args[i].equals("-i")) {
				if (i+1 < args.length) {
					try {
						inputStream	= new FileInputStream(args[i+1]);
					} catch (IOException ex) {
						System.err.println("IOException opening \"" + args[i+1] + "\" for reading.");
						System.exit(1);
					}
					i	+= 2;
				} else {
					i++;
				}
			} else if (args[i].equals("-o")) {
				if (i+1 < args.length){
					try {
						outputStream = new FileOutputStream(args[i+1]);
					} catch (IOException ex) {
						System.err.println("IOException opening \"" + args[i+1] + "\" for writing.");
						ex.printStackTrace(System.err);
						System.exit(1);;
					}
					i	+= 2;
				} else {
					i++;
				}
			} else {
				System.err.println("Unrecognized command line option \"" + args[i] + "\"");
				System.exit(1);
			}
		}
		
		/*
		 * Get the XML Parser for the input file
		 */
		DocumentBuilderFactory documentBuilderFactory	= DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		try {
			DocumentBuilder documentBuilder				= documentBuilderFactory.newDocumentBuilder();
			Document documentInput						= documentBuilder.parse(inputStream);
			Element elementRoot							= DOMUtil.getFirstChildElement(documentInput);
			if (elementRoot == null) {
				System.err.println("No root element");
				System.exit(1);
			} else if (!XACML3.ELEMENT_POLICY.equals(elementRoot.getLocalName()) && !XACML3.ELEMENT_POLICYSET.equals(elementRoot.getLocalName())) {
				System.err.println("Root element is not a Policy or PolicySet");
				System.exit(1);
			}
			
			/*
			 * Make sure there is a Version attribute
			 */
			Node nodeVersion	= DOMUtil.getAttribute(elementRoot, XACML3.ATTRIBUTE_VERSION);
			if (nodeVersion == null) {
				System.out.println("Adding Version attribute with value \"" + DEFAULT_VERSION + "\"");
				elementRoot.setAttribute(XACML3.ATTRIBUTE_VERSION, DEFAULT_VERSION);
			}
			
			/*
			 * Write out the updated document
			 */
			String newDocument	= DOMUtil.toString(documentInput);
			outputStream.write(newDocument.getBytes());
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(1);
		}
		System.exit(0);
	}

}
