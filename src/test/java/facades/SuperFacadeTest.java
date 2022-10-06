package facades;

import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SuperFacadeTest {


        public SuperFacadeTest() {
        }


        public void setUp(EntityManagerFactory emf) {
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
                Address address = new Address("Solvej 2", "");
                Cityinfo cityinfo = new Cityinfo(3450, "Alleroed");
                cityinfo.addAddress(address);
                testPerson.addAddress(address);
                testPerson.addHobby(badminton);
                Person testPerson2 = new Person("Test 2", "Person 2", "Test2@Person2.dk");
                testPerson2.addHobby(badminton);
                testPerson2.addAddress(address);
                Person testPerson3 = new Person("Lars", "test3", "test3@test.dk");
                em.persist(testPerson);
                em.persist(testPerson2);
                em.persist(testPerson3);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }



    }
