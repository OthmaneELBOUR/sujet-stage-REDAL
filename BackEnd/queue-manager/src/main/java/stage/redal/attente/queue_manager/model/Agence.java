package stage.redal.attente.queue_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agences")
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agenceId;
    private String name;
    private String adress;
    private String phoneNumber;


    public Agence() {
    }

    
    public Agence(String name, String adress, String phoneNumber) {
        this.name = name;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
    }


    public Long getAgenceId() {
        return this.agenceId;
    }

    public void setAgenceId(Long agenceId) {
        this.agenceId = agenceId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return this.adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    

    @Override
    public String toString() {
        return "{" +
            " agenceId='" + getAgenceId() + "'" +
            ", name='" + getName() + "'" +
            ", adress='" + getAdress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }

}
