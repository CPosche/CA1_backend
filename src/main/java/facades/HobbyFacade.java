package facades;

import dtos.HobbyDto;
import dtos.PersonDto;
import dtos.PhoneDto;
import entities.Hobby;
import entities.Person;
import entities.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.LinkedHashSet;
import java.util.List;

public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<HobbyDto> getAllHobby(){
        EntityManager em = getEntityManager();
        TypedQuery<Hobby> query = em.createQuery("SELECT p FROM Hobby p", Hobby.class);
        List<Hobby> hobbies = query.getResultList();
        if (hobbies == null)
            return null;
        return HobbyDto.getDtos(new LinkedHashSet<>(hobbies));
    }
}
