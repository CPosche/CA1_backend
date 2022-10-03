package facades;

import dtos.AddressDto;
import dtos.PersonDto;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static junit.framework.Assert.assertEquals;

public class AddressFacadeTest extends SuperFacadeTest{

    private static EntityManagerFactory emf;
    private static AddressFacade facade;

    public AddressFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = AddressFacade.getAddressFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        super.setUp(emf);
    }


    
}
