package practice.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import practice.entity.AirCraft;
import practice.entity.Engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Excel2EntityService {

    public static List<AirCraft> getAirCraftListFromExcel(
            Map<String, AriCraftTableFieldNameService.FieldDetails> colName2FieldDetailsMap,
            String excelFile_path)
            throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchFieldException {

        List<AirCraft> airCraftList = new ArrayList<>();

        InputStream inputStream = new ClassPathResource(excelFile_path).getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firtSheet = workbook.getSheet("AIRCRAFTS");

        Map<Integer,String> excelColNameIndex = new HashMap<>();

        for (Row row: firtSheet) {
            int rowNum = row.getRowNum();
            if (rowNum > 0) {
                AirCraft airCraft = new AirCraft();
                Engine leftCenterEngine = new Engine();
                Engine rightEngine = new Engine();

                int excelColIndex;
                for (excelColIndex=0 ; excelColIndex < excelColNameIndex.size() /*col number*/ ; excelColIndex++) {
                    Cell cell = row.getCell(excelColIndex);
                    String excelColName = excelColNameIndex.get(excelColIndex);
                    AriCraftTableFieldNameService.FieldDetails fieldDetails = colName2FieldDetailsMap.get(excelColName);

                    if (cell!=null) {
                        // populate the entity

                        //if ...
                        AriCraftTableFieldNameService.setField(fieldDetails, cell.getStringCellValue(),
                                airCraft, leftCenterEngine, rightEngine);


                    }
                }
                airCraft.setLeftOrCenterEngine(leftCenterEngine);
                airCraft.setRightEngine(rightEngine);
                airCraftList.add(airCraft);
            }
        }
        workbook.close();
        return airCraftList;
    }

}
