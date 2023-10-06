package excpetion;

public class EnderecoNuloOuVazioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EnderecoNuloOuVazioException(String message) {
        super(message);
    }
}
