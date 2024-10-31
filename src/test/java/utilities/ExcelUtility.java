package utilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {

    public FileInputStream fileInputStream;
    public FileOutputStream fileOutputStream;
    public XSSFWorkbook xssfWorkbook;
    public XSSFSheet xssfSheet;
    public XSSFRow xssfRow;
    public XSSFCell xssfCell;
    public CellStyle cellStyle;
    String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        xssfSheet = xssfWorkbook.getSheet(sheetName);
        int rowCount = xssfSheet.getLastRowNum();
        xssfWorkbook.close();
        fileInputStream.close();
        return rowCount;
    }

    public int getCellCount(String sheetName, int rowNum) throws IOException {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        xssfSheet = xssfWorkbook.getSheet(sheetName);
        xssfRow = xssfSheet.getRow(rowNum);
        int cellCount = xssfRow.getLastCellNum();
        xssfWorkbook.close();
        fileInputStream.close();
        return cellCount;
    }

    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        xssfSheet = xssfWorkbook.getSheet(sheetName);
        xssfRow = xssfSheet.getRow(rowNum);
        xssfCell = xssfRow.getCell(colNum);

        DataFormatter dataFormatter = new DataFormatter();
        String data;
        try{
            data = dataFormatter.formatCellValue(xssfCell);
        }catch(Exception e){
            data = "";
        }

        xssfWorkbook.close();
        fileInputStream.close();
        return data;
    }
}
