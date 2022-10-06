package facades;

import dtos.CityinfoDto;
import dtos.PersonDto;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PersonFacadeTest extends SuperFacadeTest{

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @BeforeEach
    public void setUp() {
      super.setUp(emf);
    }

    @Test
    void getPersonByPhoneTest(){
        assertEquals("Test", facade.getPersonByPhoneNumber(71241337).getPersonFirstname());
    }

    @Test
    void editPersonTest(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("select p from Person p where p.personFirstname = :name", Person.class);
        query.setParameter("name", "Lars");
        Person person = query.getSingleResult();
        person.setPersonEmail("edited@test.dk");
        person.addHobby(new Hobby(1, "Badminton", "Friday night"));
        PersonDto personDto = new PersonDto(person);
        assertEquals("edited@test.dk", facade.editPerson(personDto).getPersonEmail());
    }

    @Test
    void getCountByZipTest(){
        assertEquals(2, facade.getCountByZip(3450));
    }

    @Test
    void createPersonTest(){
        Person newPerson = new Person("New", "Person", "new@person.dk");
        PersonDto newPersonDto = new PersonDto(newPerson);
        PersonDto expectedPersonDto = facade.createPerson(newPersonDto);
        assertEquals("New", expectedPersonDto.getPersonFirstname());
    }


    @Test
    void getPersonsByHobbyTest() {
        assertEquals(2, facade.getPersonsByHobby("Badminton").size());
    }

    @Test
    void getPersonsByZipTest() {
        int cityInfoId = 1;
        EntityManager em = emf.createEntityManager();
        Cityinfo cityinfo = em.find(Cityinfo.class, cityInfoId);
        CityinfoDto cityinfoDto = new CityinfoDto(cityinfo);
        assertEquals(2, facade.getPersonsByZip(cityinfoDto).size());
    }

    @Test
    void getCountByHobby(){
        assertEquals(2, facade.getCountByHobby("Badminton"));
    }

    @Test
    void getAllPersons() {
        assertEquals(2, facade.getAllPersons().size());
    }
}
