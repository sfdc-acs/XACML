/*
 *
 *          Copyright (c) 2020  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */

package com.att.research.xacml.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.att.research.xacml.util.XACMLProperties;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class XACMLRestTest {
    
    private static String saveProperties = null;
    
    @BeforeClass
    public static void saveEnvironment() {
        saveProperties = System.getProperty(XACMLProperties.XACML_PROPERTIES_NAME);
        System.clearProperty(XACMLProperties.XACML_PROPERTIES_NAME);
    }
    
    @AfterClass
    public static void restoreEnvironment() {
        if (saveProperties == null) {
            System.clearProperty(XACMLProperties.XACML_PROPERTIES_NAME);
        } else {
            System.setProperty(XACMLProperties.XACML_PROPERTIES_NAME, saveProperties);
        }
    }

    @Test
    public void testInitHasPropertiesSet() throws IOException {
        assertThat(System.getProperty(XACMLProperties.XACML_PROPERTIES_NAME)).isNullOrEmpty();
        
        Vector<String> params = new Vector<>(2);
        params.add("XACML_PROPERTIES_NAME");
        params.add("TEST");
        
        ServletConfig config = mock(ServletConfig.class);
        when(config.getInitParameter("XACML_PROPERTIES_NAME")).thenReturn("src/main/resources/xacml.pdp.properties");
        when(config.getInitParameter("TEST")).thenReturn("PASS");
        when (config.getInitParameterNames()).thenReturn(params.elements());
        
        assertThatCode(() -> XACMLRest.xacmlInit(config)).doesNotThrowAnyException();
        assertThat(System.getProperty(XACMLProperties.XACML_PROPERTIES_NAME)).isEqualTo("src/main/resources/xacml.pdp.properties");

        assertThatCode(() -> XACMLRest.loadXacmlProperties(null, null)).doesNotThrowAnyException();
        
        assertThat(XACMLProperties.getProperties().getProperty("TEST")).isEqualTo("PASS");
        
        //
        // Now reset everything to something else
        //
        System.setProperty(XACMLProperties.XACML_PROPERTIES_NAME, "src/test/resources/xacml.foo.properties");
        assertThatCode(() -> XACMLRest.xacmlInit(config)).doesNotThrowAnyException();
        assertThat(System.getProperty(XACMLProperties.XACML_PROPERTIES_NAME)).isEqualTo("src/test/resources/xacml.foo.properties");
        
        Properties propsPolicies = new Properties();
        propsPolicies.setProperty("POLICYA", "ISCALLEDA");
        
        Properties propsPips = new Properties();
        propsPips.setProperty("PIPA", "HASVALUEA");

        assertThatCode(() -> XACMLRest.loadXacmlProperties(propsPolicies, propsPips)).doesNotThrowAnyException();
        
        assertThat(XACMLProperties.getProperties().getProperty("TEST")).isEqualTo("PASS");
        assertThat(XACMLProperties.getProperties().getProperty("POLICYA")).isEqualTo("ISCALLEDA");
        assertThat(XACMLProperties.getProperties().getProperty("PIPA")).isEqualTo("HASVALUEA");
        
    }
    
    @Test
    public void testDumpRequest() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getMethod()).thenReturn("GET");
        
        Vector<String> headerNames = new Vector<>(2);
        headerNames.add("Content-Type");
        when (req.getHeaderNames()).thenReturn(headerNames.elements());

        Vector<String> headers = new Vector<>(2);
        headers.add("application/xml");
        when (req.getHeaders("Content-Type")).thenReturn(headers.elements());
        
        Vector<String> attributes = new Vector<>(1);
        attributes.add("attribute1");
        when (req.getAttributeNames()).thenReturn(attributes.elements());
        when (req.getAttribute("attribute1")).thenReturn("value1");
        
        assertThatCode(() -> XACMLRest.dumpRequest(req)).doesNotThrowAnyException();
        
        Map<String, String[]> params = new HashMap<>();
        String[] vals = new String[2];
        vals[0] = "v1";
        vals[1] = "v2";
        params.put("param1", vals);
        when (req.getParameter("type")).thenReturn("hb");
        when (req.getParameterMap()).thenReturn(params);
        assertThatCode(() -> XACMLRest.dumpRequest(req)).doesNotThrowAnyException();

        when(req.getMethod()).thenReturn("PUT");
        assertThatCode(() -> XACMLRest.dumpRequest(req)).doesNotThrowAnyException();
    }

}
