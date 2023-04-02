package com.example.employeesetsandlists.controller;


import com.example.employeesetsandlists.domain.Employee;
import com.example.employeesetsandlists.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }


    @GetMapping(path = "/add")
    public Employee add(@RequestParam String firstName,
                        @RequestParam String lastName) {
            return (Employee) employeeService.addEmployee(firstName,lastName);

    }

    @GetMapping(path = "remove")
    public Employee remove(@RequestParam String firstName,
                           @RequestParam String lastName) {

        return employeeService.deleteEmployee(firstName,lastName);
    }

    @GetMapping(path = "/find")
    public Employee find(@RequestParam String firstName,
                         @RequestParam String lastName) {
        return employeeService.findEmployee(firstName,lastName);
    }
    @GetMapping
    public List<Employee> list() {
        return employeeService.list();
    }

}

