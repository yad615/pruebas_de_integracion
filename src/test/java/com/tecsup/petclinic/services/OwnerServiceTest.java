package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testFindOwnerById() {
        Integer ID = 1;
        String FIRST_NAME = "George";
        Owner owner = null;

        try {
            owner = this.ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("" + owner);
        assertEquals(FIRST_NAME, owner.getFirstName());
    }

    @Test
    public void testFindOwnerByFirstName() {
        String FIND_FIRST_NAME = "George";
        int SIZE_EXPECTED = 1;

        List<Owner> owners = this.ownerService.findByFirstName(FIND_FIRST_NAME);

        assertEquals(SIZE_EXPECTED, owners.size());
    }

    @Test
    public void testFindOwnerByLastName() {
        String FIND_LAST_NAME = "Davis";
        int SIZE_EXPECTED = 2;

        List<Owner> owners = this.ownerService.findByLastName(FIND_LAST_NAME);

        assertEquals(SIZE_EXPECTED, owners.size());
    }

    @Test
    public void testCreateOwner() {
        String FIRST_NAME = "Jane";
        String LAST_NAME = "Smith";
        String ADDRESS = "123 Main St";
        String CITY = "Springfield";
        String TELEPHONE = "1234567890";

        Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);

        Owner ownerCreated = this.ownerService.create(owner);

        log.info("OWNER CREATED :" + ownerCreated);

        assertNotNull(ownerCreated.getId());
        assertEquals(FIRST_NAME, ownerCreated.getFirstName());
        assertEquals(LAST_NAME, ownerCreated.getLastName());
        assertEquals(ADDRESS, ownerCreated.getAddress());
        assertEquals(CITY, ownerCreated.getCity());
        assertEquals(TELEPHONE, ownerCreated.getTelephone());
    }

    @Test
    public void testUpdateOwner() {
        String FIRST_NAME = "Alice";
        String LAST_NAME = "Brown";
        String ADDRESS = "456 Elm St";
        String CITY = "Metropolis";
        String TELEPHONE = "0987654321";

        String UP_FIRST_NAME = "Alicia";
        String UP_LAST_NAME = "Browne";
        String UP_ADDRESS = "789 Pine St";
        String UP_CITY = "Gotham";
        String UP_TELEPHONE = "1122334455";

        Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);

        log.info(">" + owner);
        Owner ownerCreated = this.ownerService.create(owner);
        log.info(">>" + ownerCreated);

        ownerCreated.setFirstName(UP_FIRST_NAME);
        ownerCreated.setLastName(UP_LAST_NAME);
        ownerCreated.setAddress(UP_ADDRESS);
        ownerCreated.setCity(UP_CITY);
        ownerCreated.setTelephone(UP_TELEPHONE);

        Owner updatedOwner = this.ownerService.update(ownerCreated);
        log.info(">>>>" + updatedOwner);

        assertEquals(UP_FIRST_NAME, updatedOwner.getFirstName());
        assertEquals(UP_LAST_NAME, updatedOwner.getLastName());
        assertEquals(UP_ADDRESS, updatedOwner.getAddress());
        assertEquals(UP_CITY, updatedOwner.getCity());
        assertEquals(UP_TELEPHONE, updatedOwner.getTelephone());
    }

    @Test
    public void testDeleteOwner() {
        String FIRST_NAME = "Delete";
        String LAST_NAME = "Me";
        String ADDRESS = "789 Birch St";
        String CITY = "Somewhere";
        String TELEPHONE = "5432109876";

        Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);
        owner = this.ownerService.create(owner);
        log.info("" + owner);

        try {
            this.ownerService.delete(owner.getId());
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            this.ownerService.findById(owner.getId());
            assertTrue(false);
        } catch (OwnerNotFoundException e) {
            assertTrue(true);
        }
    }
}
