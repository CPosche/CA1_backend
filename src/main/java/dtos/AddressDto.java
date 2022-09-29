package dtos;

import interfaces.IDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link entities.Address} entity
 */
@Data
public class AddressDto implements Serializable, IDTO {
    private final Integer id;
    @Size(max = 45)
    @NotNull
    private final String adressStreet;
    @Size(max = 45)
    private final String addressInfo;
    private final Set<PersonDto> people;
}