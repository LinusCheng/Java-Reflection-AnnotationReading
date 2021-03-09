package practice.entity;

import lombok.Getter;
import lombok.Setter;
import practice.constant.PropellerType;
import practice.constant.PropellerTypeConverter;

import javax.persistence.Convert;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Engine {

    private String serialNumber;
    private String brand;
    private Date makeDate;
    private BigDecimal capacity = BigDecimal.ZERO;
    @Convert(converter = PropellerTypeConverter.class)
    private PropellerType propellerType;

}
