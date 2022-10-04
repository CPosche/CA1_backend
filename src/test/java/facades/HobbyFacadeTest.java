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

public class HobbyFacadeTest extends SuperFacadeTest{

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
        super.setUp(emf);
    }

    
}
