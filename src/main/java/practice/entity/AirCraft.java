package practice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.convert.Jsr310Converters;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class AirCraft {

    @Column(name = "TAIL_NUMBER")
    private String tailNumber;

    @Column(name = "OWNER_NAME")
    private String owner;

    @Column(name = "AIRCRAFT_BRAND")
    private BigDecimal brand;

    @Column(name = "AIRCRAFT_MODEL")
    private BigDecimal model;

    @Column(name = "MAKE_DATE")
    @Convert(converter = Jsr310Converters.LocalDateToDateConverter.class)
    private LocalDate makeDate;

    @Column(name = "LENGTH")
    private BigDecimal length;

    @Column(name = "WIDTH")
    private BigDecimal width;

    @Column(name = "HEIGHT")
    private BigDecimal height;

    @Column(name = "GROSS_WEIGHT")
    private BigDecimal weight;

    @Column(name = "NUM_OF_SEATS")
    private int passenger;

    @AttributeOverrides({
            @AttributeOverride(name = "serialNumber", column = @Column(name = "LEFT/CENTER_ENGINE_SN")),
            @AttributeOverride(name = "brand", column = @Column(name = "LEFT/CENTER_ENGINE_BRAND")),
            @AttributeOverride(name = "makeDate", column = @Column(name = "LEFT/CENTER_ENGINE_MAKE_DATE")),
            @AttributeOverride(name = "capacity", column = @Column(name = "LEFT/CENTER_ENGINE_CAPACITY")),
            @AttributeOverride(name = "propellerType", column = @Column(name = "LEFT/CENTER_ENGINE_PROP_TYPE"))
    })
    private Engine leftOrCenterEngine;

    @AttributeOverrides({
            @AttributeOverride(name = "serialNumber", column = @Column(name = "RIGHT_ENGINE_SN")),
            @AttributeOverride(name = "brand", column = @Column(name = "RIGHT_ENGINE_BRAND")),
            @AttributeOverride(name = "makeDate", column = @Column(name = "RIGHT_ENGINE_MAKE_DATE")),
            @AttributeOverride(name = "capacity", column = @Column(name = "RIGHT_ENGINE_CAPACITY")),
            @AttributeOverride(name = "propellerType", column = @Column(name = "RIGHT_ENGINE_PROP_TYPE"))
    })
    private Engine rightEngine;


}
