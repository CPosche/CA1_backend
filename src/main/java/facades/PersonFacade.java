package facades;

import dtos.CityinfoDto;
import dtos.PersonDto;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.LinkedHashSet;
import java.util.List;

public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public PersonDto getPersonByPhoneNumber(int phoneNumber) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p from Person p join p.phones ph where ph.phoneNumber = :phoneNumber", Person.class);
        query.setParameter("phoneNumber", phoneNumber);
        Person person = query.getSingleResult();
        if (person == null)
            return null;
        return new PersonDto(person);
    }

    public PersonDto editPerson(PersonDto personDto){
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personDto.getId());
        person.setPersonFirstname(personDto.getPersonFirstname());
        person.setPersonLastname(personDto.getPersonLastname());
        person.setPersonEmail(personDto.getPersonEmail());
        if (!personDto.getHobbies().isEmpty())
            personDto.getHobbies().forEach(el -> person.addHobby(el.getId() != null ? new Hobby(el.getId(), el.getHobbyName(), el.getHobbyDesc()) : new Hobby(el.getHobbyName(), el.getHobbyDesc())));
        if (!personDto.getPhones().isEmpty()){
            personDto.getPhones().forEach(el -> person.addPhones(el.getId() != null ? new Phone(el.getId(), el.getPhoneNumber(), el.getPhoneDesc()) : new Phone(el.getPhoneNumber(), el.getPhoneDesc())));
        }
        person.setFkAddress(new Address(personDto.getFkAddress().getId(), personDto.getFkAddress().getAdressStreet(), personDto.getFkAddress().getAddressInfo()));
        person.getFkAddress().setFkCityinfo(new Cityinfo(personDto.getFkAddress().getFkCityinfo().getId(), personDto.getFkAddress().getFkCityinfo().getCityinfoZipcode(), personDto.getFkAddress().getFkCityinfo().getCityinfoCity()));
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        TypedQuery<PersonDto> query = em.createQuery("select NEW dtos.PersonDto(p) from Person p where p.id = :id", PersonDto.class);
        query.setParameter("id", personDto.getId());
        return query.getSingleResult();
    }


    public int getCountByZip(int zip) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select count(p) from Person p join p.fkAddress a join a.fkCityinfo ci where ci.cityinfoZipcode = :zip");
        query.setParameter("zip", zip);
        return Math.toIntExact((long) query.getSingleResult());
    }

    public PersonDto createPerson(PersonDto personDto) {
        EntityManager em = getEntityManager();
        Person person = new Person(personDto.getPersonFirstname(), personDto.getPersonLastname(), personDto.getPersonEmail());
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        return new PersonDto(person);
    }
    public List<PersonDto> getPersonsByHobby(int hobbyID){
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("select DISTINCT p from Person p join p.hobbies ph join ph.people php where ph.id = :hobbyID", Person.class);
        query.setParameter("hobbyID", hobbyID);
        List<Person> persons = query.getResultList();
        if (persons == null)
            return null;
        return PersonDto.getDtos(new LinkedHashSet<>(persons));
    }

    public int getCountByHobby(int hobbyID){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select count(distinct p) from Person p join p.hobbies ph join ph.people php where ph.id = :hobbyID");
        query.setParameter("hobbyID", hobbyID);
        return Math.toIntExact((long) query.getSingleResult());
    }

    public List<PersonDto> getPersonsByZip(CityinfoDto cityinfoDto) {
        EntityManager em = emf.createEntityManager();
        String cityName = cityinfoDto.getCityinfoCity();
        int zipCode = cityinfoDto.getCityinfoZipcode();
        System.out.println("TESTTESTTESTETS");
        System.out.println("TESTTESTTESTETS");
        System.out.println("TESTTESTTESTETS");
        TypedQuery<Person> query = em.createQuery("select p from Person p join p.fkAddress pa join pa.fkCityinfo pc where pc.cityinfoZipcode = :zipCode AND pc.cityinfoCity = :cityName", Person.class);
        query.setParameter("zipCode", zipCode);
        query.setParameter("cityName", cityName);
        List<Person> personList = query.getResultList();
        if (personList == null)
            return null;
        return PersonDto.getDtos(new LinkedHashSet<>(personList));

    }
}
