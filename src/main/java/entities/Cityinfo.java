package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cityinfo")
@NamedQuery(name = "Cityinfo.deleteAllRows", query = "delete from Cityinfo")
public class Cityinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cityinfo_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "cityinfo_zipcode", nullable = false)
    private Integer cityinfoZipcode;

    @Size(max = 45)
    @NotNull
    @Column(name = "cityinfo_city", nullable = false, length = 45)
    private String cityinfoCity;

    @OneToMany(mappedBy = "fkCityinfo", cascade = CascadeType.PERSIST)
    private Set<Address> addresses = new LinkedHashSet<>();



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityinfoZipcode() {
        return cityinfoZipcode;
    }

    public void setCityinfoZipcode(Integer cityinfoZipcode) {
        this.cityinfoZipcode = cityinfoZipcode;
    }

    public String getCityinfoCity() {
        return cityinfoCity;
    }

    public void setCityinfoCity(String cityinfoCity) {
        this.cityinfoCity = cityinfoCity;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
        address.setFkCityinfo(this);
    }

    public Cityinfo() {
    }

    public Cityinfo(Integer cityinfoZipcode, String cityinfoCity) {
        this.cityinfoZipcode = cityinfoZipcode;
        this.cityinfoCity = cityinfoCity;
    }
}