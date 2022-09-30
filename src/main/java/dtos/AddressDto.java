package dtos;

import entities.Address;
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
 * A DTO for the {@link entities.Address} entity
 */
@Data
public class AddressDto implements Serializable, IDTO {
    private Integer id;
    @Size(max = 45)
    @NotNull
    private String adressStreet;
    @Size(max = 45)
    private String addressInfo;
    private List<PersonDto> people;

    public AddressDto(Address address) {
        if (address.getId() != null)
            this.id = address.getId();
        this.adressStreet = address.getAdressStreet();
        this.addressInfo = address.getAddressInfo();
        if (!address.getPeople().isEmpty())
            this.people = PersonDto.getDtos(address.getPeople());
    }

    public static List<AddressDto> getDtos(Set<Address> addresses) {
        List<AddressDto> addressDtos = new ArrayList(addresses);
        return addressDtos;
    }
}