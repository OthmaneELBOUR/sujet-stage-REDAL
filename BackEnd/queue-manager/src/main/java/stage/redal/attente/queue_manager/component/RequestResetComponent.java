package stage.redal.attente.queue_manager.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import stage.redal.attente.queue_manager.service.RequestService;

@Component
public class RequestResetComponent {

    @Autowired
    private RequestService requestService;

    @Scheduled(cron = "0 0 8 * *")
    public void resetTicket() {
        this.requestService.resetTickets();
        this.requestService.deleteOldData();
    }    
}
