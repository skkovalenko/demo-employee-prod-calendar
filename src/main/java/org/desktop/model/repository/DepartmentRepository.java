package org.desktop.model.repository;

import org.desktop.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    List<Department> findAllByName(String name);
}
