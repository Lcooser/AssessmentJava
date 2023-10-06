package excpetion;

public class EmailNuloOuVazioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailNuloOuVazioException(String message) {
        super(message);
    }
}
