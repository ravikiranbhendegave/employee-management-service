package com.ravicode.employee.service;

import com.ravicode.employee.exception.ResourceNotFoundException;
import com.ravicode.employee.model.Employee;
import com.ravicode.employee.repository.EmployeeRepository;
import com.ravicode.employee.util.LogFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private static final Logger LOGGER = LogFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // 1. Create a new employee
    public Employee createEmployee(Employee employee) {
        LOGGER.info("Creating employee: name={}, department={}, salary={}", employee.getName(), employee.getDepartment(), employee.getSalary());
        Employee savedEmployee = employeeRepository.save(employee);
        LOGGER.info("Employee created successfully with id={}", savedEmployee.getId());
        return savedEmployee;
    }

    // 2. Get all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll().stream().collect(Collectors.toList());
        LOGGER.debug("Fetched {} employees", employees.size());
        return employees;
    }

    // 3. get employee by id
    public Employee getEmployeeById(Integer id) {
        LOGGER.debug("Fetching employee by id={}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Employee not found with id={}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
    }

    // 4. Filter employees by salary
    public List<Employee> filterEmployeesBySalary(Double salary) {
        List<Employee> employees = employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toList());
        LOGGER.debug("Found {} employees above salary={}", employees.size(), salary);
        return employees;
    }

    // 5. Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employees = employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
        LOGGER.debug("Found {} employees in department={}", employees.size(), department);
        return employees;
    }

    // 6. Sort employees by salary
    public List<Employee> sortEmployeesBySalary() {
        List<Employee> employees = employeeRepository.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .collect(Collectors.toList());
        LOGGER.debug("Sorted {} employees by salary descending", employees.size());
        return employees;
    }

    // 7. Get employee names in uppercase
    public List<String> getEmployeeNamesInUppercase() {
        List<String> names = employeeRepository.findAll()
                .stream()
                .map(Employee::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        LOGGER.debug("Generated {} uppercase employee names", names.size());
        return names;
    }

    // 8. Calculate total salary
    public Double calculateTotalSalary() {
        Double totalSalary = employeeRepository.findAll()
                .stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        LOGGER.debug("Calculated total salary={}", totalSalary);
        return totalSalary;
    }

    // 9. Find highest-paid employee
    public Employee findHighestPaidEmployee() {
        Employee highestPaidEmployee = employeeRepository.findAll()
                .stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(() -> {
                    LOGGER.warn("No employees found while searching for highest paid employee");
                    return new ResourceNotFoundException("No employees found");
                });
        LOGGER.debug("Highest paid employee is id={}, name={}, salary={}", highestPaidEmployee.getId(), highestPaidEmployee.getName(), highestPaidEmployee.getSalary());
        return highestPaidEmployee;
    }

    // 10. Find top three highest-paid employees
    public List<Employee> findTopThreeHighestPaidEmployees() {
        List<Employee> employees = employeeRepository.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(3)
                .collect(Collectors.toList());
        LOGGER.debug("Fetched top three highest paid employees: {}", employees.size());
        return employees;
    }

    // 11. Group employees by department
    public Map<String, List<Employee>> groupEmployeesByDepartment() {
        Map<String, List<Employee>> groupedEmployees = employeeRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        LOGGER.debug("Grouped employees by department into {} buckets", groupedEmployees.size());
        return groupedEmployees;
    }

    // 12. Count employees department-wise
    public Map<String, Long> countEmployeesByDepartment() {
        Map<String, Long> countMap = employeeRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        LOGGER.debug("Department employee counts: {}", countMap);
        return countMap;
    }

    // 13. Get all unique skills
    public List<String> getAllUniqueSkills() {
        List<String> skills = employeeRepository.findAll()
                .stream()
                .flatMap(employee -> employee.getSkills().stream())
                .distinct()
                .collect(Collectors.toList());
        LOGGER.debug("Fetched {} unique skills", skills.size());
        return skills;
    }

    // 14. Find employees having a particular skill
    public List<Employee> findEmployeesBySkill(String skill) {
        List<Employee> employees = employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getSkills().stream().anyMatch(s -> s.equalsIgnoreCase(skill)))
                .collect(Collectors.toList());
        LOGGER.debug("Found {} employees with skill={}", employees.size(), skill);
        return employees;
    }

    // 15. Calculate salary statistics
    public DoubleSummaryStatistics getSalaryStatistics() {
        DoubleSummaryStatistics statistics = employeeRepository.findAll()
                .stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        LOGGER.debug("Salary statistics: total={}, average={}, min={}, max={}",
                statistics.getSum(), statistics.getAverage(), statistics.getMin(), statistics.getMax());
        return statistics;
    }

    // 16. Delete employee
    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Attempted to delete an employee that does not exist: id={}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
        employeeRepository.delete(employee);
        LOGGER.info("Employee deleted successfully with id={}", id);
    }
}
