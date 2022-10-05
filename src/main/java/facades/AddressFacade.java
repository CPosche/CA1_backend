package facades;

import dtos.AddressDto;
import dtos.PersonDto;
import entities.Address;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AddressFacade {

    private static AddressFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private AddressFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static AddressFacade getAddressFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public AddressDto deleteAddress(int id){
        EntityManager em = getEntityManager();
        Address address = em.find(Address.class, id);
        TypedQuery<Person> query = em.createQuery("select p from Person p join p.fkAddress a where a.id = :id", Person.class);
        List<Person> persons = query.getResultList();
        em.getTransaction().begin();
        for (Person person : persons) {
            person.setFkAddress(null);
            em.persist(person);
        }
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.remove(address);
        em.getTransaction().commit();
        return new AddressDto(address);
    }


}
