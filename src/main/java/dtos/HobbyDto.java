package dtos;

import interfaces.IDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link entities.Hobby} entity
 */
@Data
public class HobbyDto implements Serializable, IDTO {
    private final Integer id;
    @Size(max = 45)
    @NotNull
    private final String hobbyName;
    @Size(max = 45)
    @NotNull
    private final String hobbyDesc;
    private final Set<PersonDto> people;
}