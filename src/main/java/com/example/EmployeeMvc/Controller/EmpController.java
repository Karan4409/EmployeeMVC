package com.example.EmployeeMvc.Controller;

import com.example.EmployeeMvc.Enitity.Employee;
import com.example.EmployeeMvc.Service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class EmpController {

    @Autowired
    public EmpService empService;

    @GetMapping("/employees")
    public String showAllemployees(Model model){
        model.addAttribute("employees", empService.findAllEmployee()); // This will fetch all the employees from the Service and add to Model(employee) now in html-list iterator will iterate from each employee and display it
        return "employee-list";
    }

    @GetMapping("/employee/new")
    public String addNewEmployee(Model model){
        Employee emp = new Employee();
        model.addAttribute("employee", emp);
        return "employee-form";
    }

    @PostMapping("/employee/save")
    public String saveEmployee(@ModelAttribute ("employee") Employee emp){
        empService.save(emp);
        return "redirect:/employees";
    }

    @GetMapping ("employee/edit/{id}")
    public String updateEmployee(@PathVariable(value = "id") long id, Model model){
        Optional<Employee> empWithId = empService.findById(id);
        model.addAttribute("employee", empWithId);
        return "employee-form";
    }

    @DeleteMapping("employee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id){
        empService.delete(id);
        return "redirect:/employees";
    }


}
