package practice.constant;

import practice.constant.PropellerType;

import practice.util.EnumUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;



@Converter(autoApply = true)
public class PropellerTypeConverter implements AttributeConverter<PropellerType,String> {

    @Override
    public String convertToDatabaseColumn(PropellerType propellerType) {
        return propellerType == null ? null : propellerType.toString();
    }

    @Override
    public PropellerType convertToEntityAttribute(String propellerType) {
        return EnumUtil.getEnumByValue(propellerType, PropellerType.class);
    }


}
