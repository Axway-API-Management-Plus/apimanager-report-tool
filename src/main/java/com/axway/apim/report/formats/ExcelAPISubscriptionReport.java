package com.axway.apim.report.formats;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

public class ExcelAPISubscriptionReport extends CSVAPISubscriptionReport {
	private static Logger LOG = LoggerFactory.getLogger(ExcelAPISubscriptionReport.class);
	
	@Override
	public void exportMetadata() throws AppException {
		String filename = CommandParameters.getInstance().getValue("filename");
		
		if(!filename.endsWith(".xlsx")) {
			filename = filename + ".xlsx";
		}
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("API Subscriptions");
		
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("API Organization");
        cell = row.createCell(1);
        cell.setCellValue("API Name");
        cell = row.createCell(2);
        cell.setCellValue("API Version");
        cell = row.createCell(3);
        cell.setCellValue("API Status");
        cell = row.createCell(4);
        cell.setCellValue("Application organization");
        cell = row.createCell(5);
        cell.setCellValue("Application name");
        
        createExcelReport(sheet);
        try {
        	File targetFile = new File(filename);
        	FileOutputStream outputStream = new FileOutputStream(targetFile);
        	workbook.write(outputStream);
        	LOG.info("API Subscription report exported into file: '"+targetFile.getCanonicalPath()+"'");
		} catch (IOException e) {
			throw new AppException("Cant open Excel-File for writing", ErrorCode.UNXPECTED_ERROR);
		} finally {
			 try {
				workbook.close();
			} catch (IOException ignore) {}
		}
	}
	
	private void createExcelReport(XSSFSheet sheet) throws AppException {
		int rowNum = 1;
		Cell cell;
		metaData.getAllAPIs().sort(new APIComparator());
		for(IAPI api : metaData.getAllAPIs()) {
			if(api.getApplications()==null) {
				Row row = sheet.createRow(rowNum++);
				cell = row.createCell(0);
				cell.setCellValue(APIManagerAdapter.getInstance().getOrg(api.getOrganizationId()).getName());
				cell = row.createCell(1);
				cell.setCellValue(api.getName());
				cell = row.createCell(2);
				cell.setCellValue(api.getVersion());
				cell = row.createCell(3);
				cell.setCellValue(api.getState());
				cell = row.createCell(4);
				cell.setCellValue("Not subscribed!");
				cell = row.createCell(5);
				cell.setCellValue("Not subscribed!");
			} else {
				api.getApplications().sort(new ClientAppComparator());
				for(ClientApplication app : api.getApplications()) {
					Row row = sheet.createRow(rowNum++);
					cell = row.createCell(0);
					cell.setCellValue(APIManagerAdapter.getInstance().getOrg(api.getOrganizationId()).getName());
					cell = row.createCell(1);
					cell.setCellValue(api.getName());
					cell = row.createCell(2);
					cell.setCellValue(api.getVersion());
					cell = row.createCell(3);
					cell.setCellValue(api.getState());
					cell = row.createCell(4);
					cell.setCellValue(APIManagerAdapter.getInstance().getOrg(app.getOrganizationId()).getName());
					cell = row.createCell(5);
					cell.setCellValue(app.getName());
				}
			}
		}
	}
}
