package excpetion;

public class TelefoneNuloOuVazioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TelefoneNuloOuVazioException(String message) {
        super(message);
    }
}
