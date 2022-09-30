package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "adress_street", nullable = false, length = 45)
    private String adressStreet;

    @Size(max = 45)
    @Column(name = "address_info", length = 45)
    private String addressInfo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_cityinfo_id", nullable = false)
    private Cityinfo fkCityinfo;

    @OneToMany(mappedBy = "fkAddress")
    private Set<Person> people = new LinkedHashSet<>();

    public Address() {
    }

    public Address(String adressStreet, String addressInfo) {
        this.adressStreet = adressStreet;
        this.addressInfo = addressInfo;
    }

    public Address(String adressStreet) {
        this.adressStreet = adressStreet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdressStreet() {
        return adressStreet;
    }

    public void setAdressStreet(String adressStreet) {
        this.adressStreet = adressStreet;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public Cityinfo getFkCityinfo() {
        return fkCityinfo;
    }

    public void setFkCityinfo(Cityinfo fkCityinfo) {
        this.fkCityinfo = fkCityinfo;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

}