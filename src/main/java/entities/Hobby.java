package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "hobby")
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "hobby_name", nullable = false, length = 45)
    private String hobbyName;

    @Size(max = 45)
    @NotNull
    @Column(name = "hobby_desc", nullable = false, length = 45)
    private String hobbyDesc;

    @ManyToMany
    @JoinTable(name = "hobby_has_person",
            joinColumns = @JoinColumn(name = "fk_hobby_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_person_id"))
    private Set<Person> people = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public String getHobbyDesc() {
        return hobbyDesc;
    }

    public void setHobbyDesc(String hobbyDesc) {
        this.hobbyDesc = hobbyDesc;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

}