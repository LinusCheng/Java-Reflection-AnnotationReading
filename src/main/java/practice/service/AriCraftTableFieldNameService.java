package practice.service;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import practice.entity.AirCraft;
import practice.entity.Engine;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AriCraftTableFieldNameService {

    public static void setField(FieldDetails fieldDetails, Object cellVal,
                                AirCraft airCraft, Engine leftCenterEngine, Engine rightEngine)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        if (fieldDetails.getAttributeOverrideName() == null) {
            // Plain field
            Field field = airCraft.getClass().getDeclaredField(fieldDetails.getFieldName());
            updateAirCraftObjByValType(field, airCraft, cellVal);
        } else if (fieldDetails.getAttributeOverrideName().equals("leftEngine")) {
            Field field = leftCenterEngine.getClass().getDeclaredField(fieldDetails.getFieldName());
            updateAirCraftObjByValType(field, leftCenterEngine, cellVal);
        } else if (fieldDetails.getAttributeOverrideName().equals("rightEngine")) {
            Field field = rightEngine.getClass().getDeclaredField(fieldDetails.getFieldName());
            updateAirCraftObjByValType(field, rightEngine, cellVal);
        }

    }

    private static void updateAirCraftObjByValType(Field field, Object airCraft_or_engine, Object cellVal)
            throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        field.setAccessible(true);
        if (cellVal instanceof String) {
            String converterName = field.getDeclaredAnnotationsByType(Convert.class)[0].converter().getName();
            AttributeConverter converter = (AttributeConverter) Class.forName(converterName).newInstance();
            field.set(airCraft_or_engine, converter.convertToEntityAttribute(cellVal));
        } else if (cellVal instanceof Double) {
            if (field.getType().getName().equals("java.math.BigDecimal")) {
                field.set(airCraft_or_engine, BigDecimal.valueOf((Double) cellVal));
            } else if (field.getType().getName().equals("java.lang.Long")) {
                field.set(airCraft_or_engine, ((Double) cellVal).longValue());
            } else if (field.getType().getName().equals("java.lang.int")) {
                field.set(airCraft_or_engine,((Double) cellVal).intValue());
            }
        } else if (cellVal instanceof Date) {
            String converterName = field.getDeclaredAnnotationsByType(Convert.class)[0].converter().getName();
            AttributeConverter converter = (AttributeConverter) Class.forName(converterName).newInstance();
            field.set(airCraft_or_engine, converter.convertToEntityAttribute(cellVal));
        }
        field.setAccessible(false);
    }



    // Get the name mapping between table and java field in the entity - AirCraft
    public static Map<String, FieldDetails> colName2FieldDetailsMap() {
        AirCraft airCraft = new AirCraft();
        Map<String, FieldDetails> colName2FieldDetailsMap = new HashMap<>();

        // Get info of annotations needed in AirCraft
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
                FieldDetails fieldDetails = FieldDetails.builder().fieldName(field.getName()).build();
                colName2FieldDetailsMap.put(col.name(), fieldDetails);
            }
            // Object field (The engine)
            else if (annotationFilteredList.stream().anyMatch(pAttrib)) {
                AttributeOverrides attrs = (AttributeOverrides) annotationFilteredList.stream().filter(pAttrib)
                        .collect(Collectors.toList()).get(0);
                AttributeOverride[] attrsArray = attrs.value();
                for (AttributeOverride attr: attrsArray) {
                    FieldDetails fieldDetails = FieldDetails.builder()
                            .fieldName(attr.name())
                            .attributeOverrideName(field.getName())
                            .build();
                    colName2FieldDetailsMap.put(attr.column().name(), fieldDetails);
                }
            }
        }
        return colName2FieldDetailsMap;
    }

    @Getter
    @Setter
    @Builder
    public static class FieldDetails {
        private String fieldName;
        private String attributeOverrideName;
    }

}
