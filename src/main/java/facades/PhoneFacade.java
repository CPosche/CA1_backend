package facades;

import dtos.AddressDto;
import dtos.PersonDto;
import dtos.PhoneDto;
import entities.Address;
import entities.Hobby;
import entities.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PhoneFacade {

    private static PhoneFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PhoneFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PhoneFacade getPhoneFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean checkPhoneExists(PersonDto.PhoneInnerDto phoneDto){
        EntityManager em = getEntityManager();
        Phone phone = em.find(Phone.class, phoneDto.getId());
        return phone.equals(new Phone(phoneDto.getId(), phoneDto.getPhoneNumber(), phoneDto.getPhoneDesc()));
    }

    public Phone getPhone(PersonDto.PhoneInnerDto phoneDto){
        EntityManager em = getEntityManager();
        return em.find(Phone.class, phoneDto.getId());
    }


}
