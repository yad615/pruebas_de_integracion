package com.tecsup.petclinic.services;

import java.util.List;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

public interface OwnerService {

    Owner create(Owner owner);

    Owner update(Owner owner);

    void delete(Integer id) throws OwnerNotFoundException;

    Owner findById(Integer id) throws OwnerNotFoundException;

    List<Owner> findByFirstName(String firstName);

    List<Owner> findByLastName(String lastName);

    List<Owner> findAll();
}
