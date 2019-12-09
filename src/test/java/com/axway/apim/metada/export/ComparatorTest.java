package com.axway.apim.metada.export;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.axway.apim.lib.AppException;
import com.axway.apim.report.utils.APIComparator;
import com.axway.apim.report.utils.ClientAppComparator;
import com.axway.apim.swagger.api.properties.applications.ClientApplication;
import com.axway.apim.swagger.api.state.AbstractAPI;
import com.axway.apim.swagger.api.state.ActualAPI;
import com.axway.apim.swagger.api.state.IAPI;

public class ComparatorTest {
	@Test
	public void threeAppsToCompare() {
		List<ClientApplication> apps = new ArrayList<ClientApplication>();
		ClientApplication app1 = new ClientApplication();
		app1.setName("App ABC");
		ClientApplication app2 = new ClientApplication();
		app2.setName("App XYZ");
		ClientApplication app3 = new ClientApplication();
		app3.setName("ABC App");

		apps.add(app1);
		apps.add(app2);
		apps.add(app3);

		apps.sort(new ClientAppComparator());
		Assert.assertEquals(apps.get(0).getName(), "ABC App");
		Assert.assertEquals(apps.get(1).getName(), "App ABC");
		Assert.assertEquals(apps.get(2).getName(), "App XYZ");
	}

	@Test
	public void threeAPIsToCompare() throws AppException {
		List<IAPI> apis = new ArrayList<IAPI>();
		AbstractAPI api1 = new ActualAPI();
		api1.setName("API ABC");
		AbstractAPI api2 = new ActualAPI();
		api2.setName("API XYZ");
		AbstractAPI api3 = new ActualAPI();
		api3.setName("ABC API");

		apis.add((IAPI)api1);
		apis.add((IAPI)api2);
		apis.add((IAPI)api3);

		apis.sort(new APIComparator());
		Assert.assertEquals(apis.get(0).getName(), "ABC API");
		Assert.assertEquals(apis.get(1).getName(), "API ABC");
		Assert.assertEquals(apis.get(2).getName(), "API XYZ");
	}
}
