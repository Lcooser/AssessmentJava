package excpetion;

public class ImovelNuloException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ImovelNuloException(String message) {
        super(message);
    }
}
