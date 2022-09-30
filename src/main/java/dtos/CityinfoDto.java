package dtos;

import entities.Cityinfo;
import entities.Hobby;
import interfaces.IDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link entities.Cityinfo} entity
 */
@Data
public class CityinfoDto implements Serializable, IDTO {
    private Integer id;
    @NotNull
    private Integer cityinfoZipcode;
    @Size(max = 45)
    @NotNull
    private String cityinfoCity;


    public CityinfoDto(Cityinfo cityinfo) {
        if (cityinfo.getId() != null)
            this.id = cityinfo.getId();
        this.cityinfoZipcode = cityinfo.getCityinfoZipcode();
        this.cityinfoCity = getCityinfoCity();
    }

    public static List<CityinfoDto> getDtos(Set<Cityinfo> cityinfos) {
        List<CityinfoDto> cityInfoDtos = new ArrayList(cityinfos);
        return cityInfoDtos;
    }
}