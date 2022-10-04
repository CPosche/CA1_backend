package facades;

import dtos.HobbyDto;
import dtos.PhoneDto;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Optional;

import static junit.framework.Assert.assertEquals;

public class PhoneFacadeTest extends SuperFacadeTest{



    private static EntityManagerFactory emf;
    private static PhoneFacade facade;

    public PhoneFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PhoneFacade.getPhoneFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        super.setUp(emf);
    }



    
}
