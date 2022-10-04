package entities;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "hobby")
@NamedQuery(name="Hobby.deleteAllRows", query = "delete from Hobby")
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

    @ManyToMany(mappedBy = "hobbies")
    private Set<Person> people = new LinkedHashSet<>();

    public Hobby() {
    }

    public Hobby(String hobbyName, String hobbyDesc) {
        this.hobbyName = hobbyName;
        this.hobbyDesc = hobbyDesc;
    }

    public Hobby(Integer id, String hobbyName, String hobbyDesc) {
        this.id = id;
        this.hobbyName = hobbyName;
        this.hobbyDesc = hobbyDesc;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hobby)) return false;
        Hobby hobby = (Hobby) o;
        return Objects.equals(getId(), hobby.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}