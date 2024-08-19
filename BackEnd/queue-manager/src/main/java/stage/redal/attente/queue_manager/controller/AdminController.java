package stage.redal.attente.queue_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stage.redal.attente.queue_manager.model.Agence;
import stage.redal.attente.queue_manager.model.Employee;
import stage.redal.attente.queue_manager.model.Request;
import stage.redal.attente.queue_manager.model.Service;
import stage.redal.attente.queue_manager.service.AgenceService;
import stage.redal.attente.queue_manager.service.EmployeeService;
import stage.redal.attente.queue_manager.service.RequestService;
import stage.redal.attente.queue_manager.service.ServiceService;


@RestController
@RequestMapping(path = "api/admin")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AdminController {
    @Autowired
    private EmployeeService employeeService;
     @Autowired
    private ServiceService serviceService;
    @Autowired
    private AgenceService agenceService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ClientController clientController;

    //GETTERS ----------------------------------------------------------------------------------------------------------
    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    
    @GetMapping("requests")
    public List<Request> getAllRequests() {
        return this.requestService.getAllRequests();
    }

    @GetMapping("agences")
    public List<Agence> getAllAgences() {
        return this.clientController.getAllAgences();
    }
    @GetMapping("services")
    public List<Service> getAllServices() {
        return this.clientController.getAllServices();
    }

    @GetMapping("services/agence/{agenceId}") 
    public List<Service> getServicesByAgence(@PathVariable("agenceId")Long agenceId) {
        return this.serviceService.getServicesByAgence(this.agenceService.getAgenceById(agenceId));
    }

    @GetMapping("employees/agence/{agenceId}")
    public List<Employee> getEmployeesByAgence(@PathVariable("agenceId") Long agenceId) {
        return this.employeeService.getEmployeesByAgence(agenceId);
    }

    @GetMapping("login")
    public Employee validateLogin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        return this.employeeService.validateLogin(username, password);
    }

    @GetMapping("agences/{agenceId}")
    public Agence getAgenceById(@PathVariable("agenceId") Long agenceId) {
        return agenceService.getAgenceById(agenceId);
    }

    @GetMapping("services/{serviceId}")
    public Service getServiceById(@PathVariable("serviceId")Long serviceId) {
        return serviceService.getServiceById(serviceId);
    }

    @GetMapping("employees/service/{serviceId}")
    public List<Employee> getEmployeesByService(@PathVariable("serviceId") Long serviceId) {
        Service service = this.serviceService.getServiceById(serviceId);
        return this.employeeService.getEmployeesByService(service);
    } 
    

    //POSTS ----------------------------------------------------------------------------------------------------------

    @PostMapping("new-employee")

    public Employee createNewEmployee(@RequestBody Employee employee) {
        employee.setEmployeeId(null);
        return this.employeeService.createNewEmployee(employee);
    }

    @PostMapping("new-service")
    public Service createNewService(@RequestBody Service service) {
        return this.serviceService.createNewService(service);
    }

    @PostMapping("new-agence")
    public Agence createNewAgence(@RequestBody Agence agence) {
        return this.agenceService.createNewAgence(agence);
    }
    


    //UPDATES ----------------------------------------------------------------------------------------------------------
    
    @PutMapping("update-employee/{employeeId}")
    public Employee updateEmployee(@PathVariable("employeeId") Long employeeId, @RequestBody Employee employee) {
        return this.employeeService.updateEmployee(employeeId, employee);

    }

    @PutMapping("update-agence/{agenceId}")
    public Agence updatAgence(@PathVariable Long agenceId, @RequestBody Agence agence) {
        return this.agenceService.updateAgence(agenceId, agence);
    }

    @PutMapping("update-all-passwords") 
    public List<Employee> updateAllEmployeesPasswords() {
        return this.employeeService.updateAllEmployeesPasswords();
    }

    // DELETES

    @DeleteMapping("delete/employee/{employeeId}")
    public void deletingEmployee(@PathVariable("employeeId") Long employeeId) {
        this.employeeService.deleteEmployeeById(employeeId);
    }

    @DeleteMapping("delete/request/{requestId}")
    public void deletingRequest(@PathVariable("requestId") Long requestId) {
        this.requestService.deleteRequestById(requestId);
    }

    @DeleteMapping("delete/agence/{agenceId}")
    public void deletingAgence(@PathVariable("agenceId") Long agenceId) {
        this.agenceService.deleteAgenceById(agenceId);
    }

    @DeleteMapping("delete/service/{serviceId}")
    public void deletingService(@PathVariable("serviceId") Long serviceId) {
        this.serviceService.deleteServiceById(serviceId);
    }
}
