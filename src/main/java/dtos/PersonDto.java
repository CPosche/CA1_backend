package dtos;

import entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * A DTO for the {@link entities.Person} entity
 */
@AllArgsConstructor
@Getter
@ToString
public class PersonDto implements Serializable {
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
    private final AddressInnerDto fkAddress;
    private final Set<HobbyInnerDto> hobbies = new HashSet<>();
    private final Set<PhoneInnerDto> phones = new HashSet<>();

    public static List<PersonDto> getDtos(Set<Person> people) {
        List<PersonDto> personDtos = new ArrayList<>();
        for (Person p : people) {
            personDtos.add(new PersonDto(p));
        }
        return personDtos;
    }

    public PersonDto(Person person) {
        this.id = person.getId();
        this.personFirstname = person.getPersonFirstname();
        this.personLastname = person.getPersonLastname();
        this.personEmail = person.getPersonEmail();
        this.fkAddress = person.getFkAddress() == null ? null : new AddressInnerDto(person.getFkAddress());
        person.getHobbies().forEach(el -> this.hobbies.add(new HobbyInnerDto(el)));
        person.getPhones().forEach(el -> this.phones.add(new PhoneInnerDto(el)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDto)) return false;
        PersonDto personDto = (PersonDto) o;
        return getId().equals(personDto.getId()) && getPersonFirstname().equals(personDto.getPersonFirstname()) && getPersonLastname().equals(personDto.getPersonLastname()) && getPersonEmail().equals(personDto.getPersonEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPersonFirstname(), getPersonLastname(), getPersonEmail());
    }

    /**
     * A DTO for the {@link entities.Address} entity
     */
    @AllArgsConstructor
    @Getter
    @ToString
    public static class AddressInnerDto implements Serializable {
        private final Integer id;
        @Size(max = 45)
        @NotNull
        private final String adressStreet;
        @Size(max = 45)
        private final String addressInfo;
        @NotNull
        private final CityinfoInnerDto fkCityinfo;

        public AddressInnerDto(Address address) {
            this.id = address.getId();
            this.adressStreet = address.getAdressStreet();
            this.addressInfo = address.getAddressInfo();
            this.fkCityinfo = new CityinfoInnerDto(address.getFkCityinfo());
        }

        /**
         * A DTO for the {@link entities.Cityinfo} entity
         */
        @AllArgsConstructor
        @Getter
        @ToString
        public static class CityinfoInnerDto implements Serializable {
            private final Integer id;
            @NotNull
            private final Integer cityinfoZipcode;
            @Size(max = 45)
            @NotNull
            private final String cityinfoCity;

            public CityinfoInnerDto(Cityinfo cityinfo) {
                this.id = cityinfo.getId();
                this.cityinfoZipcode = cityinfo.getCityinfoZipcode();
                this.cityinfoCity = cityinfo.getCityinfoCity();
            }
        }
    }

    /**
     * A DTO for the {@link entities.Hobby} entity
     */
    @AllArgsConstructor
    @Getter
    @ToString
    public static class HobbyInnerDto implements Serializable {
        private final Integer id;
        @Size(max = 45)
        @NotNull
        private final String hobbyName;
        @Size(max = 45)
        @NotNull
        private final String hobbyDesc;

        public HobbyInnerDto(Hobby hobby) {
            this.id = hobby.getId();
            this.hobbyName = hobby.getHobbyName();
            this.hobbyDesc = hobby.getHobbyDesc();
        }
    }

    /**
     * A DTO for the {@link entities.Phone} entity
     */
    @AllArgsConstructor
    @Getter
    @ToString
    public static class PhoneInnerDto implements Serializable {
        private final Integer id;
        @NotNull
        private final Integer phoneNumber;
        @Size(max = 45)
        @NotNull
        private final String phoneDesc;

        public PhoneInnerDto(Phone phone) {
            this.id = phone.getId();
            this.phoneNumber = phone.getPhoneNumber();
            this.phoneDesc = phone.getPhoneDesc();
        }
    }
}