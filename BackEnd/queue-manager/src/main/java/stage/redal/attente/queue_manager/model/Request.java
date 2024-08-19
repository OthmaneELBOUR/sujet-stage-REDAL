package stage.redal.attente.queue_manager.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import stage.redal.attente.queue_manager.enumeration.RequestState;

@Entity
@Table(name = "history")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private Integer numberTicket;
    private LocalDateTime timeStamp;

    @ManyToOne
    private Service service;
    @Enumerated(EnumType.STRING)
    private RequestState state;

    @ManyToOne
    private Employee employee;


    public Request() {
    }

    public Request(Integer numberTicket, LocalDateTime timeStamp, Service service) {
        this.numberTicket = numberTicket;
        this.timeStamp = timeStamp;
        this.service = service;
        this.state = RequestState.IN_PROGRESS;
        this.employee = null;
    }

    public Long getRequestId() {
        return this.requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Integer getNumberTicket() {
        return this.numberTicket;
    }

    public void setNumberTicket(Integer numberTicket) {
        this.numberTicket = numberTicket;
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Service getService() {
        return this.service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public RequestState getState() {
        return this.state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }


    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }



    @Override
    public String toString() {
        return "{" +
            " requestId='" + getRequestId() + "'" +
            ", numberTicket='" + getNumberTicket() + "'" +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", service='" + getService() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }

}
