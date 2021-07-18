package business.custom_exceptions;

public class UnauthorizeException extends RuntimeException{
    public UnauthorizeException(String exception){super(exception);}
}
