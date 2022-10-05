package rest;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SuperResourceTest {
    Phone phone;
    Phone phone2;
    Person testPerson;

    Hobby tennis;
    Hobby badminton;
    Address address;
    Cityinfo cityinfo;
    Person testPerson2;

            public void setup(EntityManagerFactory emf){
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

                     phone = new Phone(71241337, "Ny telefon");
                     phone2 = new Phone(99999999, "Fast net");
                     testPerson = new Person("Test", "Person", "Test@Person.dk");
                    testPerson.addPhones(phone);
                     badminton = new Hobby("Badminton", "Vi spiller badminton hver torsdag kl 16");
                     address = new Address("Solvej 2", "");
                     cityinfo = new Cityinfo(3450, "Alleroed");
                    tennis = new Hobby("Tennis", "friday");
                    cityinfo.addAddress(address);
                    testPerson.addAddress(address);
                    testPerson.addHobby(badminton);
                     testPerson2 = new Person("Test 2", "Person 2", "Test2@Person2.dk");
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
