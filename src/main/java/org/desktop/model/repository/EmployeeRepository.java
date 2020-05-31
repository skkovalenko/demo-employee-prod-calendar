package org.desktop.model.repository;

import org.desktop.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    List<Employee> findAllByDepartment_Id(int name);
}
