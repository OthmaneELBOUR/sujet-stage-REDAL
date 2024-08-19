package stage.redal.attente.queue_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stage.redal.attente.queue_manager.enumeration.RequestState;
import stage.redal.attente.queue_manager.enumeration.TypeService;
import stage.redal.attente.queue_manager.model.Agence;
import stage.redal.attente.queue_manager.model.Request;
import stage.redal.attente.queue_manager.model.Service;
import stage.redal.attente.queue_manager.service.AgenceService;
import stage.redal.attente.queue_manager.service.RequestService;    
import stage.redal.attente.queue_manager.service.ServiceService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;






@RestController
@RequestMapping(path = "api/client")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ClientController {
    
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private AgenceService agenceService;
    @Autowired
    private RequestService requestService;


    
    //WORKING
    @GetMapping("services")
    public List<Service> getAllServices() {
        return this.serviceService.getAllServices();
    }

    //WORKING
    @GetMapping("agences")
    public List<Agence> getAllAgences() {
        return this.agenceService.getAllAgences();
    }

    //WORKING
    @GetMapping("next_request/{typeService}/{agenceId}")
    public Request getNextRequestForService(@PathVariable("typeService") String typeService, @PathVariable("agenceId") Long agenceId) {
        System.out.println("le type recu est " + typeService);

        Agence agence = this.agenceService.getAgenceById(agenceId);
        
        List<Service> serviceList = this.serviceService.getServicesByAgence(agence);
        
        System.out.println("services recu" + serviceList);
        
        Service service = null;
        
        for(Service s : serviceList) {
            if(s.getAgence().equals(agence) && s.getTypeService().getValue().equals(typeService))
                service = s;
        }
        
        List<Request> myRequests = this.requestService.getRequestsByService(service);

        myRequests = this.requestService.filterRequestsByDate(myRequests);
        
        System.out.println(myRequests);

        Request request = null;
        for(Request r : myRequests) {
            if(r.getState() == RequestState.PENDING) {
                if(request == null)
                    request = r;
                else if(r.getTimeStamp().isBefore(request.getTimeStamp()))
                    request = r;
            }
        }

        System.out.println("The next request is " + request);
        if(request != null)
            request.setState(RequestState.IN_PROGRESS);
        //  this.requestService.updateRequest(request);

        System.out.println(this.requestService.getResetDate());
        return request;
    }

    @GetMapping("current-number/{typeService}/{agenceId}")
    public Integer getCurrentNumber(@PathVariable("typeService") String typeService, @PathVariable("agenceId") Long agenceId) {
        Agence agence = this.agenceService.getAgenceById(agenceId);
        
        List<Service> serviceList = this.serviceService.getServicesByAgence(agence);
        
        System.out.println("services recu" + serviceList);
        
        Service service = null;
        
        for(Service s : serviceList) {
            if(s.getAgence().equals(agence) && s.getTypeService().getValue().equals(typeService))
                service = s;
        }
        
        List<Request> myRequests = this.requestService.getRequestsByService(service);

        myRequests = this.requestService.filterRequestsByDate(myRequests);
        
        Request request = null;
        for(Request r : myRequests) {
            if(r.getState() == RequestState.PENDING) {
                if(request == null)
                    request = r;
                else if(r.getTimeStamp().isAfter(request.getTimeStamp()))
                    request = r;
            }
        }
        if(request == null)
            return 0;
        
        System.out.println(this.requestService.getResetDate());
        return request.getNumberTicket() + 1;

    }

    @GetMapping("services/{typeService}")
    public Service getServiceByTypService(@PathVariable("typeService") String typeService) {
        return this.serviceService.getServiceByName(TypeService.getByValue(typeService));
    }

    @GetMapping("services/agence/{agenceId}")
    public List<Service> getServicesByAgence(@PathVariable("agenceId") Long agenceId) {
        return this.serviceService.getServicesByAgence(this.agenceService.getAgenceById(agenceId));
    }

    //WORKING
    @PostMapping("new-ticket")
    public Request addNewRequest(@RequestBody Request request) {
        request.setState(RequestState.PENDING);
        return this.requestService.addNewRequest(request);

    }

    



    

    
    
    
}
