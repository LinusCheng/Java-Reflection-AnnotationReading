package practice;

import practice.entity.AirCraft;
import practice.service.AriCraftTableFieldNameService;
import practice.service.Excel2EntityService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, AriCraftTableFieldNameService.FieldDetails> colName2FieldDetailsMap = AriCraftTableFieldNameService.colName2FieldDetailsMap();

        List<AirCraft> airCraftList = Excel2EntityService
                .getAirCraftListFromExcel(colName2FieldDetailsMap,"the_path...");

        System.out.println("Completed");
    }
}
