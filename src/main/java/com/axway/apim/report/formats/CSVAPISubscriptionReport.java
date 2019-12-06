package com.axway.apim.report.formats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axway.apim.lib.AppException;
import com.axway.apim.lib.CommandParameters;
import com.axway.apim.lib.ErrorCode;
import com.axway.apim.report.utils.APIComparator;
import com.axway.apim.report.utils.ClientAppComparator;
import com.axway.apim.swagger.APIManagerAdapter;
import com.axway.apim.swagger.api.properties.applications.ClientApplication;
import com.axway.apim.swagger.api.state.IAPI;

public class CSVAPISubscriptionReport extends AbstractReportFormat implements IReportFormat {
	private static Logger LOG = LoggerFactory.getLogger(CSVAPISubscriptionReport.class);
	
	protected Map<String, List<ClientApplication>> subscribedApplications = new HashMap<String, List<ClientApplication>>();
	
	@Override
	public void preProcessMetadata() throws AppException {
		// We need to know the API-Access for each Application
		initApplicationAPISubcription();
		LOG.info("Successfully loaded application subscriptions.");
	}
	
	@Override
	public void exportMetadata() throws AppException {
		String filename = CommandParameters.getInstance().getValue("filename");
		Appendable appendable;
		CSVPrinter csvPrinter = null;
		try {
			File cvsFile = new File(filename);
			if(cvsFile.exists()) {
				LOG.info("Going to overwrite existing file: '"+cvsFile.getCanonicalPath()+"'");
			}
			appendable = new FileWriter(cvsFile);
			csvPrinter = new CSVPrinter(appendable, CSVFormat.DEFAULT.withHeader(
				"APIOrgName", 
				"APIName", 
				"APIVersion", 
				"APIStatus",  
				//"APIId", 
				"ApplicationOrganization", 
				"ApplicationName"
			));
			writeAPIUsageLineToCSV(csvPrinter);
			LOG.info("API Subscription report exported into file: '"+cvsFile.getCanonicalPath()+"'");
		} catch (IOException e) {
			throw new AppException("Cant open CSV-File for writing", ErrorCode.UNXPECTED_ERROR);
		} finally {
			if(csvPrinter!=null)
				try {
					csvPrinter.close(true);
				} catch (Exception ignore) {
					throw new AppException("Unable to close CSVWriter", ErrorCode.UNXPECTED_ERROR, ignore);
				}
		}
	}
	
	private void writeAPIUsageLineToCSV(CSVPrinter csvPrinter) throws IOException, AppException {
		int i=0;
		metaData.getAllAPIs().sort(new APIComparator());
		for(IAPI api : metaData.getAllAPIs()) {
			if(api.getApplications()==null) {
				csvPrinter.printRecord(
						APIManagerAdapter.getInstance().getOrg(api.getOrganizationId()).getName(), 
						api.getName(), 
						api.getVersion(), 
						api.getState(), 
						"Not subscribed!", 
						"Not subscribed!"
				);
			} else {
				api.getApplications().sort(new ClientAppComparator());
				for(ClientApplication app : api.getApplications()) {
					i++;
					csvPrinter.printRecord(
							APIManagerAdapter.getInstance().getOrg(api.getOrganizationId()).getName(), 
							api.getName(), 
							api.getVersion(), 
							api.getState(), 
							APIManagerAdapter.getInstance().getOrg(app.getOrganizationId()).getName(), 
							app.getName()
					);
					if( i % 50 == 0 ){
						csvPrinter.flush();
					}
				}
			}
		}
		csvPrinter.flush();
	}
}
