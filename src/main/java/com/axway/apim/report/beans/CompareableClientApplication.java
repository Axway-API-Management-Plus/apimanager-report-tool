package com.axway.apim.report.beans;

import java.util.List;

import com.axway.apim.swagger.api.properties.apiAccess.APIAccess;
import com.axway.apim.swagger.api.properties.applications.ClientApplication;

public class MetadataClientApplication {
	private ClientApplication clientApp;
	
	private List<APIAccess> subscribedAPIs;
	
}
