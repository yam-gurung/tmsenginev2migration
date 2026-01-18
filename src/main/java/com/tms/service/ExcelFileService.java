package com.tms.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.tms.model.Timesheet;
import com.tms.user.SecurityUser;

@Service
public class ExcelFileService implements FileService {

	@Override
	public void generateAndSaveTimesheetReportExcelFile(String targetLocation, List<Timesheet> timesheets)
			throws FileNotFoundException, IOException {

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Timesheets");
		String headers[] = { "username", "project", "loginDate", "loggedHr" };
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		int rowNum = 1;
		for (Timesheet timesheet : timesheets) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(timesheet.getUsername());
			row.createCell(1).setCellValue(timesheet.getProject());
			row.createCell(2).setCellValue(formatter.format(timesheet.getLoginDate()));
			row.createCell(3).setCellValue(timesheet.getLoggedHr());
		}

		Path path = Paths.get(targetLocation, "timesheets_" + LocalDate.now() + ".xlsx");
		String fileLocation = path.toAbsolutePath().toString();

		try (FileOutputStream outputStream = new FileOutputStream(fileLocation)) {
			workbook.write(outputStream);
			System.out.println("Excel file successfully saved to: " + fileLocation);
		} finally {
			workbook.close();
		}

	}

	@Override
	public void generateAndSaveUserReportExcelFile(String targetLocation, List<SecurityUser> users)
			throws FileNotFoundException, IOException {
		Workbook workbook=new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Users");
		String[] headers= {"username","firstname","lastname","rolename"};
		Row headerRow = sheet.createRow(0);
		
		for(int i=0;i<headers.length;i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}
		
		int rowNum = 1;
		for(SecurityUser user:users) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(user.getUsername());
			row.createCell(1).setCellValue(user.getFirstName());
			row.createCell(2).setCellValue(user.getLastName());
			row.createCell(3).setCellValue(user.getRoles().get(0).getRoleName());
		}
		
		Path path = Paths.get(targetLocation,"Users_"+LocalDate.now()+".xlsx");
		String fileLocation = path.toAbsolutePath().toString();
		
		try(FileOutputStream fileOutputStream = new FileOutputStream(fileLocation)){
			workbook.write(fileOutputStream);
			System.out.println("User Report Excel file successfully saved to: " + fileLocation);
		}finally {
			workbook.close();
		}
		
	}
}
