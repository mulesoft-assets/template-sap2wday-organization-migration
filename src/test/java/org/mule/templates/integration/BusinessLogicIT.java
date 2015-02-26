/**
 * Mule Anypoint Template
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleEvent;
import org.mule.api.registry.RegistrationException;
import org.mule.context.notification.NotificationException;
import org.mule.processor.chain.SubflowInterceptingChainLifecycleWrapper;
import org.mule.transport.NullPayload;

import com.mulesoft.module.batch.BatchTestHelper;
import com.workday.hr.ExternalIntegrationIDReferenceDataType;
import com.workday.hr.IDType;
import com.workday.hr.OrganizationGetType;
import com.workday.hr.OrganizationReferenceType;
import com.workday.hr.OrganizationType;

/**
 * The objective of this class is to validate the correct behavior of the flows
 * for this Anypoint Tempalte that make calls to external systems.
 * 
 */
public class BusinessLogicIT extends AbstractTemplateTestCase {

	private static String WDAY_SYSTEM_ID;
	private static String SAP_ORG_ID;
	private BatchTestHelper helper;
	protected static final int TIMEOUT_SEC = 600;
	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private String orgName;
	
	@Before
	public void setUp() throws RegistrationException, NotificationException {
		helper = new BatchTestHelper(muleContext);
		
		final Properties props = new Properties();
    	try {
    		props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
    	} catch (Exception e) {
    	   logger.error("Error occured while reading mule.test.properties", e);
    	} 
    	WDAY_SYSTEM_ID = props.getProperty("wday.ext.systemID");
    	SAP_ORG_ID = props.getProperty("sap.testorg.id");
    	
		updateSAPTestData();
	}

	private void updateSAPTestData() {
		orgName = TEMPLATE_NAME + System.currentTimeMillis();	
	}
		
	@Test
	public void testMainFlow() throws Exception {
		runFlow("mainFlow");

		// Wait for the batch job executed by the poll flow to finish
		helper.awaitJobTermination(TIMEOUT_SEC * 1000, 500);
		helper.assertJobWasSuccessful();
		
		SubflowInterceptingChainLifecycleWrapper flow = getSubFlow("getOrganization");
		MuleEvent result = flow.process(getTestEvent(prepareGet(), MessageExchangePattern.REQUEST_RESPONSE));
		assertNotNull(result);
		assertFalse(result.getMessage().getPayload() instanceof NullPayload);
		OrganizationType org = (OrganizationType) result.getMessage().getPayload();
		logger.info("wday org: " + org.getOrganizationData().getOrganizationName());
	}

	private OrganizationGetType prepareGet() {
		OrganizationGetType get = new OrganizationGetType();
		OrganizationReferenceType ref = new OrganizationReferenceType();
		ExternalIntegrationIDReferenceDataType extId = new ExternalIntegrationIDReferenceDataType();
		IDType id = new IDType();
		id.setSystemID(WDAY_SYSTEM_ID);
		id.setValue(SAP_ORG_ID);
		extId.setID(id );
		ref.setIntegrationIDReference(extId);
		get.setOrganizationReference(ref );
		return get ;
	}
	
	private static XMLGregorianCalendar xmlDate(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		gregorianCalendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}

}
