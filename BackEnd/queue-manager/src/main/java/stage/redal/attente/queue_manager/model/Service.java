package stage.redal.attente.queue_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import stage.redal.attente.queue_manager.enumeration.TypeService;

@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long serviceId;
    @Enumerated(EnumType.STRING)
    private TypeService typeService;
    @ManyToOne
    private Agence agence; 


    public Service() {
    }



    public Service(TypeService typeService, Agence agence) {
        this.typeService = typeService;
        this.agence = agence;
    }
    

    public Long getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public TypeService getTypeService() {
        return this.typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public Agence getAgence() {
        return this.agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }



    @Override
    public String toString() {
        return "{" +
            " serviceId='" + getServiceId() + "'" +
            ", typeService='" + getTypeService() + "'" +
            ", agence='" + getAgence() + "'" +
            "}";
    }

}
