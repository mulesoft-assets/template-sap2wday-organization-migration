/**
 * Mule Anypoint Template
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.registry.RegistrationException;
import org.mule.construct.Flow;
import org.mule.context.notification.NotificationException;
import org.mule.processor.chain.SubflowInterceptingChainLifecycleWrapper;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.NullPayload;

import com.mulesoft.module.batch.BatchTestHelper;
import com.workday.hr.OrganizationType;

/**
 * The objective of this class is to validate the correct behavior of the flows
 * for this Anypoint Tempalte that make calls to external systems.
 * 
 */
public class BusinessLogicIT extends AbstractTemplateTestCase {
	
	protected static final int TIMEOUT_SEC = 600;
	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private static final String SAP_INPUT_FILE = "./src/test/resources/sap-export.xml";
	private static final String ORG_NAME = "ORG_NAME";
	private static final String ORG_CODE = "ORG_CODE";
	private Flow mainFlow;
	private String orgName;
	private String orgCode;
	private BatchTestHelper helper;
	
	@Before
	public void setUp() throws RegistrationException, NotificationException {
		helper = new BatchTestHelper(muleContext);
		
		final Properties props = new Properties();
    	try {
    		props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
    	} catch (Exception e) {
    	   logger.error("Error occured while reading mule.test.properties", e);
    	}
    	
    	mainFlow =  (Flow) muleContext.getRegistry().lookupObject("mainFlow");
	}
		
	@Test
	public void testMainFlow() throws Exception {
		final MuleEvent testEvent = getTestEvent(null, mainFlow);
		testEvent.getMessage().setPayload(buildIDocRequest(), DataTypeFactory.create(InputStream.class, "application/xml"));
		mainFlow.process(testEvent);

		// Wait for the batch job executed by the poll flow to finish
		helper.awaitJobTermination(TIMEOUT_SEC * 1000, 500);
		helper.assertJobWasSuccessful();
		
		SubflowInterceptingChainLifecycleWrapper flow = getSubFlow("getOrganization");
		MuleEvent result = flow.process(getTestEvent(null, MessageExchangePattern.REQUEST_RESPONSE));
		assertNotNull(result);
		assertFalse(result.getMessage().getPayload() instanceof NullPayload);
		OrganizationType org = (OrganizationType) result.getMessage().getPayload();		
		assertEquals("Workday organization name should be synced.", orgName, org.getOrganizationData().getOrganizationName());
		assertEquals("Workday organization code should be synced.", orgCode, org.getOrganizationData().getOrganizationCode());
	}

	private String buildIDocRequest() throws MuleException, Exception {
		String xml = org.apache.commons.io.IOUtils.toString(new FileInputStream(new File(SAP_INPUT_FILE)));
		orgName = "orgMigrationName" + System.currentTimeMillis();
		orgCode = "orgMigrationCode" + System.currentTimeMillis();
			
		return xml.replace(ORG_NAME, orgName).replace(ORG_CODE, orgCode);				
	}
}
