package com.example.employeesetsandlists.service;

import com.example.employeesetsandlists.domain.Employee;
import com.example.employeesetsandlists.exceptions.EmployeeAlreadyAddedException;
import com.example.employeesetsandlists.exceptions.EmployeeNotFoundException;
import com.example.employeesetsandlists.exceptions.EmployeeStorageIsFullException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService();
    public static Stream<Arguments> addErrorNameTestTestParams() {
        return Stream.of(
                Arguments.of("Иван1"),
                Arguments.of("Иван!")
        );
    }

    public static Stream<Arguments> addErrorSurnameTestTestParams() {
        return Stream.of(
                Arguments.of("Иванов1"),
                Arguments.of("Иванов!")
        );
    }

    @BeforeEach
    public void beforeEach() {
        employeeService.addEmployee("Ивано","Иваново",1,20000);
        employeeService.addEmployee("Иван","Абрамов",2,30000);
        employeeService.addEmployee("Алексей","Иванов",3,40000);
    }

    @AfterEach
    public void afterEach() {
        employeeService.getAll().forEach(employee-> employeeService.deleteEmployee(employee.getFirstname(), employee.getLastName(), employee.getDepartment(), employee.getSalary()));
    }

@Test
    public void addTest() {
    int beforeCount = employeeService.getAll().size();
    Employee expected = new Employee("Иван","Иванов",1,20000);
    Assertions.assertThat(employeeService.addEmployee("Иван","Иванов",1,20000))
            .isEqualTo(expected)
            .isIn(employeeService.getAll());
    Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount + 1);
    Assertions.assertThat(employeeService.findEmployee("Иван","Иванов",1,20000)).isEqualTo(expected);
    }


    @ParameterizedTest
    @MethodSource("addErrorNameTestTestParams")
    public void addErrorNameTest(String incorrect) {

        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(()->employeeService.addEmployee(incorrect,"Иванов",1,20000));

    }

    @ParameterizedTest
    @MethodSource("addErrorSurnameTestTestParams")
    public void addErrorSurnameTest(String incorrect) {

        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(()->employeeService.addEmployee("Иван",incorrect,1,20000));

    }

    @Test
    public void addEmployeeAlreadyTest() {
        Assertions.assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(()-> employeeService.addEmployee("Алексей","Иванов",3,40000));
    }

    @Test
    public void addStorageIsFullTest() {
        Stream.iterate(1,i->i+1)
                        .limit(7)
                                .map(number->new Employee(
                                        "Вася" + ((char) ('a' + number)),
                                        "Васькин" + ((char) ('a' + number)),
                                        number,
                                        10000 + number))
                                        .forEach(employee -> employeeService.addEmployee(employee.getFirstname(), employee.getLastName(), employee.getDepartment(), employee.getSalary()));
        Assertions.assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(()-> employeeService.addEmployee("Алекс","Иванов",3,40000));
    }

    @Test
    public void deleteTest() {
        int beforeCount = employeeService.getAll().size();
        Employee expected = new Employee("Иван","Абрамов",2,30000);
        Assertions.assertThat(employeeService.deleteEmployee("Иван","Абрамов",2,30000))
                .isEqualTo(expected)
                .isNotIn(employeeService.getAll());
        Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount - 1);
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()-> employeeService.findEmployee("Иван","Абрамов",2,30000));
    }

    @Test
    public void deleteWhenNotFoundTest() {

        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()-> employeeService.findEmployee("ИванNotIvan","Абрамов",2,30000));
    }

    @Test
    public void findTest() {
        int beforeCount = employeeService.getAll().size();
        Employee expected = new Employee("Иван","Абрамов",2,30000);
        Assertions.assertThat(employeeService.findEmployee("Иван","Абрамов",2,30000))
                .isEqualTo(expected)
                .isIn(employeeService.getAll());
        Assertions.assertThat(employeeService.getAll()).hasSize(beforeCount );

    }

    @Test
    public void findWhenNotFoundTest() {

        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()-> employeeService.findEmployee("ИванNotIvan","Абрамов",2,30000));
    }

    @Test
    public void getAllTest() {

        Assertions.assertThat(employeeService.getAll())
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        new Employee("Ивано", "Иваново", 1, 20000),
                        new Employee("Иван", "Абрамов", 2, 30000),
                        new Employee("Алексей", "Иванов", 3, 40000)
                );
    }
}

