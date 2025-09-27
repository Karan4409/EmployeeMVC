package com.example.EmployeeMvc.Controller;

import com.example.EmployeeMvc.Enitity.Employee;
import com.example.EmployeeMvc.Service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class EmpController {

    @Autowired
    public EmpService empService;

    @GetMapping("/employees")
    public String showAllemployees(Model model){
        List<Employee> employees = empService.findAllEmployee();
        if(employees == null) employees = Collections.emptyList();
        model.addAttribute("employees", employees);
        return "employee-list";
    }


    @GetMapping("/employee/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String addNewEmployee(Model model){  // emp is not passed to save; instead it acts as a binder which helps spring to bind Employee filed to emp and only fields[HTML content] are passed as HTTP is a statelesss protocol
        Employee emp = new Employee();
        model.addAttribute("employee", emp);
        return "employee-form";
    }

    @PostMapping("/employee/save")
    public String saveEmployee(@ModelAttribute ("employee") Employee emp){ // This matches to employee of addNewEmployee .addAttribute and now it creates a new Employee object emp if the id is null it takes the value that is passed in html-form {th:action="submit} if id is not null then it choose the update option
        empService.save(emp); //
        return "redirect:/employees";
    }

    @GetMapping ("employee/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String updateEmployee(@PathVariable(value = "id") long id, Model model){
        Optional<Employee> empWithId = empService.findById(id);
        model.addAttribute("employee", empWithId);
        return "employee-form";
    }

    @DeleteMapping("employee/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String deleteEmployee(@PathVariable(value = "id") long id){
        empService.delete(id);
        return "redirect:/employees";
    }

    @GetMapping("/customLogin")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("/access-denied")
    public String unautherizedAcess(){
        return "unauth";
    }






}
