package org.desktop.model.repository;

import org.desktop.model.CalendarProd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarProdRepository extends CrudRepository<CalendarProd, Long> {
}
