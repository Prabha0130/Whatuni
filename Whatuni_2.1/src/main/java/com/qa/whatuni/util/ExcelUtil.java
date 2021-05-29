package com.qa.whatuni.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	public static Workbook book;
	public static Sheet sheet;
	public static String TestData_Sheet_Path = "./src/main/java/com/qa/wu/testdata/WhatuniTestdata.xlsx";

	public static Object[][] getTestData(String sheetName) {

		try {
			FileInputStream file = new FileInputStream(TestData_Sheet_Path);
			book = WorkbookFactory.create(file);
			System.out.println("Sheet name passed" + sheetName);
			sheet= book.getSheet(sheetName);
			//java.lang.NullPointerException - got when sheet is not asseigned to Sheet but sheet.get is called somewhere
			System.out.println(sheet.getLastRowNum()+"    and    "+sheet.getRow(0).getLastCellNum());
			Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

					data[i][k] =sheet.getRow(i+1).getCell(k).toString();
				}
			}return data;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;

	}

}
