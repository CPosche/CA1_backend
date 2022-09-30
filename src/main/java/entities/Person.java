package entities;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "person")
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "person_firstname", nullable = false, length = 45)
    private String personFirstname;

    @Size(max = 45)
    @NotNull
    @Column(name = "person_lastname", nullable = false, length = 45)
    private String personLastname;

    @Size(max = 45)
    @NotNull
    @Column(name = "person_email", nullable = false, length = 45)
    private String personEmail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_address_id")
    private Address fkAddress;

    @ManyToMany
    @JoinTable(name = "hobby_has_person",
            joinColumns = @JoinColumn(name = "fk_person_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_hobby_id"))
    private Set<Hobby> hobbies = new LinkedHashSet<>();

    @OneToMany(mappedBy = "fkPerson", cascade = CascadeType.PERSIST)
    private Set<Phone> phones = new LinkedHashSet<>();

    public Person() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonFirstname() {
        return personFirstname;
    }

    public void setPersonFirstname(String personFirstname) {
        this.personFirstname = personFirstname;
    }

    public String getPersonLastname() {
        return personLastname;
    }

    public void setPersonLastname(String personLastname) {
        this.personLastname = personLastname;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public Address getFkAddress() {
        return fkAddress;
    }

    public void setFkAddress(Address fkAddress) {
        this.fkAddress = fkAddress;
    }

    public Set<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void addPhones(Phone phone) {
        this.phones.add(phone);
        phone.setFkPerson(this);
    }

    public void addAddress(Address address) {
        this.fkAddress = address;
        address.getPeople().add(this);
    }

    public Person(String personFirstname, String personLastname, String personEmail) {
        this.personFirstname = personFirstname;
        this.personLastname = personLastname;
        this.personEmail = personEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getId().equals(person.getId()) && getPersonFirstname().equals(person.getPersonFirstname()) && getPersonLastname().equals(person.getPersonLastname()) && getPersonEmail().equals(person.getPersonEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}