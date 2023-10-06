package excpetion;

public class NomeNuloOuVazioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NomeNuloOuVazioException(String message) {
        super(message);
    }
}
