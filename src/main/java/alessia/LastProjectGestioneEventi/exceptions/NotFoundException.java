package alessia.LastProjectGestioneEventi.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(int id){
        super("The searched Record with id " + id +"is not found");
    }
}
