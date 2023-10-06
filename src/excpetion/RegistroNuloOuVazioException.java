package excpetion;

public class RegistroNuloOuVazioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RegistroNuloOuVazioException(String message) {
        super(message);
    }
}
