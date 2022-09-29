package dtos;

import interfaces.IDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link entities.Cityinfo} entity
 */
@Data
public class CityinfoDto implements Serializable, IDTO {
    private final Integer id;
    @NotNull
    private final Integer cityinfoZipcode;
    @Size(max = 45)
    @NotNull
    private final String cityinfoCity;
}