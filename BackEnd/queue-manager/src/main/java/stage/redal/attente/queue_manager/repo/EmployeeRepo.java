package stage.redal.attente.queue_manager.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stage.redal.attente.queue_manager.model.Employee;
import stage.redal.attente.queue_manager.model.Service;
import java.util.List;


@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> getEmployeeByService(Service service);
    
    List<Employee> getEmployeeByEmail(String email);

    List<Employee> getEmployeeByUsername(String username);


}
