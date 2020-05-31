package org.desktop.model.repository;

import org.desktop.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository  extends CrudRepository<Address, Integer> {
}
