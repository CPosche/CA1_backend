package dtos;

import entities.Hobby;
import interfaces.IDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link entities.Person} entity
 */
@Data
public class PersonDto implements Serializable, IDTO {
    private final Integer id;
    @Size(max = 45)
    @NotNull
    private final String personFirstname;
    @Size(max = 45)
    @NotNull
    private final String personLastname;
    @Size(max = 45)
    @NotNull
    private final String personEmail;
    private final Set<Hobby> hobbies;
}