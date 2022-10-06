package facades;

import dtos.AddressDto;
import dtos.HobbyDto;
import dtos.PersonDto;
import entities.Address;
import entities.Hobby;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.LinkedHashSet;
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
        em.getTransaction().begin();
        em.remove(address);
        em.getTransaction().commit();
        return new AddressDto(address);
    }

    public boolean checkAddressExists(PersonDto.AddressInnerDto addressDto){
        EntityManager em = getEntityManager();
        Address address = em.find(Address.class, addressDto.getId());
        return address.equals(new Address(addressDto.getId(), addressDto.getAdressStreet(), addressDto.getAddressInfo()));
    }

    public Address getAddress(PersonDto.AddressInnerDto addressDto){
        EntityManager em = getEntityManager();
        return em.find(Address.class, addressDto.getId());
    }


}
