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
    private PersonFacade() {
    }


    /**
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

    public PersonDto editPerson(PersonDto personDto) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personDto.getId());
        person.setPersonFirstname(personDto.getPersonFirstname());
        person.setPersonLastname(personDto.getPersonLastname());
        person.setPersonEmail(personDto.getPersonEmail());
        if (!personDto.getHobbies().isEmpty()) {
            for (PersonDto.HobbyInnerDto el : personDto.getHobbies()) {
                if (!person.getHobbies().contains(new Hobby(el.getId(), el.getHobbyName(), el.getHobbyDesc()))) {
                    if (el.getId() != null) {
                        if (HobbyFacade.getHobbyFacade(emf).checkHobbyExists(el)) {
                            person.addHobby(HobbyFacade.getHobbyFacade(emf).getHobby(el));
                        } else {
                            person.addHobby(new Hobby(el.getHobbyName(), el.getHobbyDesc()));
                        }
                    }else{
                        person.addHobby(new Hobby(el.getHobbyName(), el.getHobbyDesc()));
                    }
                }
            }
        }
        if (!personDto.getPhones().isEmpty()) {
            for (PersonDto.PhoneInnerDto el : personDto.getPhones()) {
                if (!person.getPhones().contains(new Phone(el.getId(), el.getPhoneNumber(), el.getPhoneDesc()))) {
                    if (el.getId() != null) {
                        if (PhoneFacade.getPhoneFacade(emf).checkPhoneExists(el)) {
                            person.addPhones(PhoneFacade.getPhoneFacade(emf).getPhone(el));
                        } else {
                            person.addPhones(new Phone(el.getPhoneNumber(), el.getPhoneDesc()));
                        }
                    }else{
                        person.addPhones(new Phone(el.getPhoneNumber(), el.getPhoneDesc()));
                    }
                }
            }
        }
        if (personDto.getFkAddress() != null) {
            if (AddressFacade.getAddressFacade(emf).checkAddressExists(personDto.getFkAddress())) {
                person.addAddress(AddressFacade.getAddressFacade(emf).getAddress(personDto.getFkAddress()));
            } else {
                Address address = new Address(personDto.getFkAddress().getAdressStreet(), personDto.getFkAddress().getAddressInfo());
                person.addAddress(address);
                person.getFkAddress().getFkCityinfo().addAddress(address);
            }
        }
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

    public List<PersonDto> getAllPersons() {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("select p from Person p", Person.class);
        List<Person> personList = query.getResultList();
        if (personList == null) {
            return null;
        }
        return PersonDto.getDtos(new LinkedHashSet<>(personList));
    }

    public List<PersonDto> getPersonsByHobby(String hobbyName) {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("select DISTINCT p from Person p join p.hobbies ph join ph.people php where ph.hobbyName = :hobbyName", Person.class);
        query.setParameter("hobbyName", hobbyName);
        List<Person> persons = query.getResultList();
        if (persons == null)
            return null;
        return PersonDto.getDtos(new LinkedHashSet<>(persons));
    }

    public int getCountByHobby(String hobbyName) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select count(distinct p) from Person p join p.hobbies ph join ph.people php where ph.hobbyName = :hobbyName");
        query.setParameter("hobbyName", hobbyName);
        return Math.toIntExact((long) query.getSingleResult());
    }

    public List<PersonDto> getPersonsByZip(CityinfoDto cityinfoDto) {
        EntityManager em = emf.createEntityManager();
        String cityName = cityinfoDto.getCityinfoCity();
        int zipCode = cityinfoDto.getCityinfoZipcode();
        TypedQuery<Person> query = em.createQuery("select p from Person p join p.fkAddress pa join pa.fkCityinfo pc where pc.cityinfoZipcode = :zipCode AND pc.cityinfoCity = :cityName", Person.class);
        query.setParameter("zipCode", zipCode);
        query.setParameter("cityName", cityName);
        List<Person> personList = query.getResultList();
        if (personList == null)
            return null;
        return PersonDto.getDtos(new LinkedHashSet<>(personList));

    }
}
