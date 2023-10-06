package excpetion;

public class TipoImovelNuloOuVazioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TipoImovelNuloOuVazioException(String message) {
        super(message);
    }
}
