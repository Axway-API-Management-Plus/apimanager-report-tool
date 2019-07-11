package com.axway.apim.report.formats;

import com.axway.apim.lib.AppException;
import com.axway.apim.report.beans.APIManagerExportMetadata;
import com.axway.apim.swagger.APIManagerAdapter;

public interface IReportFormat {
	public void setMetaData(APIManagerExportMetadata metaData);
	
	public void setMgrAdapater(APIManagerAdapter mgrAdapater);
	
	public void preProcessMetadata() throws AppException;
	
	public void exportMetadata() throws AppException;

}
