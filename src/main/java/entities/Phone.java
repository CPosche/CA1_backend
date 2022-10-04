package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "phone")
@NamedQuery(name = "Phone.deleteAllRows", query = "delete from Phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Integer phoneNumber;

    @Size(max = 45)
    @NotNull
    @Column(name = "phone_desc", nullable = false, length = 45)
    private String phoneDesc;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_person_id")
    private Person fkPerson;

    public Phone() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneDesc() {
        return phoneDesc;
    }

    public void setPhoneDesc(String phoneDesc) {
        this.phoneDesc = phoneDesc;
    }

    public Person getFkPerson() {
        return fkPerson;
    }

    public void setFkPerson(Person fkPerson) {
        this.fkPerson = fkPerson;
    }

    public Phone(Integer phoneNumber, String phoneDesc) {
        this.phoneNumber = phoneNumber;
        this.phoneDesc = phoneDesc;
    }

    public Phone(Integer id, Integer phoneNumber, String phoneDesc){
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.phoneDesc = phoneDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone phone = (Phone) o;
        return Objects.equals(getId(), phone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}