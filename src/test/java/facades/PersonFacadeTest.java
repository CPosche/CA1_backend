package facades;

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

public class PersonFacadeTest {

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
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE phone AUTO_INCREMENT = 1");
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE person AUTO_INCREMENT = 1");
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE address AUTO_INCREMENT = 1");
            em.createNamedQuery("Cityinfo.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE cityinfo AUTO_INCREMENT = 1");

            Person testPerson = new Person("Test", "Person", "Test@Person.dk");
            Address testAddress = new Address("Dalgasvej 9");
            Cityinfo testCity = new Cityinfo(4600, "Køge");
            testPerson.addPhones(new Phone(71241337, "Testpersons nye telefon"));
            testCity.addAddress(testAddress);
            testPerson.addAddress(testAddress);

            em.persist(testPerson);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void getPersonByPhoneTest(){
        assertEquals("Test", facade.getPersonByPhoneNumber(71241337).getPersonFirstname());
    }

    @Test
    void editPersonTest(){
        EntityManager em = emf.createEntityManager();
        PersonDto personDto = new PersonDto(em.find(Person.class, 1));
        personDto.setPersonEmail("edited@person.dk");
        assertEquals("Test", facade.editPerson(personDto).getPersonFirstname());
    }

    @Test
    void getCountByZipTest(){
        assertEquals(1, facade.getCountByZip(4600));
    }

    @Test
    void createPersonTest(){
        Person newPerson = new Person("New", "Person", "new@person.dk");
        PersonDto newPersonDto = new PersonDto(newPerson);
        PersonDto expectedPersonDto = facade.createPerson(newPersonDto);
        assertEquals("New", expectedPersonDto.getPersonFirstname());
    }
    
}
