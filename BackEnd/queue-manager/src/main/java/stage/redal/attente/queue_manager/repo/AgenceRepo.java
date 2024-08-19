package stage.redal.attente.queue_manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stage.redal.attente.queue_manager.model.Agence;


@Repository
public interface AgenceRepo extends JpaRepository<Agence, Long>{    
    boolean existsByName(String name);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByAdress(String adress);
}
