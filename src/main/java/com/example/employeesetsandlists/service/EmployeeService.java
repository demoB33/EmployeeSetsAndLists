package com.example.employeesetsandlists.service;

import com.example.employeesetsandlists.exceptions.EmployeeAlreadyAddedException;
import com.example.employeesetsandlists.exceptions.EmployeeNotFoundException;
import com.example.employeesetsandlists.exceptions.EmployeeStorageIsFullException;
import com.example.employeesetsandlists.domain.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class EmployeeService {
    private static final int SIZE = 10;
    private final List<Employee> employees = new ArrayList<>();

    /* @PostConstruct
    public void initial() {
        employees.add(new Employee("Сергей", "Акулов"));
        employees.add(new Employee("Иван", "Аулов"));
    }*/


    public Object addEmployee(String firstName, String lastName, int department, int salary) {

        checkEmployeeInput(firstName, lastName);

        Employee temp = new Employee(firstName, lastName, department, salary);
        if (employees.contains(temp)) {
            throw new EmployeeAlreadyAddedException();
        }

        if (employees.size() < SIZE) {
            employees.add(temp);
            System.out.println(temp);
            return temp;
        }
        throw new EmployeeStorageIsFullException();

    }

    public Employee deleteEmployee(String firstName, String lastName, int department, int salary) {

        checkEmployeeInput(firstName, lastName);

        Employee temp = new Employee(firstName, lastName, department, salary);
        if (employees.remove(temp)) {
            return temp;
        }

        throw new EmployeeNotFoundException();
    }

    public Employee findEmployee(String firstName, String lastName, int department, int salary) {

        checkEmployeeInput(firstName, lastName);

        Employee temp = new Employee(firstName, lastName, department, salary);
        if (employees.contains(temp)) {
            return temp;
        }

        throw new EmployeeNotFoundException();

    }

    private void checkEmployeeInput(String firstName, String lastName) {
        if (!(!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName) && StringUtils.isAlpha(firstName) && StringUtils.isAlpha(lastName))) {
            throw new EmployeeAlreadyAddedException();
        }

    }


    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }

}