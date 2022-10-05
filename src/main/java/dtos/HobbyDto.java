package dtos;

import entities.Hobby;
import entities.Person;
import interfaces.IDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link entities.Hobby} entity
 */
@Data
public class HobbyDto implements Serializable, IDTO {
    private Integer id;
    @Size(max = 45)
    @NotNull
    private String hobbyName;
    @Size(max = 45)
    @NotNull
    private String hobbyDesc;
//    private List<PersonDto> people;


    public HobbyDto (Hobby hobby) {
        if (hobby.getId() != null)
            this.id = hobby.getId();
        this.hobbyDesc = hobby.getHobbyDesc();
        this.hobbyName = hobby.getHobbyName();
//        if(!hobby.getPeople().isEmpty())
//            this.people = PersonDto.getDtos(hobby.getPeople());
    }

    public static List<HobbyDto> getDtos(Set<Hobby> hobbies) {
        List<HobbyDto> hobbyDtos = new ArrayList<>();
        for (Hobby hobby : hobbies){
            hobbyDtos.add(new HobbyDto(hobby));
        }
        return hobbyDtos;
    }
}