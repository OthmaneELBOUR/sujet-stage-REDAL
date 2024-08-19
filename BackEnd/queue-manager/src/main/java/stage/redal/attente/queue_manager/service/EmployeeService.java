package stage.redal.attente.queue_manager.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;
import stage.redal.attente.queue_manager.exceptions.EntityAlreadyInDataBase;
import stage.redal.attente.queue_manager.model.Employee;
import stage.redal.attente.queue_manager.model.Service;
import stage.redal.attente.queue_manager.repo.EmployeeRepo;

@org.springframework.stereotype.Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    public List<Employee> getAllEmployees() {
        return this.employeeRepo.findAll();
    }

    public List<Employee> updateAllEmployeesPasswords() {
        List<Employee> myEmployees = this.employeeRepo.findAll();

        for(Employee e : myEmployees) {
            if(e.getPassword() == null) {
                e.setPassword(this.hashPassword(e.getPassword()));
                this.employeeRepo.save(e);
            }
        }
        return myEmployees;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest passwordDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = passwordDigest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for(byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
            
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> employeeOptional = this.employeeRepo.findById(employeeId);

        if (!employeeOptional.isPresent()) {
            throw new EntityNotFoundException("Employee with id : " + employeeId + " not found in Database");
        }

        return employeeOptional.get();
    }

    public Employee validateLogin(String username, String password) {
        List<Employee> employeesByUsername = this.employeeRepo.getEmployeeByUsername(username);

        Employee employeeFound = null;
        for (Employee e : employeesByUsername) {
            if (e.getPassword().equals(this.hashPassword(password))) {
                employeeFound = e;
                break;
            }
        }
        return employeeFound;

    }

    public List<Employee> getEmployeesByAgence(Long agenceId) {
        List<Employee> employeeList = this.employeeRepo.findAll();
        List<Employee> listCopy = this.employeeRepo.findAll();
        System.out.println("Before");
        System.out.println(employeeList);

        if(employeeList != null){
            for(Employee e : listCopy) {
                if(e.getService().getAgence().getAgenceId() != agenceId) {
                    employeeList.remove(e);
                }
            }
        }

        if(employeeList.isEmpty())
            return null;
        return employeeList;

    }

    public List<Employee> getEmployeesByService(Service service) {
        List<Employee> employeesByService = this.employeeRepo.getEmployeeByService(service);
        return employeesByService;
    }

    public Employee createNewEmployee(Employee employee) {
        List<Employee> employees = this.employeeRepo.getEmployeeByEmail(employee.getEmail());

        System.out.println(employees);

        if (employees.size() > 0)
            throw new EntityAlreadyInDataBase(employee.getEmployeeId() + " : " + employee.getFirstName() + " "
                    + employee.getLastName() + " already in database");
        
        String hashedPassword = this.hashPassword(employee.getPassword());
        if(hashedPassword != null) {
            employee.setPassword(hashedPassword);
        }
        return this.employeeRepo.save(employee);
    }

    public void deleteEmployeeById(Long id) {
        Optional<Employee> employeeOptional = this.employeeRepo.findById(id);

        if (!employeeOptional.isPresent()) {
            throw new EntityNotFoundException("Employee with id : " + id + "not in database");
        }

        this.employeeRepo.deleteById(id);
    }

    public Employee updateEmployee(Long employeeId, Employee employee) {
        Optional<Employee> employeOptional = this.employeeRepo.findById(employeeId);
        if (!employeOptional.isPresent())
            throw new EntityNotFoundException("Pas d'employé d'id : " + employee.getEmployeeId());
        String hashedPassword = this.hashPassword(employee.getPassword());
        if(hashedPassword != null) {
            employee.setPassword(hashedPassword);
        }
        return this.employeeRepo.save(employee);
    }
}
