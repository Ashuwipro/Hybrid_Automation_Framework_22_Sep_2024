package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {
        //String path = ".\\..\\..\\testData\\LoginData.xlsx";
        String path = "/Users/ashutoshpal/Downloads/Hybrid Automation Framework - 22 Sep 2024/testData/LoginData.xlsx";

        ExcelUtility excelUtility = new ExcelUtility(path);

        int totalRows = excelUtility.getRowCount("Sheet1");
        int totalColumns = excelUtility.getCellCount("Sheet1", 1);

        String[][] loginData = new String[totalRows][totalColumns];

        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                loginData[i - 1][j] = excelUtility.getCellData("Sheet1", i, j);
            }
        }

        return loginData;
    }
}
