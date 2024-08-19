package stage.redal.attente.queue_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;
import stage.redal.attente.queue_manager.enumeration.TypeService;
import stage.redal.attente.queue_manager.exceptions.EntityAlreadyInDataBase;
import stage.redal.attente.queue_manager.model.Agence;
import stage.redal.attente.queue_manager.model.Service;
import stage.redal.attente.queue_manager.repo.ServiceRepo;


@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    private ServiceRepo serviceRepo;

    public List<Service> getAllServices() {
        return this.serviceRepo.findAll();
    }

    public Service createNewService(Service service) {
        List<Service> sameAgence = this.serviceRepo.getServicesByAgence(service.getAgence());

        for(Service s : sameAgence) {
            if(s.getTypeService() == service.getTypeService())
                throw new EntityAlreadyInDataBase("Service " + service.getTypeService() + " already in agence " + service.getAgence());
        }

        return this.serviceRepo.save(service);
    }

    public void deleteServiceById(Long id) {
        Optional<Service> serviceOptional = this.serviceRepo.findById(id);

        if(!serviceOptional.isPresent()) {
            throw new EntityNotFoundException("Service with id : " + id + "not in database");
        }

        this.serviceRepo.deleteById(id);
    }

    public Service getServiceById(Long serviceId) {
        Optional<Service> serviceOptional = this.serviceRepo.findById(serviceId);
        if(serviceOptional.isEmpty())
            throw new EntityNotFoundException("Service with id: " + serviceId + " not found in database");
        return serviceOptional.get();
    }

    public Service getServiceByName(TypeService typeService) {
        List<Service> resulList = this.serviceRepo.findByTypeService(typeService);
        if(resulList.isEmpty())
            throw new EntityNotFoundException("Service introuvable");
        return resulList.get(0);
    }

    public List<Service> getServicesByAgence(Agence agence) {
        return this.serviceRepo.getServicesByAgence(agence);
    }
}
