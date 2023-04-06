package com.example.employeesetsandlists.service;

import com.example.employeesetsandlists.exceptions.EmployeeAlreadyAddedException;
import com.example.employeesetsandlists.exceptions.EmployeeNotFoundException;
import com.example.employeesetsandlists.exceptions.EmployeeStorageIsFullException;
import com.example.employeesetsandlists.domain.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class EmployeeService {
    private static final int SIZE = 5;
    private final Map<String, Employee> employees = new HashMap<>(SIZE);

   /* @PostConstruct
    public void initial() {
        employees.put(new Employee("Сергей", "Акулов"));
        employees.put(new Employee("Иван", "Аулов"));
    }*/


    public Object addEmployee(String firstName, String lastName) {
        Employee temp = new Employee(firstName, lastName);
        if (employees.size() < SIZE) {
            if (employees.containsKey(temp.getFullName())) {
                throw new EmployeeAlreadyAddedException();
            }
            employees.put(temp.getFullName(), temp);
            return temp;
        }
        throw new EmployeeStorageIsFullException();
    }

    public Employee deleteEmployee(String firstName, String lastName) {
        Employee temp = new Employee(firstName, lastName);
        if (employees.containsKey(temp.getFullName())) {
            return employees.remove(temp.getFullName());

        }

        throw new EmployeeNotFoundException();
    }

    public Employee findEmployee(String firstName, String lastName) {
        Employee temp = new Employee(firstName, lastName);
        if (employees.containsKey(temp.getFullName())) {
            return employees.get(temp.getFullName());
        }

        throw new EmployeeNotFoundException();

    }


    public Collection<Employee> findAll() {
        return Collections.unmodifiableCollection(employees.values());
    }
}


