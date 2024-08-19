package stage.redal.attente.queue_manager.enumeration;

public enum TypeService {
    ABONNEMENT("ABONNEMENT"),
    PAIEMENT("PAIEMENT"),
    RECLAMATION("RECLAMATION");

    private String value;

    private TypeService(String value) {
        this.value = value;
    }

    public static TypeService getByValue(String value) {
        if(value.toUpperCase().equals("ABONNEMENT"))
            return TypeService.ABONNEMENT;
        else if (value.toUpperCase().equals("PAIEMENT"))
            return TypeService.PAIEMENT;
        return TypeService.RECLAMATION;
    }

    public String getValue() {
        return this.value;
    }
}
