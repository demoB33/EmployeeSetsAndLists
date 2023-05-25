package com.example.employeesetsandlists.service;

import com.example.employeesetsandlists.domain.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private  EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;


    public static Stream<Arguments> employeeWithMaxSalaryTestParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Алекс", "Иванько", 1, 50000)),
                Arguments.of(2, new Employee("Иван", "Абрамов", 2, 30000)),
                Arguments.of(3, new Employee("Алексей", "Иванов", 3, 40000))
        );
    }

    public static Stream<Arguments> employeeWithMinSalaryTestParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Иван", "Иванов", 1, 10000)),
                Arguments.of(2, new Employee("Иван", "Абрамов", 2, 30000)),
                Arguments.of(3, new Employee("Алексей", "Иванов", 3, 40000))
        );
    }

    public static Stream<Arguments> findAllEmployeesFromDepartmentTestParams() {
        return Stream.of(
                Arguments.of(1,
                        List.of(
                                new Employee("Иван", "Иванов", 1, 10000),
                                new Employee("Алекс", "Иванько", 1, 50000)
                        )
                ),
                Arguments.of(2,
                        List.of(
                                new Employee("Иван", "Абрамов", 2, 30000)
                        )
                ),
                Arguments.of(3,
                        List.of(
                               new Employee("Алексей", "Иванов", 3, 40000)
                        )
                ),
                Arguments.of(4,
                        Collections.emptyList()
                        )
        );
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.when(employeeService.getAll()).thenReturn(List.of(
                new Employee("Иван", "Иванов", 1, 10000),
                new Employee("Иван", "Абрамов", 2, 30000),
                new Employee("Алексей", "Иванов", 3, 40000),
                new Employee("Алекс", "Иванько", 1, 50000)
        )
        );
    }
@ParameterizedTest
@MethodSource("employeeWithMaxSalaryTestParams")
public void  employeeWithMaxSalaryTest(int departmentId, Employee expected) {
    Assertions.assertThat(departmentService.findEmployeeWithMaxSalaryFromDepartment(departmentId))
            .isEqualTo(expected);

}


    @ParameterizedTest
    @MethodSource("employeeWithMinSalaryTestParams")
    public void  employeeWithMinSalaryTest(int departmentId, Employee expected) {
        Assertions.assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(departmentId))
                .isEqualTo(expected);

    }

    @ParameterizedTest
    @MethodSource("findAllEmployeesFromDepartmentTestParams")
    public void  findAllEmployeesFromDepartmentTest(int departmentId, List<Employee> expected) {
        Assertions.assertThat(departmentService.findAllEmployeesFromDepartment(departmentId))
                .containsExactlyInAnyOrderElementsOf(expected);

    }

    @Test
    public void  findEmployeesByDepartmentTest() {
        Map<Integer, List<Employee>> expected = Map.of(
                1,
                        List.of(
                                new Employee("Иван", "Иванов", 1, 10000),
                                new Employee("Алекс", "Иванько", 1, 50000)
                        ),
                2,
                        List.of(
                                new Employee("Иван", "Абрамов", 2, 30000)
                        ),
                3,
                List.of(
                        new Employee("Алексей", "Иванов", 3, 40000)
                )
        );
        Assertions.assertThat(departmentService.findEmployeesByDepartment())
                .containsExactlyInAnyOrderEntriesOf(expected);

    }









}
