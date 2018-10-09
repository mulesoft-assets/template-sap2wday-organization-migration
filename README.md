
# Anypoint Template: SAP to Workday Organization Migration

# License Agreement
This template is subject to the conditions of the 
<a href="https://s3.amazonaws.com/templates-examples/AnypointTemplateLicense.pdf">MuleSoft License Agreement</a>.
Review the terms of the license before downloading and using this template. You can use this template for free 
with the Mule Enterprise Edition, CloudHub, or as a trial in Anypoint Studio.

# Use Case
As a SAP admin I want to migrate organizations to Workday instance.

This Template should serve as a foundation for the process of migrating organizations from SAP instance to Workday one, being able to match organizations by Integration ID. 

As implemented, this Template leverages the [Batch Module](http://www.mulesoft.org/documentation/display/current/Batch+Processing).
The batch job is divided in Process and On Complete stages.

During the Input stage the Template will go to the SAP system and query all the existing Organizations that match the filtering criteria.
The last step of the Process stage will insert or update organizations in Workday, added under a specified organization in the Workday organization hierarchy.

Finally during the On Complete stage the Template will both output statistics data into the console and send a notification email with the results of the batch execution.

# Considerations

To make this Anypoint Template run, there are certain preconditions that must be considered. All of them deal with the preparations in both, that must be made in order for all to run smoothly. **Failing to do so could lead to unexpected behavior of the template.**
Before using this Anypoint Template, you may want to check out this [Documentation Page](http://www.mulesoft.org/documentation/display/current/SAP+Connector#SAPConnector-EnablingYourStudioProjectforSAP), that will teach you how to work 
with SAP and Anypoint Studio.

## Disclaimer
This Anypoint template uses a few private Maven dependencies from Mulesoft in order to work. If you intend to run this template with Maven support, you need to add three extra dependencies for SAP to the pom.xml file.


## SAP Considerations

Here's what you need to know to get this template to work with SAP.

### As a Data Source

The SAP backend system is used as a source of data. The SAP connector is used to send and receive the data from the SAP backend. 
The connector can either use RFC calls of BAPI functions and/or IDoc messages for data exchange, and needs to be properly customized per the "Properties to Configure" section.






## Workday Considerations


### As a Data Destination

There are no considerations with using Workday as a data destination.






# Run it!
Simple steps to get SAP to Workday Organization Migration running.
In any of the ways you would like to run this Template this is an example of the output you'll see after hitting the HTTP endpoint:

{
    "Message": "Batch Process initiated",
    "ID": "e9354ac0-a9ec-11e8-ae2c-2ac63fa6f77a",
    "RecordCount": 3,
    "StartExecutionOn": "2018-08-27T13:32:42Z"
}

## Running On Premises
Fill in all the properties in one of the property files, for example in [mule.prod.properties](../blob/master/src/main/resources/mule.prod.properties) and run your app with the corresponding environment variable to use it. To follow the example, this will be `mule.env=prod`.
After this, to trigger the use case you just need to hit the local http endpoint with the port you configured in your file. If this is, for instance, `9090` then you should hit: `http://localhost:9090/migrateorganizations` and this will create a CSV report and send it to the mails set.


### Where to Download Anypoint Studio and the Mule Runtime
If you are a newcomer to Mule, here is where to get the tools.

+ [Download Anypoint Studio](https://www.mulesoft.com/platform/studio)
+ [Download Mule runtime](https://www.mulesoft.com/lp/dl/mule-esb-enterprise)


### Importing a Template into Studio
In Studio, click the Exchange X icon in the upper left of the taskbar, log in with your
Anypoint Platform credentials, search for the template, and click **Open**.


### Running on Studio
After you import your template into Anypoint Studio, follow these steps to run it:

+ Locate the properties file `mule.dev.properties`, in src/main/resources.
+ Complete all the properties required as per the examples in the "Properties to Configure" section.
+ Right click the template project folder.
+ Hover your mouse over `Run as`
+ Click `Mule Application (configure)`
+ Inside the dialog, select Environment and set the variable `mule.env` to the value `dev`
+ Click `Run`


### Running on Mule Standalone
Complete all properties in one of the property files, for example in mule.prod.properties and run your app with the corresponding environment variable. To follow the example, this is `mule.env=prod`. 


## Running on CloudHub
While creating your application on CloudHub (or you can do it later as a next step), go to Runtime Manager > Manage Application > Properties to set the environment variables listed in "Properties to Configure" as well as the **mule.env**.
Once your app is all set and started, supposing you choose as domain name `sapwdayorganizationmigration` to trigger the use case you just need to hit `http://sapwdayorganizationmigration.cloudhub.io/migrateorganizations` and report will be sent to the email configured.

### Deploying your Anypoint Template on CloudHub
Studio provides an easy way to deploy your template directly to CloudHub, for the specific steps to do so check this


## Properties to Configure
To use this template, configure properties (credentials, configurations, etc.) in the properties file or in CloudHub from Runtime Manager > Manage Application > Properties. The sections that follow list example values.
### Application Configuration
+ http.port `9090`
+ page.size `100` 

**SAP Connector configuration**

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

**WorkDay Connector configuration**

+ wday.username `user1@mulesoft_pt1`
+ wday.password `ExamplePassword565`
+ wday.tenant `tenant_example`
+ wday.host `example.host.com`
+ wday.org.subtype `Company`
+ wday.org.visibility `Everyone`
+ wday.ext.systemID `SAP sync`
 
**SMTP Services configuration**

+ smtp.host `smtp.gmail.com`
+ smtp.port `465`
+ smtp.user `user@gmail.com`
+ smtp.password `gmailpassword`

**Email Details**

+ mail.from `organizations.report%40mulesoft.com`
+ mail.to `user@mulesoft.com`
+ mail.subject `Organization Migration Report`

# API Calls
There are no particular considerations for this Anypoint Template regarding API calls.


# Customize It!
This brief guide intends to give a high level idea of how this template is built and how you can change it according to your needs.
As Mule applications are based on XML files, this page describes the XML files used with this template.

More files are available such as test classes and Mule application files, but to keep it simple, we focus on these XML files:

* config.xml
* businessLogic.xml
* endpoints.xml
* errorHandling.xml


## config.xml
Configuration for connectors and configuration properties are set in this file. Even change the configuration here, all parameters that can be modified are in properties file, which is the recommended place to make your changes. However if you want to do core changes to the logic, you need to modify this file.

In the Studio visual editor, the properties are on the *Global Element* tab.


## businessLogic.xml
Functional aspect of the Template is implemented on this XML, directed by one flow responsible of excecuting the logic.
For the pourpose of this particular Template the *mainFlow* just excecutes a [Batch Job](http://www.mulesoft.org/documentation/display/current/Batch+Processing). which handles all the logic of it.



## endpoints.xml
This is the file where you will find the inbound and outbound sides of your integration app.
This Template has only an [HTTP Listener Connector](http://www.mulesoft.org/documentation/display/current/HTTP+Listener+Connector) as the way to trigger the use case.

**HTTP Listener Connector** - Start Report Generation

+ `${http.port}` is set as a property to be defined either on a property file or in CloudHub environment variables.
+ The path configured by default is `migrateorganizations` and you are free to change for the one you prefer.
+ The host name for all endpoints in your CloudHub configuration should be defined as `localhost`. CloudHub will then route requests from your application domain URL to the endpoint.
+ The endpoint is a *request-response* since as a result of calling it the response will be the total of Organizations synced and filtered by the criteria specified.



## errorHandling.xml
This is the right place to handle how your integration reacts depending on the different exceptions. 
This file provides error handling that is referenced by the main flow in the business logic.




