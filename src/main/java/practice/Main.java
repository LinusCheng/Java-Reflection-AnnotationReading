package practice;

import practice.entity.AirCraft;
import practice.service.AriCraftTableFieldNameService;
import practice.service.Excel2EntityService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException {

        Map<String, AriCraftTableFieldNameService.FieldDetails> colName2FieldDetailsMap = AriCraftTableFieldNameService.colName2FieldDetailsMap();

        System.out.println(Paths.get("").toFile().getAbsolutePath().toString());

        List<AirCraft> airCraftList = Excel2EntityService
                .getAirCraftListFromExcel(colName2FieldDetailsMap,"/table/AircraftTable.xlsx");

        System.out.println("Completed");
    }
}
