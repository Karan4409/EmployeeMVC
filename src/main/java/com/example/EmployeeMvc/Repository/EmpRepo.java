package com.example.EmployeeMvc.Repository;

import com.example.EmployeeMvc.Enitity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepo extends JpaRepository<Employee, Long> {
}
