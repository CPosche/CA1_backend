package dtos;

import entities.Hobby;
import entities.Person;
import entities.Phone;
import interfaces.IDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link entities.Person} entity
 */
@Data
public class PersonDto implements Serializable, IDTO {
    private Integer id;
    @Size(max = 45)
    @NotNull
    private String personFirstname;
    @Size(max = 45)
    @NotNull
    private String personLastname;
    @Size(max = 45)
    @NotNull
    private String personEmail;
    private List<HobbyDto> hobbies;

    private List<PhoneDto> phones;

    public PersonDto(Person person) {
        if (person.getId() != null)
            this.id = person.getId();
        this.personFirstname = person.getPersonFirstname();
        this.personLastname = person.getPersonLastname();
        this.personEmail = person.getPersonEmail();
        if (!person.getPhones().isEmpty())
            this.phones = PhoneDto.getDtos(person.getPhones());
        if(!person.getHobbies().isEmpty())
            this.hobbies = HobbyDto.getDtos(person.getHobbies());
    }
    public static List<PersonDto> getDtos(Set<Person> persons) {
        List<PersonDto> persondtos = new ArrayList(persons);
        return persondtos;
    }
}