
# Anypoint Template: SAP to Workday Organization Migration	

<!-- Header (start) -->
Moves a large set of organizations from SAP to Workday. You can trigger this manually or programmatically with an HTTP call. Organizations are upserted so that the migration can be run multiple times without worrying about creating duplicates. This template uses batch to efficiently process many records at a time.

![d4742a57-80f3-493a-ad79-189e50faeb7d-image.png](https://exchange2-file-upload-service-kprod.s3.us-east-1.amazonaws.com:443/d4742a57-80f3-493a-ad79-189e50faeb7d-image.png)

## Workday Requirement

Install Workday HCM, the Human Resources module via the [Workday connector](https://www.mulesoft.com/exchange/com.mulesoft.connectors/mule-workday-connector/).

<!-- Header (end) -->

# License Agreement
This template is subject to the conditions of the <a href="https://s3.amazonaws.com/templates-examples/AnypointTemplateLicense.pdf">MuleSoft License Agreement</a>. Review the terms of the license before downloading and using this template. You can use this template for free with the Mule Enterprise Edition, CloudHub, or as a trial in Anypoint Studio. 
# Use Case
<!-- Use Case (start) -->
As a SAP admin I want to migrate organizations to a Workday instance.

This template serves as a foundation for the process of migrating organizations from SAP instance to Workday one, being able to match organizations by Integration ID. 

As implemented, this template leverages the Mule batch module. The batch job is divided into the Process and On Complete stages.

During the input stage the template goes to the SAP system and queries all the existing organizations that match the filtering criteria.

The last step of the Process stage inserts or updates organizations in Workday, added under a specified organization in the Workday organization hierarchy.

Finally during the On Complete stage the template outputs statistic data in the console and sends an email notification  with the results of the batch execution.
<!-- Use Case (end) -->

# Considerations
<!-- Default Considerations (start) --><!-- Default Considerations (end) --><!-- Considerations (start) -->
To make this template run, there are certain preconditions that must be considered that deal with the preparations in both, that must be made for the template to run smoothly. Failing to do so could lead to unexpected behavior of the template. Before using this template, see [Install the SAP Connector in Studio](https://docs.mulesoft.com/connectors/sap/sap-connector#install-the-sap-connector-in-studio), for installing the SAP connector in Anypoint Studio.

## Disclaimer
This template uses a few private Maven dependencies from Mulesoft to work. If you intend to run this template with Maven support, you need to add extra dependencies for SAP to the pom.xml file.
<!-- Considerations (end) -->

## SAP Considerations

Here's what you need to know to get this template to work with SAP.

### As a Data Source

The SAP backend system is used as a source of data. The SAP connector is used to send and receive the data from the SAP backend. The connector can either use RFC calls of BAPI functions and/or IDoc messages for data exchange, and needs to be properly customized per the "Properties to Configure" section.

## Workday Considerations

### As a Data Destination

There are no considerations with using Workday as a data destination.

# Run it!
Simple steps to get this template running.
<!-- Run it (start) -->
This is an example of the output you see after browsing to the HTTP endpoint:

{
    "Message": "Batch Process initiated",
    "ID": "e9354ac0-a9ec-11e8-ae2c-2ac63fa6f77a",
    "RecordCount": 3,
    "StartExecutionOn": "2018-08-27T13:32:42Z"
}
<!-- Run it (end) -->

## Running On Premises
Fill in all the properties in one of the property files, for example in mule.prod.properties and run your app with the corresponding environment variable to use it. To follow the example, use `mule.env=prod`.

After this, to trigger the use case you just need to browse to the local HTTP endpoint with the port you configured in your file. If the port is, for instance, `9090` then browse to: `http://localhost:9090/migrateorganizations`. Browsing to this endpoint causes the template to create a CSV report and send it to the email address you set.
<!-- Running on premise (start) -->

<!-- Running on premise (end) -->

### Where to Download Anypoint Studio and the Mule Runtime
If you are new to Mule, download this software:

+ [Download Anypoint Studio](https://www.mulesoft.com/platform/studio)
+ [Download Mule runtime](https://www.mulesoft.com/lp/dl/mule-esb-enterprise)

**Note:** Anypoint Studio requires JDK 8.
<!-- Where to download (start) -->

<!-- Where to download (end) -->

### Importing a Template into Studio
In Studio, click the Exchange X icon in the upper left of the taskbar, log in with your Anypoint Platform credentials, search for the template, and click Open.
<!-- Importing into Studio (start) -->

<!-- Importing into Studio (end) -->

### Running on Studio
After you import your template into Anypoint Studio, follow these steps to run it:

. Locate the properties file `mule.dev.properties`, in src/main/resources.
. Complete all the properties required as per the examples in the "Properties to Configure" section.
. Right click the template project folder.
. Hover your mouse over `Run as`.
. Click `Mule Application (configure)`.
. Inside the dialog, select Environment and set the variable `mule.env` to the value `dev`.
. Click `Run`.
<!-- Running on Studio (start) -->

<!-- Running on Studio (end) -->

### Running on Mule Standalone
Update the properties in one of the property files, for example in mule.prod.properties, and run your app with a corresponding environment variable. In this example, use `mule.env=prod`. 


## Running on CloudHub
When creating your application in CloudHub, go to Runtime Manager > Manage Application > Properties to set the environment variables listed in "Properties to Configure" as well as the mule.env value.
<!-- Running on Cloudhub (start) -->
Once your app is all set and started, if you choose as the domain name `sapwdayorganizationmigration` to trigger the use case, you just need to browse to `http://sapwdayorganizationmigration.cloudhub.io/migrateorganizations` and the output report is sent to the email address you configure.
<!-- Running on Cloudhub (end) -->

### Deploying a Template in CloudHub
In Studio, right click your project name in Package Explorer and select Anypoint Platform > Deploy on CloudHub.
<!-- Deploying on Cloudhub (start) -->

<!-- Deploying on Cloudhub (end) -->

## Properties to Configure
To use this template, configure properties such as credentials, configurations, etc.) in the properties file or in CloudHub from Runtime Manager > Manage Application > Properties. The sections that follow list example values.
### Application Configuration
<!-- Application Configuration (start) -->
+ http.port `9090`
+ page.size `100` 

**SAP Connector Configuration**

+ sap.jco.ashost `your.sap.address.com`
+ sap.jco.user `SAP_USER`
+ sap.jco.passwd `SAP_PASS`
+ sap.jco.sysnr `14`
+ sap.jco.client `800`
+ sap.jco.lang `EN`

**Date in 'YYYYMMDD' format, e.g. 20140101**
+ sap.startDate `20150101`
+ sap.endDate `20150901`
**Note**: properties *sap.startDate* and *sap.endDate* define a date range that is used for filtering SAP organizations. All organizations with a validity period overlaping with this date range are migrated. 

**WorkDay Connector Configuration**

+ wday.username `user1@mulesoft_pt1`
+ wday.password `ExamplePassword565`
+ wday.tenant `tenant_example`
+ wday.host `example.host.com`
+ wday.org.subtype `Company`
+ wday.org.visibility `Everyone`
+ wday.ext.systemID `SAP sync`
 
**SMTP Services Configuration**

+ smtp.host `smtp.gmail.com`
+ smtp.port `465`
+ smtp.user `user@gmail.com`
+ smtp.password `gmailpassword`

**Email Details**

+ mail.from `organizations.report%40mulesoft.com`
+ mail.to `user@mulesoft.com`
+ mail.subject `Organization Migration Report`
<!-- Application Configuration (end) -->

# API Calls
<!-- API Calls (start) -->
There are no particular considerations for this template regarding API calls.
<!-- API Calls (end) -->

# Customize It!
This brief guide provides a high level understanding of how this template is built and how you can change it according to your needs. As Mule applications are based on XML files, this page describes the XML files used with this template. More files are available such as test classes and Mule application files, but to keep it simple, we focus on these XML files:

* config.xml
* businessLogic.xml
* endpoints.xml
* errorHandling.xml
<!-- Customize it (start) -->

<!-- Customize it (end) -->

## config.xml
<!-- Default Config XML (start) -->
This file provides the configuration for connectors and configuration properties. Only change this file to make core changes to the connector processing logic. Otherwise, all parameters that can be modified should instead be in a properties file, which is the recommended place to make changes.
<!-- Default Config XML (end) -->

<!-- Config XML (start) -->

<!-- Config XML (end) -->

## businessLogic.xml
<!-- Default Business Logic XML (start) -->
Functional aspects of this template are implemented in this XML file, directed by a flow responsible for executing the logic. For the purpose of this template the *mainFlow* just excecutes a batch job, which handles all its logic.
<!-- Default Business Logic XML (end) -->

<!-- Business Logic XML (start) -->

<!-- Business Logic XML (end) -->

## endpoints.xml
<!-- Default Endpoints XML (start) -->
This is the file where you find the inbound and outbound sides of your integration app.
This template has only an HTTP Listener as the way to trigger the use case.

**HTTP Listener Connector** - Start Report Generation

+ `${http.port}` is set as a property to be defined either on a property file or in CloudHub environment variables.
+ The path configured by default is `migrateorganizations` and you are free to change for the one you prefer.
+ The host name for all endpoints in your CloudHub configuration should be defined as `localhost`. CloudHub then routes requests from your application domain URL to the endpoint.
+ The endpoint is a *request-response* as a result of calling it causes the response to be the total of organizations synced and filtered by the criteria specified.
<!-- Default Endpoints XML (end) -->

<!-- Endpoints XML (start) -->

<!-- Endpoints XML (end) -->

## errorHandling.xml
<!-- Default Error Handling XML (start) -->
This file handles how your integration reacts depending on the different exceptions. This file provides error handling that is referenced by the main flow in the business logic.
<!-- Default Error Handling XML (end) -->

<!-- Error Handling XML (start) -->

<!-- Error Handling XML (end) -->

<!-- Extras (start) -->

<!-- Extras (end) -->
