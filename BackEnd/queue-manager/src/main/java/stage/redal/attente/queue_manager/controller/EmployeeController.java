package stage.redal.attente.queue_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stage.redal.attente.queue_manager.enumeration.RequestState;
import stage.redal.attente.queue_manager.model.Employee;
import stage.redal.attente.queue_manager.model.Request;
import stage.redal.attente.queue_manager.service.EmployeeService;
import stage.redal.attente.queue_manager.service.RequestService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "api/employee")
@CrossOrigin(origins = {"http://localhost:4200"})
public class EmployeeController {

    @Autowired
    private RequestService requestService;
    @Autowired
    private EmployeeService employeeService;
    
    // GET ---------------------------------------------------------------------------------------------

    @GetMapping("requests/{employeeId}")
    public List<Request> getEmployeeRequests(@PathVariable("employeeId") Long employeeId) {
        return this.requestService.getRequestsByEmployee(this.employeeService.getEmployeeById(employeeId)); 
    }

    // POST --------------------------------------------------------------------------------------------

    
    // PUT METHODS -------------------------------------------------------------------------------------
    @PutMapping("validation/{requestId}")
    public void validateRequest(@PathVariable("requestId") Long requestId, @RequestBody(required = true) Employee employee) {/* Pour le moment l'employé est optionel */
        Request myRequest = this.requestService.getRequestById(requestId);
        myRequest.setState(RequestState.VALIDATED);
        myRequest.setEmployee(employee);
        this.requestService.updateRequest(myRequest);

    }

    @PutMapping("processing/{requestId}")
    public void processingRequest(@PathVariable("requestId") Long requestId) {
        Request myRequest = this.requestService.getRequestById(requestId);
        myRequest.setState(RequestState.IN_PROGRESS);
        this.requestService.updateRequest(myRequest);
    }

    @PutMapping("refusing/{requestId}")
    public void refusingRequest(@PathVariable("requestId") Long requestId, @RequestBody(required = true) Employee employee) {
        Request myRequest = this.requestService.getRequestById(requestId);
        myRequest.setState(RequestState.ABONDONNED);
        myRequest.setEmployee(employee);
        this.requestService.updateRequest(myRequest);
    }
    

    // DELETE ------------------------------------------------------------------------------------------------
}
