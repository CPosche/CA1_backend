package dtos;

import entities.Person;
import entities.Phone;
import interfaces.IDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link entities.Phone} entity
 */
@Data
public class PhoneDto implements Serializable, IDTO {
    private Integer id;
    @NotNull
    private Integer phoneNumber;
    @Size(max = 45)
    @NotNull
    private String phoneDesc;

    public PhoneDto(Phone phone) {
        if (phone.getId() != null)
            this.id = phone.getId();
        this.phoneDesc = phone.getPhoneDesc();
        this.phoneNumber = phone.getPhoneNumber();
    }

    public static List<PhoneDto> getDtos(Set<Phone> phones) {
        List<PhoneDto> phoneDtos = new ArrayList(phones);
        return phoneDtos;
    }

}