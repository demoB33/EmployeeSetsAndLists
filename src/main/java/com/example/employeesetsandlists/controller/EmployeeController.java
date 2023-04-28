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
                        @RequestParam String lastName,
                        @RequestParam("departmentId") int department,
                        @RequestParam("salary") int salary) {
        return (Employee) employeeService.addEmployee(firstName, lastName, department, salary);

    }

    @GetMapping(path = "remove")
    public Employee remove(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam("departmentId") int department,
                           @RequestParam("salary") int salary) {

        return employeeService.deleteEmployee(firstName, lastName, department, salary);
    }

    @GetMapping(path = "/find")
    public Employee find(@RequestParam String firstName,
                         @RequestParam String lastName,
                         @RequestParam("departmentId") int department,
                         @RequestParam("salary") int salary) {
        return employeeService.findEmployee(firstName, lastName, department, salary);
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }


}