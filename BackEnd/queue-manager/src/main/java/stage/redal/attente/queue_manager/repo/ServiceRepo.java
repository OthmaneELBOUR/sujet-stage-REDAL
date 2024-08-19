package stage.redal.attente.queue_manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stage.redal.attente.queue_manager.model.Service;
import stage.redal.attente.queue_manager.model.Agence;
import java.util.List;
import stage.redal.attente.queue_manager.enumeration.TypeService;




@Repository
public interface ServiceRepo extends JpaRepository<Service, Long>{
    List<Service> getServicesByAgence(Agence agence);

    List<Service> findByTypeService(TypeService typeService);
}
