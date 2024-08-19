package stage.redal.attente.queue_manager.exceptions;

public class EntityAlreadyInDataBase extends RuntimeException{
    public EntityAlreadyInDataBase(String message){
        super(message);
    }
}
