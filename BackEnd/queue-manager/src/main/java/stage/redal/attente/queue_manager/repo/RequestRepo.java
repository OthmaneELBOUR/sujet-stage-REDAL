package stage.redal.attente.queue_manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stage.redal.attente.queue_manager.model.Employee;
import stage.redal.attente.queue_manager.model.Request;
import stage.redal.attente.queue_manager.model.Service;
import java.util.List;
import stage.redal.attente.queue_manager.enumeration.RequestState;



@Repository
public interface RequestRepo extends JpaRepository<Request, Long>{
    List<Request> getRequestsByService(Service service);
    List<Request> getRequestsByEmployee(Employee employee);
    boolean existsByNumberTicket(Integer numberTicket);
    boolean existsByService(Service service);
    List<Request> findByState(RequestState state);

    
}
