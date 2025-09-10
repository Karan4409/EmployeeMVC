package com.example.EmployeeMvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/customLogin")
    public String LoginPage(){
        return "login";
    }
}
