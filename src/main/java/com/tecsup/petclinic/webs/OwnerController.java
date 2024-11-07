package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.mapper.OwnerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;

import java.util.List;

@RestController
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerMapper mapper;

    public OwnerController(OwnerService ownerService, OwnerMapper mapper) {
        this.ownerService = ownerService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/owners")
    public ResponseEntity<List<OwnerTO>> findAllOwners() {
        List<Owner> owners = ownerService.findAll();
        log.info("Owners: " + owners);
        owners.forEach(owner -> log.info("Owner >> {}", owner));

        List<OwnerTO> ownersTO = this.mapper.toOwnerTOList(owners);
        log.info("OwnersTO: " + ownersTO);
        ownersTO.forEach(ownerTO -> log.info("OwnerTO >> {}", ownerTO));

        return ResponseEntity.ok(ownersTO);
    }

    @PostMapping(value = "/owners")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OwnerTO> create(@RequestBody OwnerTO ownerTO) {
        Owner newOwner = this.mapper.toOwner(ownerTO);
        OwnerTO newOwnerTO = this.mapper.toOwnerTO(ownerService.create(newOwner));

        return ResponseEntity.status(HttpStatus.CREATED).body(newOwnerTO);
    }

    @GetMapping(value = "/owners/{id}")
    public ResponseEntity<OwnerTO> findById(@PathVariable Integer id) {
        OwnerTO ownerTO;
        try {
            Owner owner = ownerService.findById(id);
            ownerTO = this.mapper.toOwnerTO(owner);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerTO);
    }

    @PutMapping(value = "/owners/{id}")
    public ResponseEntity<OwnerTO> update(@RequestBody OwnerTO ownerTO, @PathVariable Integer id) {
        OwnerTO updatedOwnerTO;
        try {
            Owner ownerToUpdate = ownerService.findById(id);
            ownerToUpdate.setFirstName(ownerTO.getFirstName());
            ownerToUpdate.setLastName(ownerTO.getLastName());
            ownerToUpdate.setAddress(ownerTO.getAddress());
            ownerToUpdate.setCity(ownerTO.getCity());
            ownerToUpdate.setTelephone(ownerTO.getTelephone());

            ownerService.update(ownerToUpdate);

            updatedOwnerTO = this.mapper.toOwnerTO(ownerToUpdate);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedOwnerTO);
    }

    @DeleteMapping(value = "/owners/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            ownerService.delete(id);
            return ResponseEntity.ok("Deleted Owner ID: " + id);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
