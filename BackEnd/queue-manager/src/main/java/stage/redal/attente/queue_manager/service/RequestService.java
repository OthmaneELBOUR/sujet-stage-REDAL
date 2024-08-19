package stage.redal.attente.queue_manager.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;
import stage.redal.attente.queue_manager.enumeration.RequestState;
import stage.redal.attente.queue_manager.model.Employee;
import stage.redal.attente.queue_manager.model.Request;
import stage.redal.attente.queue_manager.model.Service;
import stage.redal.attente.queue_manager.repo.RequestRepo;

@org.springframework.stereotype.Service
public class RequestService {
    
    @Autowired
     private RequestRepo requestRepo;

     private LocalDate resetDate = null;
    
    public List<Request> getAllRequests() {
        return this.requestRepo.findAll();
    }

    public List<Request> getRequestsByEmployee(Employee employee) {
        return this.requestRepo.getRequestsByEmployee(employee);
    }

    public List<Request> getRequestsByService(Service service) {
        return this.requestRepo.getRequestsByService(service);
    }

    public void updateRequest(Request request) {
        this.requestRepo.save(request);
    }

    public void deleteRequestById(Long requestId) {
        Optional<Request> requestOptional = this.requestRepo.findById(requestId);
        if(!requestOptional.isPresent())
            throw new EntityNotFoundException("Request with id : " + requestId + " not in database");
        this.requestRepo.deleteById(requestId);
    }

    public Request addNewRequest(Request request) {
        
        System.out.println("New Request added: " + request);

        return this.requestRepo.save(request);
    }

    public Request getRequestById(Long requestId) {
        Optional<Request> requestOptional = this.requestRepo.findById(requestId);

        if(requestOptional.isEmpty()) 
            throw new EntityNotFoundException("Request with id : " + requestId + "not in database");

        return requestOptional.get();
    }

    public void resetTickets() {
        if(this.resetDate != null) {
            List<Request> pendingRequests = this.requestRepo.findByState(RequestState.PENDING);

            for(Request r : pendingRequests) {
                r.setState(RequestState.ABONDONNED);
            }

            this.requestRepo.saveAll(pendingRequests);
        }
        
        this.resetDate = LocalDate.now();

    }

    public void deleteOldData() {
        List<Request> allRequests = this.requestRepo.findAll();

        List<Request> dataToDelete = allRequests;

        for(Request r : allRequests) {
            Long days = ChronoUnit.DAYS.between(r.getTimeStamp().toLocalDate(), resetDate);
            if(days > 30) {
                dataToDelete.remove(r);
            }
        }

        this.requestRepo.deleteAll(dataToDelete);
    }

    public LocalDate getResetDate() {
        return this.resetDate;
    }

    public List<Request> filterRequestsByDate( List<Request> myRequests) {
        List<Request> result = new ArrayList<Request>();

        if(resetDate == null)
            this.resetTickets();

        for(Request r : myRequests) {
            if(r.getTimeStamp().toLocalDate().isEqual(this.resetDate))
                result.add(r);
        }
        return result;
    }
}
