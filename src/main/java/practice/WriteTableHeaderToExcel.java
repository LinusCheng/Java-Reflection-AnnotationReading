package practice;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import practice.entity.AirCraft;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WriteTableHeaderToExcel {

    public static void main(String[] args) {

        List<String> excelHeaderNames = readAnnotation();
        writeToFile(excelHeaderNames);

        System.out.println("Col csv file generated");

    }

    private static List<String> readAnnotation() {

        AirCraft airCraft = new AirCraft();
        List<String> excelHeaderNames = new ArrayList<>();

        Field[] declaredFields = airCraft.getClass().getDeclaredFields();
        Predicate<Annotation> pColumn = a -> a.annotationType().getName().equals("javax.persistence.Column");
        Predicate<Annotation> pAttrib = a -> a.annotationType().getName().equals("javax.persistence.AttributeOverrides");
        Predicate<Annotation> pAll = pColumn.or(pAttrib);

        for (Field field: declaredFields) {
            List<Annotation> annotationList = Arrays.asList(field.getDeclaredAnnotations());
            List<Annotation> annotationFilteredList = annotationList.stream().filter(pAll).collect(Collectors.toList());
            if (annotationFilteredList.isEmpty()) {
                continue;
            }
            // Plain field
            if (annotationFilteredList.stream().anyMatch(pColumn)) {
                Column col = (Column) annotationFilteredList.stream().filter(pColumn).collect(Collectors.toList()).get(0);
                excelHeaderNames.add(col.name());
            }
            // Object field (The engine)
            else if (annotationFilteredList.stream().anyMatch(pAttrib)) {
                AttributeOverrides attrs = (AttributeOverrides) annotationFilteredList.stream().filter(pAttrib)
                        .collect(Collectors.toList()).get(0);
                AttributeOverride[] attrsArray = attrs.value();
                for (AttributeOverride attr: attrsArray) {
                    excelHeaderNames.add(attr.column().name());
                }
            }
        }

        return excelHeaderNames;
    }


    private static void writeToFile(List<String> excelColNames) {

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        for (int i = 0 ; i<excelColNames.size() ; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(excelColNames.get(i));
        }

        Path fileSavingPath = Paths.get("src", "main", "resources", "table", "Header_export.xls");
        String absPath = fileSavingPath.toFile().getAbsolutePath();
        try (FileOutputStream fos = new FileOutputStream(absPath)) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Not able to generate the col csv");
        }
    }

}
