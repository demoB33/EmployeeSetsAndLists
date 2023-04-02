package com.example.employeesetsandlists.service;

import com.example.employeesetsandlists.exceptions.EmployeeAlreadyAddedException;
import com.example.employeesetsandlists.exceptions.EmployeeNotFoundException;
import com.example.employeesetsandlists.exceptions.EmployeeStorageIsFullException;
import com.example.employeesetsandlists.domain.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private static final int SIZE = 5;
    private final List<Employee> employees = new ArrayList<>(SIZE);

    @PostConstruct
    public void initial() {
        employees.add(new Employee("Сергей", "Акулов"));
        employees.add(new Employee("Иван", "Аулов"));

    }


    public Object addEmployee(String firstName, String lastName) {
        Employee temp = new Employee(firstName, lastName);
        if (employees.size() < SIZE) {
            for (Employee emp : employees) {
                if (employees.contains(emp)) {
                    throw new EmployeeAlreadyAddedException();
                }

            }
            employees.add(temp);
            return temp;
        }
        throw new EmployeeStorageIsFullException();

    }

    public Employee deleteEmployee(String firstName, String lastName) {
        Employee temp = new Employee(firstName, lastName);
        if (employees.remove(temp)) {
            return temp;
        }

        throw new EmployeeNotFoundException();
    }

    public Employee findEmployee(String firstName, String lastName) {
        Employee temp = new Employee(firstName, lastName);
        if (employees.contains(temp)) {
            return temp;
        }

        throw new EmployeeNotFoundException();

    }


    public List<Employee> list() {
        return Collections.unmodifiableList(employees);
    }
}


