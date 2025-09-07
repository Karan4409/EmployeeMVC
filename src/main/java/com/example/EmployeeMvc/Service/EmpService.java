package com.example.EmployeeMvc.Service;

import com.example.EmployeeMvc.Enitity.Employee;
import com.example.EmployeeMvc.Repository.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EmpService {

    @Autowired
    public EmpRepo empRepo;

    public List<Employee> findAllEmployee(){
        return empRepo.findAll();
    }

    public Employee save(Employee emp){
         return empRepo.save(emp);
    }

    public Optional<Employee> findById(long id) {
        return empRepo.findById(id);
    }

    public void delete(long id) {
        empRepo.deleteById(id);
    }
}
