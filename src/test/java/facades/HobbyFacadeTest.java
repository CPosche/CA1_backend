package facades;

import dtos.AddressDto;
import dtos.HobbyDto;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static junit.framework.Assert.assertEquals;

public class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;

    public HobbyFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HobbyFacade.getHobbyFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE phone AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("DELETE FROM hobby_has_person").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE hobby AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE person AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE address AUTO_INCREMENT = 1").executeUpdate();
            em.createNamedQuery("Cityinfo.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE cityinfo AUTO_INCREMENT = 1").executeUpdate();

            Phone phone = new Phone(71241337, "Ny telefon");
            Person testPerson = new Person("Test", "Person", "Test@Person.dk");
            testPerson.addPhones(phone);
            Hobby badminton = new Hobby("Badminton", "Vi spiller badminton hver torsdag kl 16");
            Address address = new Address("Solvej 2");
            Cityinfo cityinfo = new Cityinfo(3450, "Alleroed");
            cityinfo.addAddress(address);
            testPerson.addAddress(address);
            testPerson.addHobby(badminton);
            Person testPerson2 = new Person("Test 2", "Person 2", "Test2@Person2.dk");
            testPerson2.addHobby(badminton);
            testPerson2.addAddress(address);
            em.persist(testPerson);
            em.persist(testPerson2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    
}
