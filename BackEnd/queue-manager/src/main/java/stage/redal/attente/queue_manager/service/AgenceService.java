package stage.redal.attente.queue_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import stage.redal.attente.queue_manager.exceptions.EntityAlreadyInDataBase;
import stage.redal.attente.queue_manager.exceptions.UpdateException;
import stage.redal.attente.queue_manager.model.Agence;
import stage.redal.attente.queue_manager.repo.AgenceRepo;

@Service
public class AgenceService {
    @Autowired
    private AgenceRepo agenceRepo;

    // GET ALL
    public List<Agence> getAllAgences() {
        return agenceRepo.findAll();
    }

    //GET BY ID
    public Agence getAgenceById(Long agenceId) {
        Optional<Agence> agenceOptional = this.agenceRepo.findById(agenceId);
        if(agenceOptional.isEmpty())
            throw new EntityNotFoundException("Can't find agence with id : " + agenceId);
        return agenceOptional.get();
    }

    //ADD NEW AGENCY
    public Agence createNewAgence(Agence agence) {
        if(this.agenceRepo.existsByAdress(agence.getAdress()) ||
         this.agenceRepo.existsByName(agence.getName()) || 
         this.agenceRepo.existsByPhoneNumber(agence.getPhoneNumber()))
            {
                throw new EntityAlreadyInDataBase("Agence " + agence + " already in database");
        }

        
        return this.agenceRepo.save(agence);
        

    }

    // DELETE AGENCE BY ID
    public void deleteAgenceById(Long id) {
        if(!agenceRepo.existsById(id))
            throw new EntityNotFoundException("Agence with ID : " + id + "not in database");
        agenceRepo.deleteById(id);
    }

    // UPDATE AGENCE NAME
    public Agence updateAgenceName(Long agenceId, String newName) {
        Optional<Agence> agenceOptional = agenceRepo.findById(agenceId);
        
        if(!agenceOptional.isPresent()) {
            throw new UpdateException("Cannot Update the agence " + agenceId + " with name " + newName);
        }
        
        Agence agence = agenceOptional.get();
            
        if(newName != null && newName.length() > 0 && !newName.equals(agence.getName()))
                agence.setName(newName);
        return this.agenceRepo.save(agence);
            
            

    }


    // UPDATE AGENCE ADRESS
    public Agence updateAgence(Long agenceId, Agence agence) {
        Optional<Agence> agenceOptional = agenceRepo.findById(agenceId);
        
        if(!agenceOptional.isPresent()) {
            throw new UpdateException("Cannot Update the agence " + agenceId);
        }
        
        return this.agenceRepo.save(agence);
    }
    
}