package com.ravicode.employee.controller;

import com.ravicode.employee.model.Employee;
import com.ravicode.employee.service.EmployeeService;
import com.ravicode.employee.util.LogFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private static final Logger LOGGER = LogFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 1. Create a new employee
    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        LOGGER.info("Received request to create employee: name={}, department={}", employee.getName(), employee.getDepartment());
        Employee saved = employeeService.createEmployee(employee);
        LOGGER.info("Employee created successfully: id={}", saved.getId());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // 2. Get all employees
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        LOGGER.info("Request received: fetch all employees");
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // 3. Get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        LOGGER.info("Request received: get employee by id={}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // 4. Filter employees by salary
    @GetMapping("/salary")
    public ResponseEntity<List<Employee>> filterEmployeesBySalary(@RequestParam Double salary) {
        LOGGER.info("Request received: filter employees by salary={}", salary);
        List<Employee> employees = employeeService.filterEmployeesBySalary(salary);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // 5. Get employees by department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String department) {
        LOGGER.info("Request received: get employees by department={}", department);
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // 6. Sort employees by salary
    @GetMapping("/sort/salary")
    public ResponseEntity<List<Employee>> sortEmployeesBySalary() {
        LOGGER.info("Request received: sort employees by salary");
        List<Employee> employees = employeeService.sortEmployeesBySalary();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // 7. Get employee names in uppercase
    @GetMapping("/names")
    public ResponseEntity<List<String>> getEmployeeNamesInUppercase() {
        LOGGER.info("Request received: get employee names uppercase");
        List<String> names = employeeService.getEmployeeNamesInUppercase();
        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    // 8. Calculate total salary
    @GetMapping("/salary/total")
    public ResponseEntity<Double> calculateTotalSalary() {
        LOGGER.info("Request received: calculate total salary");
        Double totalSalary = employeeService.calculateTotalSalary();
        return new ResponseEntity<>(totalSalary, HttpStatus.OK);
    }

    // 9. Find highest-paid employee
    @GetMapping("/highest-salary")
    public ResponseEntity<Employee> findHighestPaidEmployee() {
        LOGGER.info("Request received: find highest paid employee");
        Employee highestPaid = employeeService.findHighestPaidEmployee();
        return new ResponseEntity<>(highestPaid, HttpStatus.OK);
    }

    // 10. Find top three highest-paid employees
    @GetMapping("/top-three")
    public ResponseEntity<List<Employee>> findTopThreeHighestPaidEmployees() {
        LOGGER.info("Request received: find top three highest paid employees");
        List<Employee> topThree = employeeService.findTopThreeHighestPaidEmployees();
        return new ResponseEntity<>(topThree, HttpStatus.OK);
    }

    // 11. Group employees by department
    @GetMapping("/department-summary")
    public ResponseEntity<Map<String, List<Employee>>> groupEmployeesByDepartment() {
        LOGGER.info("Request received: group employees by department");
        Map<String, List<Employee>> grouped = employeeService.groupEmployeesByDepartment();
        return new ResponseEntity<>(grouped, HttpStatus.OK);
    }

    // 12. Count employees department-wise
    @GetMapping("/department-count")
    public ResponseEntity<Map<String, Long>> getEmployeeCountByDepartment() {
        LOGGER.info("Request received: count employees by department");
        Map<String, Long> countMap = employeeService.countEmployeesByDepartment();
        return new ResponseEntity<>(countMap, HttpStatus.OK);
    }

    // 13. Get all unique skills
    @GetMapping("/skills")
    public ResponseEntity<List<String>> getAllUniqueSkills() {
        LOGGER.info("Request received: get unique skills");
        List<String> uniqueSkills = employeeService.getAllUniqueSkills();
        return new ResponseEntity<>(uniqueSkills, HttpStatus.OK);
    }

    // 14. Find employees having a particular skill
    @GetMapping("/skill/{skill}")
    public ResponseEntity<List<Employee>> findEmployeesBySkill(@PathVariable String skill) {
        LOGGER.info("Request received: find employees by skill={}", skill);
        List<Employee> employeesWithSkill = employeeService.findEmployeesBySkill(skill);
        return new ResponseEntity<>(employeesWithSkill, HttpStatus.OK);
    }

    // 15. Calculate salary statistics
    @GetMapping("/salary/statistics")
    public ResponseEntity<DoubleSummaryStatistics> getSalaryStatistics() {
        LOGGER.info("Request received: calculate salary statistics");
        DoubleSummaryStatistics statistics = employeeService.getSalaryStatistics();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    // 16. Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        LOGGER.info("Request received: delete employee id={}", id);
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
