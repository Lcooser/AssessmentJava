package excpetion;

public class ValorAluguelInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValorAluguelInvalidoException(String message) {
        super(message);
    }
}
